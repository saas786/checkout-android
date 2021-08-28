/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.ui.model.RegistrationOption;

/**
 * Widget for showing the CheckBox input element
 */
public class RegistrationWidget extends CheckboxWidget {

    public RegistrationWidget(String name) {
        super(name);
    }

    @Override
    public void putValue(Operation operation) throws PaymentException {
        operation.putRegistrationBooleanValue(name, switchView.isChecked());
    }

    /**
     * Bind this RegistrationWidget to the RegistrationOption
     *
     * @param option containing the registration settings
     */
    public void onBind(RegistrationOption option) {
        super.onBind(option.getCheckboxMode(), option.getLabel());
    }
}
