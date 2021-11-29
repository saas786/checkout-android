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
        values.put("inputelement.number", "4111111111111111");
        values.put("inputelement.expiryDate", "1245");
        values.put("inputelement.verificationCode", "123");
        values.put("inputelement.holderName", "Thomas Smith");
        return values;
    }

    public static Map<String, String> amexCardTestData() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("inputelement.number", "370000000000002");
        values.put("inputelement.expiryDate", "0330");
        values.put("inputelement.verificationCode", "7373");
        values.put("inputelement.holderName", "Thomas Smith");
        return values;
    }

    public static Map<String, String> riskDeniedCardTestData() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("inputelement.number", "5105105105105100");
        values.put("inputelement.expiryDate", "0330");
        values.put("inputelement.verificationCode", "333");
        values.put("inputelement.holderName", "John Doe");
        return values;
    }

    public static Map<String, String> sepaTestData() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("inputelement.iban", "NL69INGB0123456789");
        values.put("inputelement.holderName", "John Doe");
        return values;
    }
}
