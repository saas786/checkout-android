/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import static com.payoneer.checkout.model.RegistrationType.FORCED;
import static com.payoneer.checkout.model.RegistrationType.FORCED_DISPLAYED;
import static com.payoneer.checkout.model.RegistrationType.NONE;
import static com.payoneer.checkout.model.RegistrationType.OPTIONAL;
import static com.payoneer.checkout.model.RegistrationType.OPTIONAL_PRESELECTED;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.model.CheckboxMode;
import com.payoneer.checkout.model.NetworkOperationType;
import com.payoneer.checkout.model.RegistrationType;
import com.payoneer.checkout.ui.model.RegistrationOptions;

@RunWith(RobolectricTestRunner.class)
public class RegistrationOptionsBuilderTest {

    private final static String[][] DEFAULT_REGISTRATION_OPTIONS = {
        { NONE, NONE, CheckboxMode.NONE },
        { FORCED, NONE, CheckboxMode.NONE },
        { FORCED_DISPLAYED, NONE, CheckboxMode.FORCED_DISPLAYED },
        { FORCED, FORCED, CheckboxMode.NONE },
        { FORCED_DISPLAYED, FORCED_DISPLAYED, CheckboxMode.FORCED_DISPLAYED },
        { OPTIONAL, NONE, CheckboxMode.OPTIONAL },
        { OPTIONAL_PRESELECTED, NONE, CheckboxMode.OPTIONAL_PRESELECTED },
        { OPTIONAL, OPTIONAL, CheckboxMode.OPTIONAL },
        { OPTIONAL_PRESELECTED, OPTIONAL_PRESELECTED, CheckboxMode.OPTIONAL_PRESELECTED }
    };

    private final static String[][] UPDATE_REGISTRATION_OPTIONS = {
        { NONE, NONE, CheckboxMode.NONE },
        { FORCED, NONE, CheckboxMode.NONE },
        { FORCED_DISPLAYED, NONE, CheckboxMode.NONE },
        { FORCED, FORCED, CheckboxMode.NONE },
        { FORCED_DISPLAYED, FORCED_DISPLAYED, CheckboxMode.NONE },
        { OPTIONAL, NONE, CheckboxMode.NONE },
        { OPTIONAL_PRESELECTED, NONE, CheckboxMode.NONE },
        { OPTIONAL, OPTIONAL, CheckboxMode.NONE },
        { OPTIONAL_PRESELECTED, OPTIONAL_PRESELECTED, CheckboxMode.NONE }
    };

    @Test(expected = IllegalStateException.class)
    public void buildRegistrationOptions_missingOperationType() throws PaymentException {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder();
        builder.setRegistrationOptions(RegistrationType.OPTIONAL, RegistrationType.OPTIONAL);
        builder.buildRegistrationOptions();
    }

    @Test(expected = IllegalStateException.class)
    public void buildRegistrationOptions_missingRegistrationOptions() throws PaymentException {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder();
        builder.setOperationType(NetworkOperationType.UPDATE);
        builder.buildRegistrationOptions();
    }

    @Test(expected = PaymentException.class)
    public void buildRegistrationOptions_invalidRegistrationOptions() throws PaymentException {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder();
        builder.setOperationType(NetworkOperationType.UPDATE);
        builder.setRegistrationOptions(RegistrationType.NONE, RegistrationType.OPTIONAL);
        builder.buildRegistrationOptions();
    }

    @Test
    public void buildRegistrationOptions_validDefaultRegistrationOptions() throws PaymentException {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder();
        builder.setOperationType(NetworkOperationType.CHARGE);

        for (String[] registrationOptions : DEFAULT_REGISTRATION_OPTIONS) {
            testValidRegistrationOptions(builder, registrationOptions);
        }
    }

    @Test
    public void buildRegistrationOptions_validUpdateRegistrationOptions() throws PaymentException {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder();
        builder.setOperationType(NetworkOperationType.UPDATE);

        for (String[] registrationOptions : UPDATE_REGISTRATION_OPTIONS) {
            testValidRegistrationOptions(builder, registrationOptions);
        }
    }

    private void testValidRegistrationOptions(RegistrationOptionsBuilder builder, String[] registrationOptions) throws PaymentException {
        builder.setRegistrationOptions(registrationOptions[0], registrationOptions[1]);
        RegistrationOptions options = builder.buildRegistrationOptions();

        String autoRegistration = registrationOptions[0];
        String allowRecurrence = registrationOptions[1];
        String checkboxMode = registrationOptions[2];

        assertEquals("Expect autoRegistration: " + autoRegistration, autoRegistration, options.getAutoRegistration());
        assertEquals("Expect allowRecurrence: " + allowRecurrence, allowRecurrence, options.getAllowRecurrence());
        assertEquals("Expect checkboxMode: " + checkboxMode, checkboxMode, options.getCheckboxMode());
    }
}
