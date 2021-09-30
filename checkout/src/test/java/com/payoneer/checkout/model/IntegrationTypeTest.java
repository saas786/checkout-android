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

public class IntegrationTypeTest {

    @Test
    public void isIntegrationType_invalidValue_false() {
        assertFalse(IntegrationType.isValid("foo"));
    }

    @Test
    public void isIntegrationType_validValue_true() {
        assertTrue(IntegrationType.isValid(IntegrationType.MOBILE_NATIVE));
        assertTrue(IntegrationType.isValid(IntegrationType.DISPLAY_NATIVE));
        assertTrue(IntegrationType.isValid(IntegrationType.SELECTIVE_NATIVE));
        assertTrue(IntegrationType.isValid(IntegrationType.PURE_NATIVE));
        assertTrue(IntegrationType.isValid(IntegrationType.HOSTED));
    }
}
