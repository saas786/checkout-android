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
import com.payoneer.checkout.ui.model.CheckboxSettings;

import android.text.TextUtils;

/**
 * Class for building the Checkbox settings for both 
 * recurrence and registration settings
 */
public final class RegisterCheckboxBuilder {

    private String registration;
    private String recurrence;
    private String operationType;

    /**
     * Build the registration Checkbox settings
     *
     * @return the newly build CheckboxSettings for registration
     */
    public CheckboxSettings buildRegistrationCheckboxSettings() {
        if (TextUtils.isEmpty(operationType) || TextUtils.isEmpty(recurrence)) {
            throw new IllegalStateException("operationType or recurrence may not be null or empty");
        }
        if (UPDATE.equals(operationType)) {
            return buildUpdateRegistrationCheckboxSettings();
        }
        return buildDefaultRegistrationCheckboxSettings();
    }

    private CheckboxSettings buildUpdateRegistrationCheckboxSettings() {
        switch (registration) {
            case RegistrationType.OPTIONAL:
            case RegistrationType.OPTIONAL_PRESELECTED:
            case RegistrationType.FORCED:
            case RegistrationType.FORCED_DISPLAYED:
                return new CheckboxSettings(CheckboxMode.FORCED, AUTO_REGISTRATION_FORCED);
            default:
                return new CheckboxSettings(CheckboxMode.NONE, AUTO_REGISTRATION_FORCED);            
        }
    }

    private CheckboxSettings buildDefaultRegistrationCheckboxSettings() {
        switch (registration) {
            case RegistrationType.OPTIONAL:
                return new CheckboxSettings(CheckboxMode.OPTIONAL, AUTO_REGISTRATION_OPTIONAL);
            case RegistrationType.OPTIONAL_PRESELECTED:
                return new CheckboxSettings(CheckboxMode.OPTIONAL_PRESELECTED, AUTO_REGISTRATION_OPTIONAL);
            case RegistrationType.FORCED:
                return new CheckboxSettings(CheckboxMode.FORCED, AUTO_REGISTRATION_FORCED);
            case RegistrationType.FORCED_DISPLAYED:
                return new CheckboxSettings(CheckboxMode.FORCED_DISPLAYED, AUTO_REGISTRATION_FORCED);
            default:
                return new CheckboxSettings(CheckboxMode.NONE, AUTO_REGISTRATION_OPTIONAL);
        }
    }
    
    /**
     * Build the recurrence Checkbox settings
     *
     * @return the newly build CheckboxSettings for recurrence
     */
    public CheckboxSettings buildRecurrenceCheckboxSettings() {
        if (TextUtils.isEmpty(operationType) || TextUtils.isEmpty(recurrence)) {
            throw new IllegalStateException("operationType or recurrence may not be null or empty");
        }
        if (UPDATE.equals(operationType)) {
            return buildUpdateRecurrenceCheckboxSettings();
        }
        return buildDefaultRecurrenceCheckboxSettings();
    }

    private CheckboxSettings buildDefaultRecurrenceCheckboxSettings() {
        switch (recurrence) {
            case RegistrationType.OPTIONAL:
                return new CheckboxSettings(CheckboxMode.OPTIONAL, ALLOW_RECURRENCE_OPTIONAL);
            case RegistrationType.OPTIONAL_PRESELECTED:
                return new CheckboxSettings(CheckboxMode.OPTIONAL_PRESELECTED, ALLOW_RECURRENCE_OPTIONAL);
            case RegistrationType.FORCED:
                return new CheckboxSettings(CheckboxMode.FORCED, ALLOW_RECURRENCE_FORCED);
            case RegistrationType.FORCED_DISPLAYED:
                return new CheckboxSettings(CheckboxMode.FORCED_DISPLAYED, ALLOW_RECURRENCE_FORCED);
            default:
                return new CheckboxSettings(CheckboxMode.NONE, ALLOW_RECURRENCE_OPTIONAL);
        }
    }
    
    private CheckboxSettings buildUpdateRecurrenceCheckboxSettings() {
        switch (recurrence) {
            case RegistrationType.OPTIONAL:
            case RegistrationType.OPTIONAL_PRESELECTED:
            case RegistrationType.FORCED:
            case RegistrationType.FORCED_DISPLAYED:
                return new CheckboxSettings(CheckboxMode.FORCED, ALLOW_RECURRENCE_FORCED);
            default:
                return new CheckboxSettings(CheckboxMode.NONE, ALLOW_RECURRENCE_FORCED);
        }
    }
    
    public RegisterCheckboxBuilder setOperationType(String operationType) {
        this.operationType = operationType;
        return this;
    }
    
    public RegisterCheckboxBuilder setRegistration(String registration) {
        this.registration = registration;
        return this;
    }

    public RegisterCheckboxBuilder setRecurrence(String recurrence) {
        this.recurrence = recurrence;
        return this;
    }
}
