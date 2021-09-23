/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.theme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.payoneer.checkout.R;
import com.payoneer.checkout.ui.PaymentTheme;

public class PaymentThemeTest {

    @Test
    public void createBuilder() {
        PaymentTheme.Builder builder = PaymentTheme.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        PaymentTheme theme = PaymentTheme.createDefault();
        assertNotNull(theme);

        assertEquals(theme.getPaymentListTheme(), R.style.PaymentTheme_Toolbar);
        assertEquals(theme.getChargePaymentTheme(), R.style.PaymentTheme_NoToolbar);
    }

    @Test
    public void getPaymentListTheme() {
        int value = R.style.PaymentTheme;
        PaymentTheme theme = PaymentTheme.createBuilder().
            setPaymentListTheme(value).build();
        assertEquals(theme.getPaymentListTheme(), value);
    }

    @Test
    public void getChargePaymentTheme() {
        int value = R.style.PaymentTheme;
        PaymentTheme theme = PaymentTheme.createBuilder().
            setChargePaymentTheme(value).build();
        assertEquals(theme.getChargePaymentTheme(), value);
    }
}
