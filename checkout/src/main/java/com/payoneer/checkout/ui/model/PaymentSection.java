/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.net.URL;
import java.util.List;
import java.util.Map;

import com.payoneer.checkout.localization.Localization;

/**
 * Payment section containing a header title and payment cards.
 * Currently there are three payment sections: preset accounts,
 * saved accounts, and payment networks.
 */
public final class PaymentSection {

    private final String titleKey;
    private String messageKey;
    private final List<PaymentCard> cards;

    /**
     * Construct a new PaymentSection with the header title localization key and message text
     *
     * @param titleKey localization key for the payment section header title
     * @param messageKey localization key for the message text presented to the user during the preset flow
     * @param cards the list of payment cards in this section
     */
    public PaymentSection(String titleKey, String messageKey, List<PaymentCard> cards) {
        this.titleKey = titleKey;
        this.messageKey = messageKey;
        this.cards = cards;
    }

    /**
     * Construct a new PaymentSection with the header title localization key
     *
     * @param titleKey localization key for the payment section header title
     * @param cards the list of payment cards in this section
     */
    public PaymentSection(String titleKey, List<PaymentCard> cards) {
        this.titleKey = titleKey;
        this.cards = cards;
    }

    /**
     * Get the list of payment cards
     *
     * @return list of payment cards
     */
    public List<PaymentCard> getPaymentCards() {
        return cards;
    }

    /**
     * Get the localized header title
     *
     * @return localized header title
     */
    public String getTitle() {
        return Localization.translate(titleKey);
    }

    /**
     * Get the message text
     *
     * @return message text
     */
    public String getMessage() {
        return Localization.translate(messageKey);
    }

    /**
     * Check if this section contains a link with the provided name
     *
     * @param name of the link
     * @param url that should match
     * @return true when it contains the link, false otherwise
     */
    public boolean containsLink(String name, URL url) {
        for (PaymentCard card : cards) {
            if (card.containsLink(name, url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if this section contains a PaymentCard that has user input data.
     *
     * @return true when it contains data, false otherwise
     */
    public boolean hasUserInputData() {
        for (PaymentCard card : cards) {
            if (card.hasUserInputData()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reset all payment cards in this section
     */
    public void reset() {
        for (PaymentCard card : cards) {
            card.reset();
        }
    }

    /**
     * Put all language links from the section in the provided map.
     * The key of the map is the network code.
     *
     * @param links map containing language links
     */
    public void putLanguageLinks(Map<String, URL> links) {
        for (PaymentCard card : cards) {
            card.putLanguageLinks(links);
        }
    }
}
