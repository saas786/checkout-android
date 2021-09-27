/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class IbanValidatorTest {

    @Test
    public void isValidIban() {
        assertTrue(IbanValidator.isValidIban("DE27100777770209299700"));
        assertTrue(IbanValidator.isValidIban("AT022050302101023600"));

        assertFalse(IbanValidator.isValidIban(null));
        assertFalse(IbanValidator.isValidIban(""));
        assertFalse(IbanValidator.isValidIban("AT1234567890123456"));
    }
}
