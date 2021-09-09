/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.localization.LocalizationKey;
import com.payoneer.checkout.ui.model.RegistrationOptions;

/**
 * Widget for showing the RegistrationOptions, e.g. allowRecurrence and autoRegistration
 */
public class RegistrationWidget extends CheckboxWidget {

    public RegistrationWidget(String category, String name) {
        super(category, name);
    }

    /**
     * Bind this RegistrationWidget to the RegistrationOption
     *
     * @param option containing the registration options
     */
    public void onBind(RegistrationOptions option) {
        String label = Localization.translate(LocalizationKey.NETWORKS_REGISTRATION_LABEL);
        super.onBind(option.getCheckboxMode(), label);
    }
}
