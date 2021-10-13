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

public class HttpMethodTest {

    @Test
    public void isHttpMethod_invalidValue_false() {
        assertFalse(HttpMethod.isValid("foo"));
    }

    @Test
    public void isHttpMethod_validValue_true() {
        assertTrue(HttpMethod.isValid(HttpMethod.GET));
        assertTrue(HttpMethod.isValid(HttpMethod.POST));
    }
}
