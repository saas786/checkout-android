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

public class LocalizationConnectionTest {

    @Test
    public void loadLocalizationHolder_invalidURL_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Context context = ApplicationProvider.getApplicationContext();
            LocalizationConnection conn = new LocalizationConnection(context);
            conn.loadLocalization(null);
        });
    }
}
