/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.model.ApplicableNetwork;
import com.payoneer.checkout.model.InputElement;
import com.payoneer.checkout.util.PaymentUtils;

/**
 * Class for holding the ApplicableNetwork
 */
public class PaymentNetwork {

    private final ApplicableNetwork network;

    public PaymentNetwork(ApplicableNetwork network) {
        this.network = network;
    }

    public boolean containsLink(String name, URL url) {
        return PaymentUtils.equalsAsString(getLink(name), url);
    }

    public URL getLink(String name) {
        Map<String, URL> links = network.getLinks();
        return links != null ? links.get(name) : null;
    }

    public void putLanguageLink(Map<String, URL> links) {
        URL url = getLink("lang");
        if (url != null) {
            links.put(getCode(), url);
        }
    }

    public String getOperationType() {
        return network.getOperationType();
    }

    public String getPaymentMethod() {
        return network.getMethod();
    }

    public String getCode() {
        return network.getCode();
    }

    public String getLabel() {
        return Localization.translateNetworkLabel(network.getCode());
    }

    public String getRecurrence() {
        return network.getRecurrence();
    }

    public String getRegistration() {
        return network.getRegistration();
    }

    public boolean isPreselected() {
        return PaymentUtils.isTrue(network.getSelected());
    }

    public List<InputElement> getInputElements() {
        List<InputElement> elements = network.getInputElements();
        return elements == null ? Collections.emptyList() : elements;
    }

    public boolean compare(PaymentNetwork network) {
        List<InputElement> srcItems = getInputElements();
        List<InputElement> cmpItems = network.getInputElements();

        if (srcItems.size() != cmpItems.size()) {
            return false;
        }
        InputElement srcItem;
        InputElement cmpItem;

        for (int i = 0, e = srcItems.size(); i < e; i++) {
            srcItem = srcItems.get(i);
            cmpItem = cmpItems.get(i);

            if (!(srcItem.getName().equals(cmpItem.getName()) && srcItem.getType().equals(cmpItem.getType()))) {
                return false;
            }
        }
        return true;
    }
}
