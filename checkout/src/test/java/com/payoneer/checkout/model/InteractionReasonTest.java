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

public class InteractionReasonTest {

    @Test
    public void isInteractionReason_invalidValue_false() {
        assertFalse(InteractionReason.isValid("foo"));
    }

    @Test
    public void isInteractionReason_validValue_true() {
        assertTrue(InteractionReason.isValid(InteractionReason.OK));
        assertTrue(InteractionReason.isValid(InteractionReason.PENDING));
        assertTrue(InteractionReason.isValid(InteractionReason.STRONG_AUTHENTICATION));
        assertTrue(InteractionReason.isValid(InteractionReason.DECLINED));
        assertTrue(InteractionReason.isValid(InteractionReason.EXCEEDS_LIMIT));
        assertTrue(InteractionReason.isValid(InteractionReason.TEMPORARY_FAILURE));
        assertTrue(InteractionReason.isValid(InteractionReason.NETWORK_FAILURE));
        assertTrue(InteractionReason.isValid(InteractionReason.BLACKLISTED));
        assertTrue(InteractionReason.isValid(InteractionReason.BLOCKED));
        assertTrue(InteractionReason.isValid(InteractionReason.SYSTEM_FAILURE));
        assertTrue(InteractionReason.isValid(InteractionReason.INVALID_ACCOUNT));
        assertTrue(InteractionReason.isValid(InteractionReason.FRAUD));
        assertTrue(InteractionReason.isValid(InteractionReason.ADDITIONAL_NETWORKS));
        assertTrue(InteractionReason.isValid(InteractionReason.INVALID_REQUEST));
        assertTrue(InteractionReason.isValid(InteractionReason.SCHEDULED));
        assertTrue(InteractionReason.isValid(InteractionReason.NO_NETWORKS));
        assertTrue(InteractionReason.isValid(InteractionReason.DUPLICATE_OPERATION));
        assertTrue(InteractionReason.isValid(InteractionReason.CHARGEBACK));
        assertTrue(InteractionReason.isValid(InteractionReason.RISK_DETECTED));
        assertTrue(InteractionReason.isValid(InteractionReason.CUSTOMER_ABORT));
        assertTrue(InteractionReason.isValid(InteractionReason.EXPIRED_SESSION));
        assertTrue(InteractionReason.isValid(InteractionReason.EXPIRED_ACCOUNT));
        assertTrue(InteractionReason.isValid(InteractionReason.ACCOUNT_NOT_ACTIVATED));
        assertTrue(InteractionReason.isValid(InteractionReason.TRUSTED_CUSTOMER));
        assertTrue(InteractionReason.isValid(InteractionReason.UNKNOWN_CUSTOMER));
        assertTrue(InteractionReason.isValid(InteractionReason.ACTIVATED));
        assertTrue(InteractionReason.isValid(InteractionReason.UPDATED));
        assertTrue(InteractionReason.isValid(InteractionReason.TAKE_ACTION));
        assertTrue(InteractionReason.isValid(InteractionReason.COMMUNICATION_FAILURE));
        assertTrue(InteractionReason.isValid(InteractionReason.CLIENTSIDE_ERROR));
    }
}
