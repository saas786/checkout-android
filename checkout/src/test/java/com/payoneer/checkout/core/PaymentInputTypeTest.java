/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PaymentInputTypeTest {

    @Test
    public void isPaymentInputType_invalidValue_false() {
        assertFalse(PaymentInputType.isValid("foo"));
    }

    @Test
    public void isPaymentInputType_validValue_true() {
        assertTrue(PaymentInputType.isValid(PaymentInputType.HOLDER_NAME));
        assertTrue(PaymentInputType.isValid(PaymentInputType.ACCOUNT_NUMBER));
        assertTrue(PaymentInputType.isValid(PaymentInputType.BANK_CODE));
        assertTrue(PaymentInputType.isValid(PaymentInputType.BANK_NAME));
        assertTrue(PaymentInputType.isValid(PaymentInputType.BIC));
        assertTrue(PaymentInputType.isValid(PaymentInputType.BRANCH));
        assertTrue(PaymentInputType.isValid(PaymentInputType.CITY));
        assertTrue(PaymentInputType.isValid(PaymentInputType.EXPIRY_MONTH));
        assertTrue(PaymentInputType.isValid(PaymentInputType.EXPIRY_YEAR));
        assertTrue(PaymentInputType.isValid(PaymentInputType.EXPIRY_DATE));
        assertTrue(PaymentInputType.isValid(PaymentInputType.IBAN));
        assertTrue(PaymentInputType.isValid(PaymentInputType.LOGIN));
        assertTrue(PaymentInputType.isValid(PaymentInputType.OPTIN));
        assertTrue(PaymentInputType.isValid(PaymentInputType.PASSWORD));
        assertTrue(PaymentInputType.isValid(PaymentInputType.VERIFICATION_CODE));
        assertTrue(PaymentInputType.isValid(PaymentInputType.CUSTOMER_BIRTHDAY));
        assertTrue(PaymentInputType.isValid(PaymentInputType.CUSTOMER_BIRTHMONTH));
        assertTrue(PaymentInputType.isValid(PaymentInputType.CUSTOMER_BIRTHYEAR));
        assertTrue(PaymentInputType.isValid(PaymentInputType.INSTALLMENT_PLANID));
        assertTrue(PaymentInputType.isValid(PaymentInputType.ALLOW_RECURRENCE));
        assertTrue(PaymentInputType.isValid(PaymentInputType.AUTO_REGISTRATION));
    }
}
