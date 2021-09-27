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

public class InputElementTypeTest {

    @Test
    public void isInputElementType_invalidValue_false() {
        assertFalse(InputElementType.isValid("foo"));
    }

    @Test
    public void isInputElementType_validValue_true() {
        assertTrue(InputElementType.isValid(InputElementType.STRING));
        assertTrue(InputElementType.isValid(InputElementType.NUMERIC));
        assertTrue(InputElementType.isValid(InputElementType.INTEGER));
        assertTrue(InputElementType.isValid(InputElementType.SELECT));
        assertTrue(InputElementType.isValid(InputElementType.CHECKBOX));
    }
}
