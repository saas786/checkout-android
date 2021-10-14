/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PaymentNetworkCodesTest {

    @Test
    public void isPaymentMethodCode_invalidValue_false() {
        assertFalse(PaymentNetworkCodes.isValid("foo"));
    }

    @Test
    public void isPaymentMethodCode_validValue_true() {
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.AMEX));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.CASTORAMA));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.DINERS));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.DISCOVER));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.MASTERCARD));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.UNIONPAY));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.VISA));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.VISA_DANKORT));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.VISAELECTRON));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.CARTEBANCAIRE));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.MAESTRO));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.MAESTROUK));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.POSTEPAY));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.SEPADD));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.GOOGLEPAY));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.PAYPAL));
        assertTrue(PaymentNetworkCodes.isValid(PaymentNetworkCodes.WECHATPC_R));
    }
}
