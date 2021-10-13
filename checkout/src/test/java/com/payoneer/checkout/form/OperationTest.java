/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.form;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

import java.net.URL;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.core.PaymentInputCategory;
import com.payoneer.checkout.core.PaymentInputType;
import com.payoneer.checkout.model.BrowserData;
import com.payoneer.checkout.test.util.TestUtils;

public class OperationTest {

    @BeforeAll
    public static void beforeAll() {
        start();
    }

    @AfterAll
    public static void afterAll() {
        validateSnapshots();
    }

    @Test
    public void putValue_invalidCategory_exception() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            URL url = TestUtils.createTestURL("http://localhost/charge");
            Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", url);
            operation.putStringValue(null, PaymentInputType.HOLDER_NAME, "Foo");
        });
    }

    @Test
    public void putValue_invalidName_exception() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            URL url = TestUtils.createTestURL("http://localhost/charge");
            Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", url);
            operation.putStringValue(PaymentInputCategory.INPUTELEMENT, null, "Foo");
        });
    }

    @Test
    public void putValue_invalidRegistrationName_exception() {
        Assertions.assertThrows(PaymentException.class, () -> {
            URL url = TestUtils.createTestURL("http://localhost/charge");
            Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", url);
            operation.putBooleanValue(PaymentInputCategory.REGISTRATION, PaymentInputType.HOLDER_NAME, true);
        });
    }

    @Test
    public void putValue_invalidInputElementName_exception() {
        Assertions.assertThrows(PaymentException.class, () -> {
            URL url = TestUtils.createTestURL("http://localhost/charge");
            Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", url);
            operation.putBooleanValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.ALLOW_RECURRENCE, true);
        });
    }

    @Test
    public void putValue_success() throws PaymentException {
        URL url = TestUtils.createTestURL("http://localhost/charge");
        Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", url);
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.HOLDER_NAME, "John Doe");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.ACCOUNT_NUMBER, "accountnumber123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.BANK_CODE, "bankcode123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.BANK_NAME, "bankname123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.BIC, "bic123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.BRANCH, "branch123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.CITY, "city123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.EXPIRY_MONTH, "12");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.EXPIRY_YEAR, "2019");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.IBAN, "iban123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.LOGIN, "login123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.PASSWORD, "password123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.VERIFICATION_CODE, "123");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.CUSTOMER_BIRTHDAY, "3");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.CUSTOMER_BIRTHMONTH, "12");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.CUSTOMER_BIRTHYEAR, "72");
        operation.putStringValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.INSTALLMENT_PLANID, "72");
        operation.putBooleanValue(PaymentInputCategory.INPUTELEMENT, PaymentInputType.OPTIN, true);

        operation.putBooleanValue(PaymentInputCategory.REGISTRATION, PaymentInputType.ALLOW_RECURRENCE, true);
        operation.putBooleanValue(PaymentInputCategory.REGISTRATION, PaymentInputType.AUTO_REGISTRATION, true);

        BrowserData browserData = new BrowserData();
        browserData.setJavaEnabled(true);
        browserData.setLanguage("en");
        browserData.setTimezone("Berlin/Europe");
        browserData.setColorDepth(24);
        browserData.setBrowserScreenWidth(680);
        browserData.setBrowserScreenHeight(760);
        operation.setBrowserData(browserData);

        expect(operation.toJson()).toMatchSnapshot();
    }
}

