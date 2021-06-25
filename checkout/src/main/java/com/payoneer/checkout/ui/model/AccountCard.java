/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import static com.payoneer.checkout.ui.model.PaymentSession.LINK_LANGUAGE;

import java.net.URL;
import java.util.List;
import java.util.Map;

import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.model.AccountMask;
import com.payoneer.checkout.model.AccountRegistration;
import com.payoneer.checkout.model.InputElement;
import com.payoneer.checkout.util.PaymentUtils;

/**
 * Class for holding the data of a AccountCard in the list
 */
public final class AccountCard extends PaymentCard {
    private final AccountRegistration account;
    private final String buttonKey;
    private final boolean deletable;

    public AccountCard(AccountRegistration account, String buttonKey, boolean deletable, boolean checkable) {
        super(checkable);
        this.account = account;
        this.buttonKey = buttonKey;
        this.deletable = deletable;
    }

    @Override
    public void putLanguageLinks(Map<String, URL> links) {
        URL url = getLink(LINK_LANGUAGE);
        if (url != null) {
            links.put(getNetworkCode(), url);
        }
    }

    @Override
    public String getOperationType() {
        return account.getOperationType();
    }

    @Override
    public String getPaymentMethod() {
        return account.getMethod();
    }

    @Override
    public String getNetworkCode() {
        return account.getCode();
    }

    @Override
    public boolean hasEmptyInputForm() {
        return getInputElements().size() == 0;
    }

    @Override
    public String getTitle() {
        AccountMask accountMask = account.getMaskedAccount();
        if (accountMask != null) {
            return PaymentUtils.getAccountMaskLabel(accountMask, getPaymentMethod());
        }
        return Localization.translateNetworkLabel(account.getCode());
    }

    @Override
    public String getSubtitle() {
        AccountMask accountMask = account.getMaskedAccount();
        return accountMask != null ? PaymentUtils.getExpiryDateString(accountMask) : null;
    }

    @Override
    public Map<String, URL> getLinks() {
        return PaymentUtils.emptyMapIfNull(account.getLinks());
    }

    @Override
    public List<InputElement> getInputElements() {
        return PaymentUtils.emptyListIfNull(account.getInputElements());
    }

    @Override
    public String getButton() {
        return Localization.translate(getNetworkCode(), buttonKey);
    }

    @Override
    public boolean isPreselected() {
        return PaymentUtils.isTrue(account.getSelected());
    }

    @Override
    public boolean onTextInputChanged(String type, String text) {
        return false;
    }

    @Override
    public boolean containsLink(final String name, final URL url) {
        return PaymentUtils.equalsAsString(getLink(name), url);
    }

    public boolean isDeletable() {
        return deletable;
    }

    public AccountMask getMaskedAccount() {
        return account.getMaskedAccount();
    }

    public AccountRegistration getAccountRegistration() {
        return account;
    }
}
