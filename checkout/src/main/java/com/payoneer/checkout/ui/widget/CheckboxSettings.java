/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

/**
 * Class for storing the CheckBoxSettings
 */
public final class CheckboxSettings {
    private final String checkboxMode;
    private final String labelKey;

    /** 
     * Construct a new CheckBoxSettings
     * 
     * @param checkboxMode the mode of the checkbox
     * @param labelKey localization key for the checkbox label
     */
    public CheckboxSettings(String checkboxMode, String labelKey) {
        this.checkboxMode = checkboxMode;
        this.labelKey = labelKey;
    }

    public String getCheckboxMode() {
        return checkboxMode;
    }

    public String getLabelKey() {
        return labelKey;
    }
}
