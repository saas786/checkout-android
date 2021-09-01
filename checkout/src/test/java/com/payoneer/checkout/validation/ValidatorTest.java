/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.checkout.R;
import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.core.PaymentInputType;
import com.payoneer.checkout.core.PaymentNetworkCodes;
import com.payoneer.checkout.model.PaymentMethod;
import com.payoneer.checkout.resource.ResourceLoader;

import android.content.res.Resources;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class ValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void createInstance_invalidContext() {
        new Validator(null);
    }

    @Test(expected = PaymentException.class)
    public void createInstance_invalidResource() throws PaymentException {
        createValidator(0);
    }

    @Test
    public void createInstance_success() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        assertNotNull(validator);
    }

    @Test
    public void getMaxInputLength_from_resource() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        assertEquals(3, validator.getMaxInputLength(PaymentNetworkCodes.VISA, PaymentInputType.VERIFICATION_CODE));
    }

    @Test
    public void getMaxInputLength_default() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        assertEquals(Validator.MAXLENGTH_VERIFICATION_CODE, validator.getMaxInputLength("FOO", PaymentInputType.VERIFICATION_CODE));
    }

    @Test
    public void isHidden_SEPADD_bic() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        assertTrue(validator.isHidden(PaymentNetworkCodes.SEPADD, PaymentInputType.BIC));
    }

    @Test
    public void isHidden_CREDIT_VISA_holderName() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        assertFalse(validator.isHidden(PaymentNetworkCodes.VISA, PaymentInputType.HOLDER_NAME));
    }


    @Test(expected = IllegalArgumentException.class)
    public void validate_invalidMethod() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        validator.validate(null, PaymentNetworkCodes.VISA, PaymentInputType.ACCOUNT_NUMBER, "4111111111111111", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_invalidType() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        validator.validate(PaymentMethod.CREDIT_CARD, PaymentNetworkCodes.VISA, null, "4111111111111111", null);
    }

    @Test
    public void validate_accountNumber_missingValidation() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "12345", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12345ABC", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_cardNumber_missingValidation() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = "code";
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "36699", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12345", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_verificationCode_missingValidation() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456789", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }

    @Test
    public void validate_CREDIT_AMEX_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.AMEX;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "373051954985299", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_AMEX_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.AMEX;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "1234", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "123", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_CASTORAMA_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.CASTORAMA;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4111111111111111", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_CASTORAMA_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.CASTORAMA;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "1234", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "123", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_DINERS_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.DINERS;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "30282713214300", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_DINERS_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.DINERS;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_DISCOVER_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.DISCOVER;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "6011548597185331", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_DISCOVER_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.DISCOVER;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_MASTERCARD_validation() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.MASTERCARD;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "5290836048016633", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_MASTERCARD_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.MASTERCARD;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }


    @Test
    public void validate_CREDIT_UNIONPAY_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.UNIONPAY;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "62123456789000003", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_UNIONPAY_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.UNIONPAY;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_VISA_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.VISA;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4556260657599841", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_VISA_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.VISA;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_VISA_DANKORT_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.VISA_DANKORT;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4917300800000000", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_VISA_DANKORT_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentNetworkCodes.VISA_DANKORT;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_DEBIT_VISAELECTRON_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentNetworkCodes.VISAELECTRON;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4917300800000000", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_VISAELECTRON_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentNetworkCodes.VISAELECTRON;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_DEBIT_CARTEBANCAIRE_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentNetworkCodes.CARTEBANCAIRE;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4035501000000008", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_CARTEBANCAIRE_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentNetworkCodes.CARTEBANCAIRE;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123ABC", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }

    @Test
    public void validate_DEBIT_MAESTRO_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentNetworkCodes.MAESTRO;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_MAESTRO_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentNetworkCodes.MAESTRO;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123ABC", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }

    @Test
    public void validate_DEBIT_MAESTROUK_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentNetworkCodes.MAESTROUK;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_MAESTROUK_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentNetworkCodes.MAESTROUK;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123ABC", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }

    @Test
    public void validate_HolderName() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.HOLDER_NAME;

        ValidationResult result;
        result = validator.validate(method, code, type, "John Doe", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_HOLDER_NAME, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_HOLDER_NAME, result.getError());
    }

    @Test
    public void validateExpiryDate() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.EXPIRY_DATE;

        ValidationResult result;
        result = validator.validate(method, code, type, "04", "2030");
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "3", "2017");
        assertEquals(ValidationResult.INVALID_EXPIRY_DATE, result.getError());

        result = validator.validate(method, code, type, "04", null);
        assertEquals(ValidationResult.MISSING_EXPIRY_DATE, result.getError());

        result = validator.validate(method, code, type, null, "2020");
        assertEquals(ValidationResult.MISSING_EXPIRY_DATE, result.getError());

        result = validator.validate(method, code, type, "04", "1999");
        assertEquals(ValidationResult.INVALID_EXPIRY_DATE, result.getError());

        int year = Calendar.getInstance().get(Calendar.YEAR);
        result = validator.validate(method, code, type, "04", Integer.toString(year + 50));
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "04", Integer.toString(year + 51));
        assertEquals(ValidationResult.INVALID_EXPIRY_DATE, result.getError());
    }

    @Test
    public void validateExpiryMonth() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.EXPIRY_MONTH;

        ValidationResult result;
        result = validator.validate(method, code, type, "01", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "13", null);
        assertEquals(ValidationResult.INVALID_EXPIRY_MONTH, result.getError());

        result = validator.validate(method, code, type, "January", null);
        assertEquals(ValidationResult.INVALID_EXPIRY_MONTH, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_EXPIRY_MONTH, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_EXPIRY_MONTH, result.getError());

    }

    @Test
    public void validateExpiryYear() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.EXPIRY_YEAR;

        ValidationResult result;
        result = validator.validate(method, code, type, "2018", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "18", null);
        assertEquals(ValidationResult.INVALID_EXPIRY_YEAR, result.getError());

        result = validator.validate(method, code, type, "abc", null);
        assertEquals(ValidationResult.INVALID_EXPIRY_YEAR, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_EXPIRY_YEAR, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_EXPIRY_YEAR, result.getError());
    }

    @Test
    public void validateBankCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.BANK_CODE;

        ValidationResult result;
        result = validator.validate(method, code, type, "abcd", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_BANK_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_BANK_CODE, result.getError());
    }

    @Test
    public void validateIban() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.IBAN;

        ValidationResult result;
        result = validator.validate(method, code, type, "AT022050302101023600", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "ABCD", null);
        assertEquals(ValidationResult.INVALID_IBAN, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_IBAN, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_IBAN, result.getError());
    }

    @Test
    public void validateBic() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.BIC;

        ValidationResult result;
        result = validator.validate(method, code, type, "AABSDE31XXX", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "ABCD", null);
        assertEquals(ValidationResult.INVALID_BIC, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_BIC, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_BIC, result.getError());
    }

    private Validator createValidator(int resId) throws PaymentException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        return new Validator(ResourceLoader.loadValidations(res, resId));
    }
}
