/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RegistrationTypeTest {

    @Test
    public void isRegistrationType_invalidValue_false() {
        assertFalse(RegistrationType.isValid("foo"));
    }

    @Test
    public void isRegistrationType_validValue_true() {
        assertTrue(RegistrationType.isValid(RegistrationType.NONE));
        assertTrue(RegistrationType.isValid(RegistrationType.OPTIONAL));
        assertTrue(RegistrationType.isValid(RegistrationType.FORCED));
        assertTrue(RegistrationType.isValid(RegistrationType.OPTIONAL_PRESELECTED));
        assertTrue(RegistrationType.isValid(RegistrationType.FORCED_DISPLAYED));
    }
}
