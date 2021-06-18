/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import static com.payoneer.checkout.localization.LocalizationKey.BUTTON_UPDATE_ACCOUNT;
import static com.payoneer.checkout.localization.LocalizationKey.NETWORK_LABEL;
import static com.payoneer.checkout.model.NetworkOperationType.UPDATE;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.localization.LocalizationKey;
import com.payoneer.checkout.model.AccountMask;
import com.payoneer.checkout.model.AccountRegistration;
import com.payoneer.checkout.model.InputElement;
import com.payoneer.checkout.util.PaymentUtils;

/**
 * Class for holding the data of a AccountCard in the list
 */
public final class AccountCard implements PaymentCard {
    private final AccountRegistration account;

    public AccountCard(AccountRegistration account) {
        this.account = account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsLink(String name, URL url) {
        return PaymentUtils.equalsAsString(getLink(name), url);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putLanguageLinks(Map<String, URL> links) {
        URL url = getLink("lang");
        if (url != null) {
            links.put(getCode(), url);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getOperationLink() {
        return getLink("operation");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOperationType() {
        return account.getOperationType();
    }

    public URL getLink(String name) {
        Map<String, URL> links = account.getLinks();
        return links != null ? links.get(name) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaymentMethod() {
        return account.getMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return account.getCode();
    }

    @Override
    public String getLabel() {
        AccountMask accountMask = account.getMaskedAccount();
        if (accountMask != null) {
            return PaymentUtils.getAccountMaskLabel(accountMask, getPaymentMethod());
        }
        return Localization.translateNetworkLabel(account.getCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InputElement> getInputElements() {
        List<InputElement> elements = account.getInputElements();
        return elements == null ? Collections.emptyList() : elements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputElement getInputElement(String name) {
        for (InputElement element : getInputElements()) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPreselected() {
        return PaymentUtils.isTrue(account.getSelected());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getButton() {
        String operationType = getOperationType();
        return UPDATE.equals(operationType) ? BUTTON_UPDATE_ACCOUNT : LocalizationKey.operationButtonKey(getOperationType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTextInputChanged(String type, String text) {
        return false;
    }

    public AccountMask getMaskedAccount() {
        return account.getMaskedAccount();
    }

    public AccountRegistration getAccountRegistration() {
        return account;
    }
}
