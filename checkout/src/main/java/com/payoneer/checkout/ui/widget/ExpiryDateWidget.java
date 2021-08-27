/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.model.InputElement;
import com.payoneer.checkout.ui.widget.input.ExpiryDateInputMode;
import com.payoneer.checkout.util.PaymentUtils;
import com.payoneer.checkout.validation.ValidationResult;

import android.text.TextUtils;

/**
 * Widget for handling the date input
 */
public final class ExpiryDateWidget extends InputLayoutWidget {

    private InputElement monthElement;
    private InputElement yearElement;

    /**
     * Construct a new DateWidget
     *
     * @param name name identifying this widget
     */
    public ExpiryDateWidget(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        ExpiryDate date = getExpiryDate();
        ValidationResult result = presenter.validate(name, date.month, date.year);
        return setValidationResult(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {
        ExpiryDate date = getExpiryDate();
        operation.putInputElementStringValue(monthElement.getName(), date.month);
        operation.putInputElementStringValue(yearElement.getName(), date.year);
    }

    /**
     * Set the InputElements in this DateWidget
     *
     * @param monthElement expiry month element
     * @param yearElement expiry year element
     */
    public void onBind(String code, InputElement monthElement, InputElement yearElement) {
        setLabel(Localization.translateAccountLabel(code, name));
        setHelperText(Localization.translateAccountPlaceholder(code, name));

        this.monthElement = monthElement;
        this.yearElement = yearElement;
        setTextInputMode(new ExpiryDateInputMode());
        setValidation();
    }

    private ExpiryDate getExpiryDate() {
        String[] split = getValue().split(ExpiryDateInputMode.DIVIDER);
        String month = split.length > 0 ? split[0] : "";
        String year = split.length > 1 ? split[1] : "";

        if (!TextUtils.isEmpty(year)) {
            int expiryYear = PaymentUtils.createExpiryYear(Integer.parseInt(year));
            year = Integer.toString(expiryYear);
        }
        return new ExpiryDate(month, year);
    }

    private static class ExpiryDate {
        final String month;
        final String year;

        ExpiryDate(String month, String year) {
            this.month = month;
            this.year = year;
        }
    }
}
