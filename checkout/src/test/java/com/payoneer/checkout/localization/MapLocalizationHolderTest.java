/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.localization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MapLocalizationHolderTest {

    @Test
    public void translate() {
        LocalizationHolder holder = LocalizationTest.createMapLocalizationHolder("key", "value", 5);
        assertEquals("value3", holder.translate("key3"));
        assertNull(holder.translate("foo"));
    }
}
