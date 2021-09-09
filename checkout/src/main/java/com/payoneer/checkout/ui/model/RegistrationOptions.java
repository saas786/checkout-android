/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.util.Objects;

/**
 * Class for storing the RegistrationOption for autoRegistration and allowRecurrence options
 */
public final class RegistrationOptions {
    private final String checkboxMode;
    private final String autoRegistration;
    private final String allowRecurrence;

    /**
     * Construct a new RegistrationOption
     *
     * @param autoRegistration
     * @param allowRecurrence
     * @param checkboxMode the mode of the checkbox
     */
    public RegistrationOptions(String autoRegistration, String allowRecurrence, String checkboxMode) {
        this.autoRegistration = autoRegistration;
        this.allowRecurrence = allowRecurrence;
        this.checkboxMode = checkboxMode;
    }

    public String getAutoRegistration() {
        return autoRegistration;
    }

    public String getAllowRecurrence() {
        return allowRecurrence;
    }

    public String getCheckboxMode() {
        return checkboxMode;
    }

    public boolean matches(String autoRegistration, String allowRecurrence) {
        return (Objects.equals(this.autoRegistration, autoRegistration)) && (Objects.equals(this.allowRecurrence, allowRecurrence));
    }
}
