/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.validation;

import java.util.Calendar;
import java.util.Map;

import com.payoneer.checkout.core.PaymentInputType;
import com.payoneer.checkout.model.PaymentMethod;
import com.payoneer.checkout.resource.ValidationGroup;

import android.text.TextUtils;
import android.util.Log;

/**
 * Class for validating input type values
 */
public class Validator {

    public final static String REGEX_MONTH = "(^0[1-9]|1[0-2]$)";
    public final static String REGEX_YEAR = "^(20)\\d{2}$";
    public final static String REGEX_BIC = "([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)";
    public final static String REGEX_ACCOUNT_NUMBER = "^[0-9]+$";
    public final static String REGEX_VERIFICATION_CODE = "^[0-9]*$";
    public final static String REGEX_BANK_CODE = "^.+$";

    public final static int MAXLENGTH_DEFAULT = 128;
    public final static int MAXLENGTH_ACCOUNT_NUMBER = 34;
    public final static int MAXLENGTH_VERIFICATION_CODE = 4;
    public final static int MAXLENGTH_IBAN = 34;
    public final static int MAXLENGTH_BIC = 11;
    public final static int MAX_EXPIRY_YEAR = 50;

    private final Map<String, ValidationGroup> validations;
    private static Validator instance;

    /**
     * Construct a new Validator with the provided validations
     *
     * @param validations the list of validations to be used to validate input values
     */
    public Validator(Map<String, ValidationGroup> validations) {
        if (validations == null) {
            throw new IllegalArgumentException("Validations may not be null");
        }
        this.validations = validations;
    }

    /**
     * Get the currently set Validator instance
     *
     * @return the current instance or null if not previously set
     */
    public static Validator getInstance() {
        return instance;
    }

    /**
     * Set the current Validator instance
     *
     * @param newInstance to be set as the current instance
     */
    public static void setInstance(Validator newInstance) {
        instance = newInstance;
    }

    /**
     * Get the validation for the given method, code and type.
     *
     * @param code Payment code like VISA
     * @param type payment input type like "number"
     * @return ValidationGroupItem or null if not found
     */
    public String getValidationRegex(String code, String type) {

        if (code == null || type == null) {
            return null;
        }
        ValidationGroup group = validations.get(code);
        return group != null ? group.getValidationRegex(type) : null;
    }

    public int getMaxInputLength(String code, String type) {

        if (code == null || type == null) {
            return MAXLENGTH_DEFAULT;
        }
        int maxLength = 0;

        ValidationGroup group = validations.get(code);
        if (group != null) {
            maxLength = group.getMaxLength(type);
        }
        if (maxLength > 0) {
            return maxLength;
        }
        switch (type) {
            case PaymentInputType.ACCOUNT_NUMBER:
                return MAXLENGTH_ACCOUNT_NUMBER;
            case PaymentInputType.VERIFICATION_CODE:
                return MAXLENGTH_VERIFICATION_CODE;
            case PaymentInputType.IBAN:
                return MAXLENGTH_IBAN;
            case PaymentInputType.BIC:
                return MAXLENGTH_BIC;
            default:
                return MAXLENGTH_DEFAULT;
        }
    }

    public boolean isHidden(String code, String type) {

        if (code == null || type == null) {
            return false;
        }
        if (!validations.containsKey(code)) {
            return false;
        }
        ValidationGroup group = validations.get(code);
        return group != null && group.isHidden(type);
    }

    /**
     * Validate the given input values defined by its type
     *
     * @param method the Payment method like CREDIT_CARD
     * @param code the payment code like VISA
     * @param type the PaymentInputType like "number"
     * @param value1 holding the mandatory first value for the given input type, may be empty
     * @param value2 holding the optional second value for the given input type
     */
    public ValidationResult validate(String method, String code, String type, String value1, String value2) {

        if (TextUtils.isEmpty(method)) {
            throw new IllegalArgumentException("method may not be null or empty");
        }
        if (TextUtils.isEmpty(code)) {
            throw new IllegalArgumentException("code may not be null or empty");
        }
        if (TextUtils.isEmpty(type)) {
            throw new IllegalArgumentException("type may not be null or empty");
        }
        value1 = value1 == null ? "" : value1;
        value2 = value2 == null ? "" : value2;
        String regex = getValidationRegex(code, type);

        switch (type) {
            case PaymentInputType.ACCOUNT_NUMBER:
                return validateAccountNumber(method, value1, regex);
            case PaymentInputType.VERIFICATION_CODE:
                return validateVerificationCode(value1, regex);
            case PaymentInputType.HOLDER_NAME:
                return validateHolderName(value1);
            case PaymentInputType.BANK_CODE:
                return validateBankCode(value1, regex);
            case PaymentInputType.EXPIRY_DATE:
                return validateExpiryDate(value1, value2);
            case PaymentInputType.EXPIRY_MONTH:
                return validateExpiryMonth(value1);
            case PaymentInputType.EXPIRY_YEAR:
                return validateExpiryYear(value1);
            case PaymentInputType.IBAN:
                return validateIban(value1);
            case PaymentInputType.BIC:
                return validateBic(value1);
            default:
                return new ValidationResult(null);
        }
    }

    private ValidationResult validateAccountNumber(String method, String number, String regex) {
        regex = regex != null ? regex : REGEX_ACCOUNT_NUMBER;

        switch (method) {
            case PaymentMethod.CREDIT_CARD:
            case PaymentMethod.DEBIT_CARD:
                return validateCardNumber(number, regex);
            default:
                if (!number.matches(regex)) {
                    if (TextUtils.isEmpty(number)) {
                        return new ValidationResult(ValidationResult.MISSING_ACCOUNT_NUMBER);
                    }
                    return new ValidationResult(ValidationResult.INVALID_ACCOUNT_NUMBER);
                }
        }
        return new ValidationResult(null);
    }

    private ValidationResult validateCardNumber(String number, String regex) {

        if (!number.matches(regex)) {
            if (TextUtils.isEmpty(number)) {
                return new ValidationResult(ValidationResult.MISSING_ACCOUNT_NUMBER);
            }
            return new ValidationResult(ValidationResult.INVALID_ACCOUNT_NUMBER);
        }
        if (!CardNumberValidator.isValidLuhn(number)) {
            return new ValidationResult(ValidationResult.INVALID_ACCOUNT_NUMBER);
        }
        return new ValidationResult(null);
    }

    private ValidationResult validateVerificationCode(String verificationCode, String regex) {
        regex = regex != null ? regex : REGEX_VERIFICATION_CODE;

        if (!verificationCode.matches(regex)) {
            if (TextUtils.isEmpty(verificationCode)) {
                return new ValidationResult(ValidationResult.MISSING_VERIFICATION_CODE);
            }
            return new ValidationResult(ValidationResult.INVALID_VERIFICATION_CODE);
        }
        return new ValidationResult(null);
    }

    private ValidationResult validateHolderName(String holderName) {
        String error = null;

        if (TextUtils.isEmpty(holderName)) {
            error = ValidationResult.MISSING_HOLDER_NAME;
        } else if (!HolderNameValidator.isValidHolderName(holderName)) {
            error = ValidationResult.INVALID_HOLDER_NAME;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateExpiryDate(String month, String year) {
        String error = null;

        if (TextUtils.isEmpty(month) || TextUtils.isEmpty(year)) {
            error = ValidationResult.MISSING_EXPIRY_DATE;
        } else if (!isValidExpiryDate(month, year)) {
            error = ValidationResult.INVALID_EXPIRY_DATE;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateExpiryMonth(String month) {
        String error = null;

        if (TextUtils.isEmpty(month)) {
            error = ValidationResult.MISSING_EXPIRY_MONTH;
        } else if (!month.matches(REGEX_MONTH)) {
            error = ValidationResult.INVALID_EXPIRY_MONTH;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateExpiryYear(String year) {
        String error = null;

        if (TextUtils.isEmpty(year)) {
            error = ValidationResult.MISSING_EXPIRY_YEAR;
        } else if (!year.matches(REGEX_YEAR)) {
            error = ValidationResult.INVALID_EXPIRY_YEAR;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateBankCode(String bankCode, String regex) {
        regex = regex != null ? regex : REGEX_BANK_CODE;

        if (!bankCode.matches(regex)) {
            if (TextUtils.isEmpty(bankCode)) {
                return new ValidationResult(ValidationResult.MISSING_BANK_CODE);
            }
            return new ValidationResult(ValidationResult.INVALID_BANK_CODE);
        }
        return new ValidationResult(null);
    }

    private ValidationResult validateIban(String iban) {
        String error = null;

        if (TextUtils.isEmpty(iban)) {
            error = ValidationResult.MISSING_IBAN;
        } else if (!IbanValidator.isValidIban(iban)) {
            error = ValidationResult.INVALID_IBAN;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateBic(String bic) {
        String error = null;

        if (TextUtils.isEmpty(bic)) {
            error = ValidationResult.MISSING_BIC;
        } else if (!bic.matches(REGEX_BIC)) {
            error = ValidationResult.INVALID_BIC;
        }
        return new ValidationResult(error);
    }

    private boolean isValidExpiryDate(String month, String year) {

        if (!(month.matches(REGEX_MONTH) && year.matches(REGEX_YEAR))) {
            return false;
        }
        try {
            int expMonth = Integer.parseInt(month);
            int expYear = Integer.parseInt(year);

            Calendar cal = Calendar.getInstance();
            int curMonth = cal.get(Calendar.MONTH) + 1;
            int curYear = cal.get(Calendar.YEAR);

            if (expYear < curYear) {
                return false;
            }
            if (expYear == curYear) {
                return expMonth >= curMonth;
            }
            return expYear <= (curYear + MAX_EXPIRY_YEAR);
        } catch (NumberFormatException e) {
            // this should never happen since the regex makes sure both are integers
            Log.w("sdk_Validator", e);
        }
        return false;
    }
}
