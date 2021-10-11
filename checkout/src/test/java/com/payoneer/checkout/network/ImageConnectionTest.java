/*
 *
 *  * Copyright (c) 2021 Payoneer Germany GmbH
 *  * https://www.payoneer.com
 *  *
 *  * This file is open source and available under the MIT license.
 *  * See the LICENSE file for more information.
 *  *
 */

package com.payoneer.checkout.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.checkout.core.PaymentException;

/**
 * Containing ImageConnection tests
 */
@RunWith(RobolectricTestRunner.class)
public class ImageConnectionTest {

    /**
     * Create ImageConnection and loadBitmap with invalid url
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void loadBitmap_invalidUrl_IllegalArgumentException() throws PaymentException {
        ImageConnection conn = new ImageConnection();
        conn.loadBitmap(null);
    }
}