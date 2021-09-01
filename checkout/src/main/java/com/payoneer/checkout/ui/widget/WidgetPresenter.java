/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import com.payoneer.checkout.validation.ValidationResult;

import android.view.View;

/**
 * The presenter which is controlling each widget
 */
public interface WidgetPresenter {

    /**
     * Inform the presenter that the Pay Button has been clicked
     */
    void onActionClicked();

    /**
     * Inform the presenter that the hint icon has been clicked for the input field with the given type
     *
     * @param type the type of the widget
     */
    void onHintClicked(String type);

    /**
     * Ask the presenter to hide the keyboard
     */
    void hideKeyboard();

    /**
     * Ask the presenter to show the keyboard
     *
     * @param view for which the keyboard should be shown
     */
    void showKeyboard(View view);

    /**
     * Request focus for the next widget after the current widget.
     *
     * @param currentWidget widget that is currently focused
     * @return true when next widget is focused, false otherwise
     */
    boolean requestFocusNextWidget(FormWidget currentWidget);

    /**
     * Get the max length for the given input type
     *
     * @param code of the network
     * @param type of the input
     * @return the max length or -1 if not available
     */
    int getMaxInputLength(String code, String type);

    /**
     * Widgets call this method to validate their input values. The first value is mandatory, the second is optional.
     * I.e. A Date widget may use both values to validate the month and year values at the same time.
     *
     * @param type type of the value to be validated
     * @param value1 mandatory first value to validate
     * @param value2 optional second value to validate
     * @return ValidationResult holding the result of the validation
     */
    ValidationResult validate(String type, String value1, String value2);

    /**
     * Notify that text has changed in this widget.
     *
     * @param type the type of the TextInput widget
     * @param text new text of the textInput
     */
    void onTextInputChanged(String type, String text);
}
