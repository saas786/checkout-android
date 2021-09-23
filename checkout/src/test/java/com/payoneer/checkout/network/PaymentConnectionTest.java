/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.network;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import androidx.test.core.app.ApplicationProvider;

public class PaymentConnectionTest {

    @Test
    public void createOperation_invalidData_exception() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PaymentConnection conn = new PaymentConnection(ApplicationProvider.getApplicationContext());
            conn.postOperation(null);
        });
    }
}
