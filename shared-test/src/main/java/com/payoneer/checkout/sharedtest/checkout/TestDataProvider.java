/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.sharedtest.checkout;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class for providing test card data
 */
public final class TestDataProvider {

    public static Map<String, String> visaCardTestData() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("number", "4111111111111111");
        values.put("expiryDate", "1245");
        values.put("verificationCode", "123");
        values.put("holderName", "Thomas Smith");
        return values;
    }

    public static Map<String, String> riskDeniedCardTestData() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("number", "5105105105105100");
        values.put("expiryDate", "0330");
        values.put("verificationCode", "333");
        values.put("holderName", "John Doe");
        return values;
    }

    public static Map<String, String> sepaTestData() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("iban", "NL69INGB0123456789");
        values.put("holderName", "John Doe");
        return values;
    }
}
