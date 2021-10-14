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

public class RedirectTypeTest {

    @Test
    public void isRedirectType_invalidValue_false() {
        assertFalse(RedirectType.isValid("foo"));
    }

    @Test
    public void isRedirectType_validValue_true() {
        assertTrue(RedirectType.isValid(RedirectType.SUMMARY));
        assertTrue(RedirectType.isValid(RedirectType.RETURN));
        assertTrue(RedirectType.isValid(RedirectType.CANCEL));
        assertTrue(RedirectType.isValid(RedirectType.PROVIDER));
        assertTrue(RedirectType.isValid(RedirectType.HANDLER3DS2));
    }
}
