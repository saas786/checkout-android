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
import com.payoneer.checkout.util.PaymentUtils;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Base class for all widgets, i.e. ButtonWidget, CheckBoxWidget, TextInputWidget etc.
 */
public abstract class FormWidget {

    final static int VALIDATION_UNKNOWN = 0x00;
    final static int VALIDATION_ERROR = 0x01;
    final static int VALIDATION_OK = 0x02;

    final String category;
    final String name;
    View widgetView;

    WidgetPresenter presenter;
    int state;

    /**
     * Create a new FormWidget with the category and name values
     *
     * @param category defines the category this widget belongs to, e.g. INPUTELEMENT, EXTRAELEMENT, REGISTRATION
     * @param name contains the name of the widget, e.g. number, holderName, verificationCode
     */
    FormWidget(String category, String name) {
        if (TextUtils.isEmpty(category)) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.category = category;
        this.name = name;
    }

    public static String createWidgetKey(String category, String name) {
        if (TextUtils.isEmpty(category)) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return category + "." + name;
    }

    /**
     * Inflate this widget inside the parent view
     *
     * @param parent the parent ViewGroup in which to inflate this widget
     */
    public abstract View inflate(ViewGroup parent);

    /**
     * Set the presenter in this widget, the presenter may be used by this widget i.e. to inform of events or validate input.
     *
     * @param presenter to be set in this widget
     */
    public final void setPresenter(WidgetPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Get the widgetView of this Widget
     *
     * @return the root view of this widget
     */
    public final View getRootView() {
        return widgetView;
    }

    /**
     * Get the key of this widget, this key must be unique between all widgets in the same payment card
     * and is constructed using the category and name
     *
     * @return unique key of this widget
     */
    public final String getKey() {
        return createWidgetKey(category, name);
    }

    /**
     * Get the input category of this widget, i.e. "extraelement", "inputelement" or "registration"
     *
     * @return input categhory of this widget
     */
    public final String getCategory() {
        return category;
    }

    /**
     * Does this FormWidget matches the provide category and name
     *
     * @return true when matches, false otherwise
     */
    public final boolean matches(String category, String name) {
        return this.category.equals(category) && this.name.equals(name);
    }

    /**
     * Get the name of this widget, i.e. "number", "iban" or "bic"
     *
     * @return input name of this widget
     */
    public final String getName() {
        return name;
    }

    /**
     * Set this widget to be the last ImeOptions if it supports ImeOptions.
     *
     * @return true when set, false otherwise
     */
    public boolean setLastImeOptionsWidget() {
        return false;
    }

    /**
     * Does this widget have any input data entered by the user
     *
     * @return true when data has been entered, false otherwise
     */
    public boolean hasUserInputData() {
        return false;
    }

    /**
     * Set the validation in this widget given the current input value.
     */
    public void setValidation() {
    }

    /**
     * Clear the focus of this widget if it supports focus i.e. the TextLayoutWidget.
     */
    public void clearFocus() {
    }

    /**
     * Set this widget to be focused.
     *
     * @return true when focused, false otherwise
     */
    public boolean requestFocus() {
        return false;
    }

    /**
     * Request the widget to inject its input value into the operation Object.
     *
     * @param operation in which the input value should be added
     */
    public void putValue(Operation operation) throws PaymentException {
    }

    /**
     * Request the widget to validate itself given the current input value.
     *
     * @return true when validated, false otherwise
     */
    public boolean validate() {
        setValidationState(VALIDATION_OK);
        return true;
    }

    /**
     * Inflate and set the widgetView of this widget
     *
     * @param parent in which to inflate this widget widgetView
     * @param layoutResId the resource id of the layout that should be inflated
     */
    final void inflateWidgetView(ViewGroup parent, int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        widgetView = inflater.inflate(layoutResId, parent, false);
        PaymentUtils.setTestId(widgetView, "widget", getKey());
    }

    /**
     * Set this widget visible or hide it.
     *
     * @param visible true when visible, false for hiding this widget
     */
    final void setVisible(boolean visible) {
        widgetView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Set the validation state of this widget
     *
     * @param state to be set
     */
    final void setValidationState(int state) {
        this.state = state;
    }
}
