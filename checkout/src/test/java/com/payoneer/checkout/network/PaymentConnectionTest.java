/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.checkout.core.PaymentException;

import androidx.test.core.app.ApplicationProvider;

/**
 * The type Operation connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class PaymentConnectionTest {

    /**
     * Post operation invalid data invalid value error.
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createOperation_invalidData_exception() throws PaymentException {
        PaymentConnection conn = new PaymentConnection(ApplicationProvider.getApplicationContext());
        conn.postOperation(null);
    }
}
