/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CheckboxModeTest {

    @Test
    public void isCheckboxMode_invalidValue_false() {
        assertFalse(CheckboxMode.isValid("foo"));
    }

    @Test
    public void isCheckboxMode_validValue_true() {
        assertTrue(CheckboxMode.isValid(CheckboxMode.OPTIONAL));
        assertTrue(CheckboxMode.isValid(CheckboxMode.OPTIONAL_PRESELECTED));
        assertTrue(CheckboxMode.isValid(CheckboxMode.REQUIRED));
        assertTrue(CheckboxMode.isValid(CheckboxMode.REQUIRED_PRESELECTED));
        assertTrue(CheckboxMode.isValid(CheckboxMode.FORCED));
        assertTrue(CheckboxMode.isValid(CheckboxMode.FORCED_DISPLAYED));
    }
}
