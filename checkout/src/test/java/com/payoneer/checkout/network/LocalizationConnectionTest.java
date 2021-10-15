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
 * The type Localization connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class LocalizationConnectionTest {

    /**
     * Gets LocalizationHolder with invalid URL
     */
    @Test(expected = IllegalArgumentException.class)
    public void loadLocalizationHolder_invalidURL_IllegalArgumentException() throws PaymentException {
        Context context = ApplicationProvider.getApplicationContext();
        LocalizationConnection conn = new LocalizationConnection(context);
        conn.loadLocalization(null);
    }
}
