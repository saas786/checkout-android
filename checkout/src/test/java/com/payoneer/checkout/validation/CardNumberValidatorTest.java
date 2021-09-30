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

import org.junit.jupiter.api.Test;

public class CardNumberValidatorTest {

    @Test
    public void isValidLuhn() {
        // visa
        assertTrue(CardNumberValidator.isValidLuhn("4556260657599841"));
        // mastercard
        assertTrue(CardNumberValidator.isValidLuhn("5386813397330555"));
        // American Express
        assertTrue(CardNumberValidator.isValidLuhn("6011206880824187"));
        // discover
        assertTrue(CardNumberValidator.isValidLuhn("376848137807457"));
        // JCB
        assertTrue(CardNumberValidator.isValidLuhn("3540173304463363"));
        // Diners club - North America 
        assertTrue(CardNumberValidator.isValidLuhn("5545674672092247"));
        // Diners Club - Card Blanche
        assertTrue(CardNumberValidator.isValidLuhn("30121723749105"));
        // Diners Club - International 
        assertTrue(CardNumberValidator.isValidLuhn("36394455633086"));
        // Maestro
        assertTrue(CardNumberValidator.isValidLuhn("6762174698488713"));
        // Visa Electron
        assertTrue(CardNumberValidator.isValidLuhn("4917582391037711"));
        // InstaPayment
        assertTrue(CardNumberValidator.isValidLuhn("6393363361928900"));

        // these should fail
        assertFalse(CardNumberValidator.isValidLuhn(null));
        assertFalse(CardNumberValidator.isValidLuhn(""));
        assertFalse(CardNumberValidator.isValidLuhn("1234567890123456"));
    }

}
