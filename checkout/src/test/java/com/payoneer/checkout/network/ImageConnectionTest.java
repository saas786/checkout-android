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
import org.junit.jupiter.api.Assertions;

/**
 * Containing ImageConnection tests
 */
public class ImageConnectionTest {

    @Test()
    public void loadBitmap_invalidUrl_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ImageConnection conn = new ImageConnection();
            conn.loadBitmap(null);
        });
    }
}