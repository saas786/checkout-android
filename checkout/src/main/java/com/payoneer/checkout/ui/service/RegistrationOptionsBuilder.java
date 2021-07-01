/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import static com.payoneer.checkout.localization.LocalizationKey.ALLOW_RECURRENCE_FORCED;
import static com.payoneer.checkout.localization.LocalizationKey.ALLOW_RECURRENCE_OPTIONAL;
import static com.payoneer.checkout.localization.LocalizationKey.AUTO_REGISTRATION_FORCED;
import static com.payoneer.checkout.localization.LocalizationKey.AUTO_REGISTRATION_OPTIONAL;
import static com.payoneer.checkout.model.NetworkOperationType.UPDATE;

import com.payoneer.checkout.model.CheckboxMode;
import com.payoneer.checkout.model.RegistrationType;
import com.payoneer.checkout.ui.model.RegistrationOption;

import android.text.TextUtils;

/**
 * Class for building the RegistrationOptions for both
 * recurrence and registration
 */
public final class RegistrationOptionsBuilder {

    private String autoRegistration;
    private String allowRecurrence;
    private String operationType;

    /**
     * Build the RegistrationOption for autoRegistration
     *
     * @return the newly build RegistrationOption for autoRegistration
     */
    public RegistrationOption buildAutoRegistrationOption() {
        if (TextUtils.isEmpty(operationType) || TextUtils.isEmpty(allowRecurrence)) {
            throw new IllegalStateException("operationType or autoRegistration may not be null or empty");
        }
        if (UPDATE.equals(operationType)) {
            return buildUpdateAutoRegistrationOption();
        }
        return buildDefaultAutoRegistrationOption();
    }

    private RegistrationOption buildUpdateAutoRegistrationOption() {
        switch (autoRegistration) {
            case RegistrationType.OPTIONAL:
            case RegistrationType.OPTIONAL_PRESELECTED:
            case RegistrationType.FORCED:
            case RegistrationType.FORCED_DISPLAYED:
                return new RegistrationOption(CheckboxMode.FORCED, AUTO_REGISTRATION_FORCED);
            default:
                return new RegistrationOption(CheckboxMode.NONE, AUTO_REGISTRATION_FORCED);
        }
    }

    private RegistrationOption buildDefaultAutoRegistrationOption() {
        switch (autoRegistration) {
            case RegistrationType.OPTIONAL:
                return new RegistrationOption(CheckboxMode.OPTIONAL, AUTO_REGISTRATION_OPTIONAL);
            case RegistrationType.OPTIONAL_PRESELECTED:
                return new RegistrationOption(CheckboxMode.OPTIONAL_PRESELECTED, AUTO_REGISTRATION_OPTIONAL);
            case RegistrationType.FORCED:
                return new RegistrationOption(CheckboxMode.FORCED, AUTO_REGISTRATION_FORCED);
            case RegistrationType.FORCED_DISPLAYED:
                return new RegistrationOption(CheckboxMode.FORCED_DISPLAYED, AUTO_REGISTRATION_FORCED);
            default:
                return new RegistrationOption(CheckboxMode.NONE, AUTO_REGISTRATION_FORCED);
        }
    }

    /**
     * Build the RegistrationOption for allowRecurrence
     *
     * @return the newly build RegistrationOption for allowRecurrence
     */
    public RegistrationOption buildAllowRecurrenceOption() {
        if (TextUtils.isEmpty(operationType) || TextUtils.isEmpty(allowRecurrence)) {
            throw new IllegalStateException("operationType or allowRecurrence may not be null or empty");
        }
        if (UPDATE.equals(operationType)) {
            return buildUpdateAllowRecurrenceOption();
        }
        return buildDefaultAllowRecurrenceOption();
    }

    private RegistrationOption buildDefaultAllowRecurrenceOption() {
        switch (allowRecurrence) {
            case RegistrationType.OPTIONAL:
                return new RegistrationOption(CheckboxMode.OPTIONAL, ALLOW_RECURRENCE_OPTIONAL);
            case RegistrationType.OPTIONAL_PRESELECTED:
                return new RegistrationOption(CheckboxMode.OPTIONAL_PRESELECTED, ALLOW_RECURRENCE_OPTIONAL);
            case RegistrationType.FORCED:
                return new RegistrationOption(CheckboxMode.FORCED, ALLOW_RECURRENCE_FORCED);
            case RegistrationType.FORCED_DISPLAYED:
                return new RegistrationOption(CheckboxMode.FORCED_DISPLAYED, ALLOW_RECURRENCE_FORCED);
            default:
                return new RegistrationOption(CheckboxMode.NONE, ALLOW_RECURRENCE_FORCED);
        }
    }

    private RegistrationOption buildUpdateAllowRecurrenceOption() {
        switch (allowRecurrence) {
            case RegistrationType.OPTIONAL:
            case RegistrationType.OPTIONAL_PRESELECTED:
            case RegistrationType.FORCED:
            case RegistrationType.FORCED_DISPLAYED:
                return new RegistrationOption(CheckboxMode.FORCED, ALLOW_RECURRENCE_FORCED);
            default:
                return new RegistrationOption(CheckboxMode.NONE, ALLOW_RECURRENCE_FORCED);
        }
    }

    public RegistrationOptionsBuilder setOperationType(String operationType) {
        this.operationType = operationType;
        return this;
    }

    public RegistrationOptionsBuilder setAutoRegistration(String autoRegistration) {
        this.autoRegistration = autoRegistration;
        return this;
    }

    public RegistrationOptionsBuilder setAllowRecurrence(String allowRecurrence) {
        this.allowRecurrence = allowRecurrence;
        return this;
    }
}
