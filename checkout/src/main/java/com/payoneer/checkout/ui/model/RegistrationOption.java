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
 * Class for storing the RegistrationOption for autoRegistration and allowRecurrence options
 */
public final class RegistrationOption {
    private final String checkboxMode;
    private final String labelKey;

    /**
     * Construct a new RegistrationOption
     *
     * @param checkboxMode the mode of the checkbox
     * @param labelKey localization key for the checkbox label
     */
    public RegistrationOption(String checkboxMode, String labelKey) {
        this.checkboxMode = checkboxMode;
        this.labelKey = labelKey;
    }

    public String getCheckboxMode() {
        return checkboxMode;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public String getLabel() {
        return Localization.translate(labelKey);
    }
}
