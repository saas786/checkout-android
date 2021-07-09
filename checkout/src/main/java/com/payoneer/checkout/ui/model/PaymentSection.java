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
 * Payment section containing a header label and payment cards.
 * Currently there are three payment sections: preset accounts,
 * saved accounts, and payment networks.
 */
public final class PaymentSection {

    private final String labelKey;
    private final List<PaymentCard> cards;

    /**
     * Construct a new PaymentSection with the header label localization key
     *
     * @param labelKey localization key for the payment section header label
     * @param cards the list of payment cards in this section
     */
    public PaymentSection(String labelKey, List<PaymentCard> cards) {
        this.labelKey = labelKey;
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
     * Get the localized header label
     *
     * @return localized header label
     */
    public String getLabel() {
        return Localization.translate(labelKey);
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
