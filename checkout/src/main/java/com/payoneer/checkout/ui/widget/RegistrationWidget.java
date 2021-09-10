/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import static com.payoneer.checkout.core.PaymentInputType.ALLOW_RECURRENCE;
import static com.payoneer.checkout.core.PaymentInputType.AUTO_REGISTRATION;
import static com.payoneer.checkout.model.RegistrationType.FORCED;
import static com.payoneer.checkout.model.RegistrationType.FORCED_DISPLAYED;
import static com.payoneer.checkout.model.RegistrationType.OPTIONAL;
import static com.payoneer.checkout.model.RegistrationType.OPTIONAL_PRESELECTED;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.localization.LocalizationKey;
import com.payoneer.checkout.ui.model.RegistrationOptions;

/**
 * Widget for showing the RegistrationOptions, e.g. allowRecurrence and autoRegistration
 */
public class RegistrationWidget extends CheckboxWidget {

    private RegistrationOptions registrationOptions;

    public RegistrationWidget(String category, String name) {
        super(category, name);
    }

    @Override
    public void putValue(Operation operation) throws PaymentException {
        putRegistrationValue(operation, AUTO_REGISTRATION, registrationOptions.getAutoRegistration());
        putRegistrationValue(operation, ALLOW_RECURRENCE, registrationOptions.getAllowRecurrence());
    }

    private void putRegistrationValue(Operation operation, String registrationName, String registrationOption) throws PaymentException {
        switch (registrationOption) {
            case FORCED:
            case FORCED_DISPLAYED:
                operation.putBooleanValue(category, registrationName, true);
                break;
            case OPTIONAL:
            case OPTIONAL_PRESELECTED:
                operation.putBooleanValue(category, registrationName, switchView.isChecked());
        }
    }

    /**
     * Bind this RegistrationWidget to the RegistrationOption
     *
     * @param registrationOptions containing the registration options
     */
    public void onBind(RegistrationOptions registrationOptions) {
        this.registrationOptions = registrationOptions;
        String label = Localization.translate(LocalizationKey.NETWORKS_REGISTRATION_LABEL);
        super.onBind(registrationOptions.getCheckboxMode(), label);
    }
}
