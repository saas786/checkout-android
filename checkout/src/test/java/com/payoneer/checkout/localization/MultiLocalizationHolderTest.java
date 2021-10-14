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
public class MultiLocalizationHolderTest {

    @Test
    public void translate() {
        LocalizationHolder visa = LocalizationTest.createMapLocalizationHolder("visa", "visa", 5);
        LocalizationHolder mastercard = LocalizationTest.createMapLocalizationHolder("mastercard", "mastercard", 5);
        LocalizationHolder jcb = LocalizationTest.createMapLocalizationHolder("jcb", "jcb", 5);
        LocalizationHolder multi = new MultiLocalizationHolder(visa, mastercard, jcb);

        assertEquals("visa2", multi.translate("visa2"));
        assertEquals("mastercard3", multi.translate("mastercard3"));
        assertEquals("jcb1", multi.translate("jcb1"));
        assertNull(multi.translate("foo"));
    }
}
