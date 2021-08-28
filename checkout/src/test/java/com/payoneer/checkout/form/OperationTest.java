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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.core.PaymentInputType;
import com.payoneer.checkout.model.BrowserData;
import com.payoneer.checkout.test.util.TestUtils;

@RunWith(RobolectricTestRunner.class)
public class OperationTest {

    @BeforeClass
    public static void beforeAll() {
        start();
    }

    @AfterClass
    public static void afterAll() {
        validateSnapshots();
    }

    @Test(expected = IllegalArgumentException.class)
    public void putValue_invalidName_exception() throws PaymentException {
        URL url = TestUtils.createTestURL("http://localhost/charge");
        Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", url);
        operation.putInputElementStringValue(null, "Foo");
    }

    @Test
    public void putValue_success() throws PaymentException {
        URL url = TestUtils.createTestURL("http://localhost/charge");
        Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", url);
        operation.putInputElementStringValue(PaymentInputType.HOLDER_NAME, "John Doe");
        operation.putInputElementStringValue(PaymentInputType.ACCOUNT_NUMBER, "accountnumber123");
        operation.putInputElementStringValue(PaymentInputType.BANK_CODE, "bankcode123");
        operation.putInputElementStringValue(PaymentInputType.BANK_NAME, "bankname123");
        operation.putInputElementStringValue(PaymentInputType.BIC, "bic123");
        operation.putInputElementStringValue(PaymentInputType.BRANCH, "branch123");
        operation.putInputElementStringValue(PaymentInputType.CITY, "city123");
        operation.putInputElementStringValue(PaymentInputType.EXPIRY_MONTH, "12");
        operation.putInputElementStringValue(PaymentInputType.EXPIRY_YEAR, "2019");
        operation.putInputElementStringValue(PaymentInputType.IBAN, "iban123");
        operation.putInputElementStringValue(PaymentInputType.LOGIN, "login123");
        operation.putInputElementStringValue(PaymentInputType.PASSWORD, "password123");
        operation.putInputElementStringValue(PaymentInputType.VERIFICATION_CODE, "123");
        operation.putInputElementStringValue(PaymentInputType.CUSTOMER_BIRTHDAY, "3");
        operation.putInputElementStringValue(PaymentInputType.CUSTOMER_BIRTHMONTH, "12");
        operation.putInputElementStringValue(PaymentInputType.CUSTOMER_BIRTHYEAR, "72");
        operation.putInputElementStringValue(PaymentInputType.INSTALLMENT_PLANID, "72");
        operation.putInputElementBooleanValue(PaymentInputType.OPTIN, true);
        
        operation.putRegistrationBooleanValue(PaymentInputType.ALLOW_RECURRENCE, true);
        operation.putRegistrationBooleanValue(PaymentInputType.AUTO_REGISTRATION, true);

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

