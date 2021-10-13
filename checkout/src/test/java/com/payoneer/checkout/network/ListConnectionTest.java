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

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

public class ListConnectionTest {

    @Test
    public void createPaymentSession_invalidBaseUrl_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ListConnection conn = createListConnection();
            conn.createPaymentSession(null, "auth123", "{}");
        });
    }

    @Test
    public void createPaymentSession_invalidAuthorization_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ListConnection conn = createListConnection();
            conn.createPaymentSession("http://localhost", null, "{}");
        });
    }

    @Test
    public void createPaymentSession_invalidListData_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ListConnection conn = createListConnection();
            conn.createPaymentSession("http://localhost", "auth123", "");
        });
    }

    @Test
    public void getListResult_invalidURL_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ListConnection conn = createListConnection();
            conn.getListResult(null);
        });
    }

    private ListConnection createListConnection() {
        Context context = ApplicationProvider.getApplicationContext();
        return new ListConnection(context);
    }
}