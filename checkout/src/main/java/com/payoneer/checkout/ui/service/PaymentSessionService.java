/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import static com.payoneer.checkout.localization.LocalizationKey.BUTTON_UPDATE_ACCOUNT;
import static com.payoneer.checkout.localization.LocalizationKey.LIST_HEADER_ACCOUNTS;
import static com.payoneer.checkout.localization.LocalizationKey.LIST_HEADER_ACCOUNTS_UPDATE;
import static com.payoneer.checkout.localization.LocalizationKey.LIST_HEADER_NETWORKS;
import static com.payoneer.checkout.localization.LocalizationKey.LIST_HEADER_NETWORKS_OTHER;
import static com.payoneer.checkout.localization.LocalizationKey.LIST_HEADER_NETWORKS_UPDATE;
import static com.payoneer.checkout.localization.LocalizationKey.LIST_HEADER_PRESET;
import static com.payoneer.checkout.model.IntegrationType.MOBILE_NATIVE;
import static com.payoneer.checkout.model.NetworkOperationType.CHARGE;
import static com.payoneer.checkout.model.NetworkOperationType.PRESET;
import static com.payoneer.checkout.model.NetworkOperationType.UPDATE;
import static com.payoneer.checkout.model.RegistrationType.NONE;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.payoneer.checkout.R;
import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.core.WorkerSubscriber;
import com.payoneer.checkout.core.WorkerTask;
import com.payoneer.checkout.core.Workers;
import com.payoneer.checkout.localization.LocalLocalizationHolder;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.localization.LocalizationCache;
import com.payoneer.checkout.localization.LocalizationHolder;
import com.payoneer.checkout.localization.LocalizationKey;
import com.payoneer.checkout.localization.MultiLocalizationHolder;
import com.payoneer.checkout.model.AccountRegistration;
import com.payoneer.checkout.model.ApplicableNetwork;
import com.payoneer.checkout.model.ExtraElements;
import com.payoneer.checkout.model.ListResult;
import com.payoneer.checkout.model.Networks;
import com.payoneer.checkout.model.PresetAccount;
import com.payoneer.checkout.network.ListConnection;
import com.payoneer.checkout.network.LocalizationConnection;
import com.payoneer.checkout.resource.PaymentGroup;
import com.payoneer.checkout.resource.ResourceLoader;
import com.payoneer.checkout.ui.model.AccountCard;
import com.payoneer.checkout.ui.model.NetworkCard;
import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.model.PaymentNetwork;
import com.payoneer.checkout.ui.model.PaymentSection;
import com.payoneer.checkout.ui.model.PaymentSession;
import com.payoneer.checkout.ui.model.PresetCard;
import com.payoneer.checkout.ui.model.RegistrationOption;
import com.payoneer.checkout.validation.Validator;

import android.content.Context;
import android.text.TextUtils;

/**
 * The PaymentSessionService providing asynchronous loading of the PaymentSession.
 * This service makes callbacks in the listener to notify of request completions.
 */
public final class PaymentSessionService {

    private final ListConnection listConnection;
    private final LocalizationConnection locConnection;

    private PaymentSessionListener listener;
    private WorkerTask<PaymentSession> sessionTask;

    /** Memory cache of localizations */
    private static final LocalizationCache cache = new LocalizationCache();

    /**
     * Create a new PaymentSessionService, this service is used to load the PaymentSession.
     *
     * @param context context in which this service will run
     */
    public PaymentSessionService(Context context) {
        this.listConnection = new ListConnection(context);
        this.locConnection = new LocalizationConnection(context);
    }

    /**
     * Set the session listener which will be informed about the state of a payment session being loaded.
     *
     * @param listener to be informed about the payment session being loaded
     */
    public void setListener(PaymentSessionListener listener) {
        this.listener = listener;
    }

    /**
     * Stop and unsubscribe from tasks that are currently active in this service.
     */
    public void stop() {
        if (sessionTask != null) {
            sessionTask.unsubscribe();
            sessionTask = null;
        }
    }

    /**
     * Check if this service is currently active, i.e. is is loading a payment session or posting an operation.
     *
     * @return true when active, false otherwise
     */
    public boolean isActive() {
        return sessionTask != null && sessionTask.isSubscribed();
    }

    /**
     * Load the PaymentSession with the given listUrl, this will load the list result, languages and validator.
     *
     * @param listUrl URL pointing to the list on the Payment API
     * @param context Android context in which this service is used
     */
    public void loadPaymentSession(final String listUrl, final Context context) {

        if (sessionTask != null) {
            throw new IllegalStateException("Already loading payment session, stop first");
        }
        sessionTask = WorkerTask.fromCallable(new Callable<PaymentSession>() {
            @Override
            public PaymentSession call() throws PaymentException {
                return asyncLoadPaymentSession(listUrl, context);
            }
        });
        sessionTask.subscribe(new WorkerSubscriber<PaymentSession>() {
            @Override
            public void onSuccess(PaymentSession paymentSession) {
                sessionTask = null;

                if (listener != null) {
                    listener.onPaymentSessionSuccess(paymentSession);
                }
            }

            @Override
            public void onError(Throwable cause) {
                sessionTask = null;

                if (listener != null) {
                    listener.onPaymentSessionError(cause);
                }
            }
        });
        Workers.getInstance().forNetworkTasks().execute(sessionTask);
    }

    /**
     * Check if the provided operationType is supported by this PaymentSessionService
     *
     * @param operationType the operation type to check
     * @return true when supported, false otherwise
     */
    public boolean isSupportedNetworkOperationType(String operationType) {
        if (operationType == null) {
            return false;
        }
        switch (operationType) {
            case CHARGE:
            case PRESET:
            case UPDATE:
                return true;
            default:
                return false;
        }
    }

    private PaymentSession asyncLoadPaymentSession(String listUrl, Context context) throws PaymentException {
        ListResult listResult = listConnection.getListResult(listUrl);

        String integrationType = listResult.getIntegrationType();
        if (!MOBILE_NATIVE.equals(integrationType)) {
            throw new PaymentException("Integration type is not supported: " + integrationType);
        }
        String operationType = listResult.getOperationType();
        if (!isSupportedNetworkOperationType(operationType)) {
            throw new PaymentException("List operationType is not supported: " + operationType);
        }
        List<PaymentSection> sections = new ArrayList<PaymentSection>();
        PaymentSection section = createPresetSection(listResult);
        if (section != null) {
            sections.add(section);
        }
        section = createAccountSection(listResult);
        if (section != null) {
            sections.add(section);
        }
        section = createNetworkSection(listResult, context, section != null);
        if (section != null) {
            sections.add(section);
        }
        boolean refresh = UPDATE.equals(operationType);
        PaymentSession session = new PaymentSession(listResult, sections, refresh);

        loadValidator(context);
        loadLocalizations(context, session);
        return session;
    }

    private PaymentSection createPresetSection(ListResult listResult) {
        PresetAccount account = listResult.getPresetAccount();
        if (account == null) {
            return null;
        }
        List<PaymentCard> cards = new ArrayList<>();
        cards.add(createPresetCard(account, listResult));
        return new PaymentSection(LIST_HEADER_PRESET, cards);
    }

    private PaymentSection createAccountSection(ListResult listResult) {
        List<PaymentCard> cards = new ArrayList<>();
        List<AccountRegistration> accounts = listResult.getAccounts();

        if (accounts == null || accounts.size() == 0) {
            return null;
        }
        for (AccountRegistration account : accounts) {
            if (NetworkServiceLookup.supports(account.getCode(), account.getMethod())) {
                cards.add(createAccountCard(account, listResult));
            }
        }
        if (cards.size() == 0) {
            return null;
        }
        String labelKey = UPDATE.equals(listResult.getOperationType()) ?
            LIST_HEADER_ACCOUNTS_UPDATE : LIST_HEADER_ACCOUNTS;
        return new PaymentSection(labelKey, cards);
    }

    private PresetCard createPresetCard(PresetAccount account, ListResult listResult) {
        String buttonKey = LocalizationKey.operationButtonKey(PRESET);
        ExtraElements extraElements = listResult.getExtraElements();
        return new PresetCard(account, buttonKey, extraElements);
    }


    private AccountCard createAccountCard(AccountRegistration account, ListResult listResult) {
        String operationType = account.getOperationType();
        boolean update = UPDATE.equals(operationType);
        ExtraElements extraElements = listResult.getExtraElements();
        String buttonKey = update ? BUTTON_UPDATE_ACCOUNT :
            LocalizationKey.operationButtonKey(operationType);

        AccountCard card = new AccountCard(account, buttonKey, update, update, extraElements);

        // Only in update flow and when the input form is empty, the input form is hidden
        if (update && card.hasEmptyInputForm()) {
            card.setHideInputForm(true);
        }
        return card;
    }

    private PaymentSection createNetworkSection(ListResult listResult, Context context, boolean containsAccounts)
        throws PaymentException {
        Map<String, PaymentGroup> groups = loadPaymentGroups(context);
        Map<String, PaymentNetwork> networks = loadPaymentNetworks(listResult);
        Map<String, NetworkCard> cards = new LinkedHashMap<>();
        PaymentGroup group;

        for (PaymentNetwork network : networks.values()) {
            group = groups.get(network.getNetworkCode());

            if (group == null) {
                addNetwork2SingleCard(cards, network, listResult);
            } else {
                addNetwork2GroupCard(cards, network, group, listResult);
            }
        }
        if (cards.size() == 0) {
            return null;
        }
        String labelKey;
        if (UPDATE.equals(listResult.getOperationType())) {
            labelKey = LIST_HEADER_NETWORKS_UPDATE;
        } else if (containsAccounts) {
            labelKey = LIST_HEADER_NETWORKS_OTHER;
        } else {
            labelKey = LIST_HEADER_NETWORKS;
        }
        return new PaymentSection(labelKey, new ArrayList<>(cards.values()));
    }

    private Map<String, PaymentNetwork> loadPaymentNetworks(ListResult listResult) {
        LinkedHashMap<String, PaymentNetwork> items = new LinkedHashMap<>();
        Networks nw = listResult.getNetworks();

        if (nw == null) {
            return items;
        }
        List<ApplicableNetwork> an = nw.getApplicable();
        if (an == null || an.size() == 0) {
            return items;
        }
        for (ApplicableNetwork network : an) {
            String code = network.getCode();
            if (supportsApplicableNetwork(listResult, network)) {
                items.put(code, createPaymentNetwork(network));
            }
        }
        return items;
    }

    private boolean supportsApplicableNetwork(ListResult listResult, ApplicableNetwork network) {
        String operationType = listResult.getOperationType();
        String recurrence = network.getRecurrence();
        String registration = network.getRegistration();

        // Special case to hide networks in Update flow with both registration settings set to NONE.
        if (UPDATE.equals(operationType) && NONE.equals(recurrence) && NONE.equals(registration)) {
            return false;
        }
        return NetworkServiceLookup.supports(network.getCode(), network.getMethod());
    }

    private PaymentNetwork createPaymentNetwork(ApplicableNetwork network) {
        String operationType = network.getOperationType();
        String buttonKey = LocalizationKey.operationButtonKey(operationType);

        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(operationType)
            .setAutoRegistration(network.getRegistration())
            .setAllowRecurrence(network.getRecurrence());

        RegistrationOption registration = builder.buildAutoRegistrationOption();
        RegistrationOption recurrence = builder.buildAllowRecurrenceOption();
        return new PaymentNetwork(network, buttonKey, registration, recurrence);
    }

    private void addNetwork2SingleCard(Map<String, NetworkCard> cards, PaymentNetwork network, ListResult listResult) {
        NetworkCard card = new NetworkCard(listResult.getExtraElements());
        card.addPaymentNetwork(network);
        cards.put(network.getNetworkCode(), card);
    }

    private void addNetwork2GroupCard(Map<String, NetworkCard> cards, PaymentNetwork network, PaymentGroup group, ListResult listResult)
        throws PaymentException {
        String code = network.getNetworkCode();
        String groupId = group.getId();
        String regex = group.getSmartSelectionRegex(code);
        ExtraElements extraElements = listResult.getExtraElements();

        if (TextUtils.isEmpty(regex)) {
            throw new PaymentException("Missing regex for network: " + code + " in group: " + groupId);
        }
        NetworkCard card = cards.get(groupId);
        if (card == null) {
            card = new NetworkCard(extraElements);
            cards.put(groupId, card);
        }
        // a network can always be added to an empty card
        if (!card.addPaymentNetwork(network)) {
            addNetwork2SingleCard(cards, network, listResult);
            return;
        }
        card.getSmartSwitch().addSelectionRegex(code, regex);
    }

    private Map<String, PaymentGroup> loadPaymentGroups(Context context) throws PaymentException {
        return ResourceLoader.loadPaymentGroups(context.getResources(), R.raw.groups);
    }

    private void loadValidator(Context context) throws PaymentException {
        if (Validator.getInstance() == null) {
            Validator validator = new Validator(ResourceLoader.loadValidations(context.getResources(), R.raw.validations));
            Validator.setInstance(validator);
        }
    }

    private void loadLocalizations(Context context, PaymentSession session) throws PaymentException {
        String listUrl = session.getListSelfUrl();
        if (!listUrl.equals(cache.getCacheId())) {
            cache.clear();
            cache.setCacheId(listUrl);
        }
        LocalizationHolder localHolder = new LocalLocalizationHolder(context);
        LocalizationHolder sharedHolder = loadLocalizationHolder(session.getListLanguageLink(), localHolder);

        Map<String, LocalizationHolder> holders = new HashMap<>();
        Map<String, URL> links = session.getLanguageLinks();
        for (Map.Entry<String, URL> entry : links.entrySet()) {
            holders.put(entry.getKey(), loadLocalizationHolder(entry.getValue(), sharedHolder));
        }
        Localization.setInstance(new Localization(sharedHolder, holders));
    }

    private LocalizationHolder loadLocalizationHolder(URL url, LocalizationHolder fallback) throws PaymentException {
        String langUrl = url.toString();
        LocalizationHolder holder = cache.get(langUrl);

        if (holder == null) {
            holder = new MultiLocalizationHolder(locConnection.loadLocalization(url), fallback);
            cache.put(langUrl, holder);
        }
        return holder;
    }
}
