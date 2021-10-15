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

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

/**
 * The type List connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class ListConnectionTest {

    /**
     * Create payment session invalid baseUrl
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createPaymentSession_invalidBaseUrl_IllegalArgumentException() throws PaymentException {
        ListConnection conn = createListConnection();
        conn.createPaymentSession(null, "auth123", "{}");
    }

    /**
     * Create payment session invalid authorization
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createPaymentSession_invalidAuthorization_IllegalArgumentException() throws PaymentException {
        ListConnection conn = createListConnection();
        conn.createPaymentSession("http://localhost", null, "{}");
    }

    /**
     * Create payment session invalid list data
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createPaymentSession_invalidListData_IllegalArgumentException() throws PaymentException {
        ListConnection conn = createListConnection();
        conn.createPaymentSession("http://localhost", "auth123", "");
    }

    /**
     * Gets list result invalid url
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void getListResult_invalidURL_IllegalArgumentException() throws PaymentException {
        ListConnection conn = createListConnection();
        conn.getListResult(null);
    }

    private ListConnection createListConnection() {
        Context context = ApplicationProvider.getApplicationContext();
        return new ListConnection(context);
    }
}
