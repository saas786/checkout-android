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

public class InteractionCodeTest {

    @Test
    public void isInteractionCode_invalidValue_false() {
        assertFalse(InteractionCode.isValid("foo"));
    }

    @Test
    public void isInteractionCode_validValue_true() {
        assertTrue(InteractionCode.isValid(InteractionCode.PROCEED));
        assertTrue(InteractionCode.isValid(InteractionCode.ABORT));
        assertTrue(InteractionCode.isValid(InteractionCode.TRY_OTHER_NETWORK));
        assertTrue(InteractionCode.isValid(InteractionCode.TRY_OTHER_ACCOUNT));
        assertTrue(InteractionCode.isValid(InteractionCode.RETRY));
        assertTrue(InteractionCode.isValid(InteractionCode.RELOAD));
        assertTrue(InteractionCode.isValid(InteractionCode.VERIFY));
    }
}
