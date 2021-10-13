/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.model.ListResult;
import com.payoneer.checkout.resource.PaymentGroup;
import com.payoneer.checkout.ui.model.PaymentSession;

public class PaymentSessionBuilderTest {

    @Test
    public void build_missingListResult() throws PaymentException {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Map<String, PaymentGroup> paymentGroups = new HashMap<>();
            new PaymentSessionBuilder()
                .setPaymentGroups(paymentGroups)
                .build();
        });
    }

    @Test
    public void build_missingPaymentGroups() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            ListResult listResult = new ListResult();
            new PaymentSessionBuilder()
                .setListResult(listResult)
                .build();
        });
    }

    @Test
    public void build_successful() throws PaymentException {
        ListResult listResult = new ListResult();
        Map<String, PaymentGroup> paymentGroups = new HashMap<>();
        PaymentSession session = new PaymentSessionBuilder()
            .setListResult(listResult)
            .setPaymentGroups(paymentGroups)
            .build();
        assertNotNull(session);
    }
}