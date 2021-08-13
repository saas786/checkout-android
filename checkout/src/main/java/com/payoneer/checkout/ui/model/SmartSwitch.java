/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * Class for storing and selecting payment networks based on smart switch logic
 */
public final class SmartSwitch {

    private final Map<String, Pattern> smartMapping;
    private final List<PaymentNetwork> networks;
    private final List<PaymentNetwork> smartSelected;
    private final List<PaymentNetwork> smartBuffer;

    public SmartSwitch(List<PaymentNetwork> networks) {
        this.networks = networks;
        this.smartMapping = new HashMap<>();
        this.smartSelected = new ArrayList<>();
        this.smartBuffer = new ArrayList<>();
    }

    public void addSelectionRegex(String code, String regex) {
        if (!TextUtils.isEmpty(regex)) {
            smartMapping.put(code, Pattern.compile(regex));
        }
    }

    public void reset() {
        smartBuffer.clear();
        smartSelected.clear();
    }

    /**
     * Get the first smart selected PaymentNetwork, may return null if none are selected.
     *
     * @return the first smart selected PaymentNetwork.
     */
    public PaymentNetwork getFirstSelected() {
        return smartSelected.isEmpty() ? null : smartSelected.get(0);
    }

    /**
     * Get the number of selected networks
     *
     * @return the number of selected networks
     */
    public int getSelectedCount() {
        return smartSelected.size();
    }

    /**
     * Validate the PaymentMethods for smart selection given the new text
     *
     * @param text to validate for smart selection
     * @return true when the smart selection of payment methods has changed, false otherwise
     */
    public boolean validate(String text) {
        smartBuffer.clear();

        if (text != null) {
            for (PaymentNetwork network : networks) {
                Pattern pattern = smartMapping.get(network.getNetworkCode());

                if (pattern != null && pattern.matcher(text).matches()) {
                    smartBuffer.add(network);
                }
            }
        }
        if (!smartSelected.equals(smartBuffer)) {
            smartSelected.clear();
            smartSelected.addAll(smartBuffer);
            return true;
        }
        return false;
    }
}

