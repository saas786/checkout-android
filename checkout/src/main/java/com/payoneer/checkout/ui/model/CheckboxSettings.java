/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import com.payoneer.checkout.localization.Localization;

/**
 * Class for storing the CheckBoxSettings
 */
public final class CheckboxSettings {
    private final String mode;
    private final String labelKey;

    /**
     * Construct a new CheckBoxSettings
     *
     * @param mode the mode of the checkbox
     * @param labelKey localization key for the checkbox label
     */
    public CheckboxSettings(String mode, String labelKey) {
        this.mode = mode;
        this.labelKey = labelKey;
    }

    public String getMode() {
        return mode;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public String getLabel() {
        return Localization.translate(labelKey);
    }
}
