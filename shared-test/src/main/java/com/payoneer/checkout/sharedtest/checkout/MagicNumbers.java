/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.sharedtest.checkout;

import java.math.BigDecimal;

/**
 * Class containing magic numbers for generating different responses from the TESTPSP
 */
public class MagicNumbers {

    public final static BigDecimal CHARGE_PROCEED_OK = BigDecimal.valueOf(1.01);
    public final static BigDecimal CHARGE_PROCEED_PENDING = BigDecimal.valueOf(1.04);

    public final static BigDecimal CHARGE_RETRY = BigDecimal.valueOf(1.03);
    public final static BigDecimal CHARGE_TRY_OTHER_NETWORK = BigDecimal.valueOf(1.20);
    public final static BigDecimal CHARGE_TRY_OTHER_ACCOUNT = BigDecimal.valueOf(1.21);

    public final static BigDecimal UPDATE_PROCEED_PENDING = BigDecimal.valueOf(7.51);
    public final static BigDecimal THREE3DS2 = BigDecimal.valueOf(1.23);

}
