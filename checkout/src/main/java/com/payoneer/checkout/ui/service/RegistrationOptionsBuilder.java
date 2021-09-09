/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import static com.payoneer.checkout.model.NetworkOperationType.UPDATE;
import static com.payoneer.checkout.model.RegistrationType.FORCED;
import static com.payoneer.checkout.model.RegistrationType.FORCED_DISPLAYED;
import static com.payoneer.checkout.model.RegistrationType.NONE;
import static com.payoneer.checkout.model.RegistrationType.OPTIONAL;
import static com.payoneer.checkout.model.RegistrationType.OPTIONAL_PRESELECTED;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.model.CheckboxMode;
import com.payoneer.checkout.ui.model.RegistrationOptions;

import android.text.TextUtils;

/**
 * Class for building the RegistrationOptions for both
 * allowRecurrence and autoRegistration options.
 */
public final class RegistrationOptionsBuilder {

    private String autoRegistration;
    private String allowRecurrence;
    private String operationType;

    private final static RegistrationOptions[] DEFAULT_REGISTRATION_OPTIONS = {
        new RegistrationOptions(NONE, NONE, CheckboxMode.NONE),
        new RegistrationOptions(FORCED, NONE, CheckboxMode.NONE),
        new RegistrationOptions(FORCED_DISPLAYED, NONE, CheckboxMode.FORCED_DISPLAYED),
        new RegistrationOptions(FORCED, FORCED, CheckboxMode.NONE),
        new RegistrationOptions(FORCED_DISPLAYED, FORCED_DISPLAYED, CheckboxMode.FORCED_DISPLAYED),
        new RegistrationOptions(OPTIONAL, NONE, CheckboxMode.OPTIONAL),
        new RegistrationOptions(OPTIONAL_PRESELECTED, NONE, CheckboxMode.OPTIONAL_PRESELECTED),
        new RegistrationOptions(OPTIONAL, OPTIONAL, CheckboxMode.OPTIONAL),
        new RegistrationOptions(OPTIONAL_PRESELECTED, OPTIONAL_PRESELECTED, CheckboxMode.OPTIONAL_PRESELECTED)
    };

    private final static RegistrationOptions[] UPDATE_REGISTRATION_OPTIONS = {
        new RegistrationOptions(NONE, NONE, CheckboxMode.NONE),
        new RegistrationOptions(FORCED, NONE, CheckboxMode.NONE),
        new RegistrationOptions(FORCED_DISPLAYED, NONE, CheckboxMode.NONE),
        new RegistrationOptions(FORCED, FORCED, CheckboxMode.NONE),
        new RegistrationOptions(FORCED_DISPLAYED, FORCED_DISPLAYED, CheckboxMode.NONE),
        new RegistrationOptions(OPTIONAL, NONE, CheckboxMode.NONE),
        new RegistrationOptions(OPTIONAL_PRESELECTED, NONE, CheckboxMode.NONE),
        new RegistrationOptions(OPTIONAL, OPTIONAL, CheckboxMode.NONE),
        new RegistrationOptions(OPTIONAL_PRESELECTED, OPTIONAL_PRESELECTED, CheckboxMode.NONE)
    };

    /**
     * Build the RegistrationOption for autoRegistration
     *
     * @return the newly build RegistrationOption for autoRegistration
     * @throws PaymentException when an invalid registration combination is set
     */
    public RegistrationOptions buildRegistrationOptions() throws PaymentException {
        if (TextUtils.isEmpty(operationType)) {
            throw new IllegalStateException("operationType may not be null or empty");
        }
        if (TextUtils.isEmpty(autoRegistration)) {
            throw new IllegalStateException("autoRegistration may not be null or empty");
        }
        if (TextUtils.isEmpty(allowRecurrence)) {
            throw new IllegalStateException("allowRecurrence may not be null or empty");
        }
        RegistrationOptions[] list = (UPDATE.equals(operationType)) ? UPDATE_REGISTRATION_OPTIONS : DEFAULT_REGISTRATION_OPTIONS;
        RegistrationOptions options = getRegistrationOptions(list);

        if (options == null) {
            String message = "Unsupported combination of autoRegistration (" + autoRegistration + ") "
                + "and allowRecurrence (" + allowRecurrence + ") "
                + "for operationType (" + operationType + ")";
            throw new PaymentException(message);
        }
        return options;
    }

    private RegistrationOptions getRegistrationOptions(RegistrationOptions[] registrationOptions) {
        for (RegistrationOptions options : registrationOptions) {
            if (options.matches(autoRegistration, allowRecurrence)) {
                return options;
            }
        }
        return null;
    }

    public RegistrationOptionsBuilder setOperationType(String operationType) {
        this.operationType = operationType;
        return this;
    }

    public RegistrationOptionsBuilder setRegistrationOptions(String autoRegistration, String allowRecurrence) {
        this.autoRegistration = autoRegistration;
        this.allowRecurrence = allowRecurrence;
        return this;
    }
}
