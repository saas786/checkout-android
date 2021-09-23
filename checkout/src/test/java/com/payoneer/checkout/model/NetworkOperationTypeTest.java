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

public class NetworkOperationTypeTest {

    @Test
    public void isNetworkOperationType_invalidValue_false() {
        assertFalse(NetworkOperationType.isValid("foo"));
    }

    @Test
    public void isNetworkOperationType_validValue_true() {
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.CHARGE));
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.PRESET));
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.PAYOUT));
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.UPDATE));
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.ACTIVATION));
    }
}
