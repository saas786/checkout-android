/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_NONE;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.payoneer.checkout.R;
import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.ui.widget.input.EditTextInputMode;
import com.payoneer.checkout.validation.ValidationResult;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Base class for widgets using the TextInputLayout and TextInputEditText
 */
public abstract class InputLayoutWidget extends FormWidget {
    TextInputEditText textInput;
    TextInputLayout textLayout;

    EditTextInputMode mode;
    private String helperText;

    /**
     * Construct a new InputLayoutWidget
     *
     * @param name name identifying this widget
     */
    InputLayoutWidget(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View inflate(ViewGroup parent) {
        inflateWidgetView(parent, R.layout.widget_textinput);
        textLayout = widgetView.findViewById(R.id.textinputlayout);
        textLayout.setErrorEnabled(true);
        textLayout.setHelperTextEnabled(true);

        textInput = widgetView.findViewById(R.id.textinputedittext);
        textInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        return handleOnKeyboardDone();
                    case EditorInfo.IME_ACTION_NEXT:
                        return handleOnKeyboardNext();
                    default:
                        return false;
                }
            }
        });

        textInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                handleOnFocusChange(hasFocus);
            }
        });
        return widgetView;
    }

    public void setLabel(String label) {
        textLayout.setHintAnimationEnabled(false);
        textLayout.setHint(label);
        textLayout.setHintAnimationEnabled(true);
    }

    public void setHelperText(String helperText) {
        this.helperText = helperText;
    }

    @Override
    public boolean hasUserInputData() {
        return !(TextUtils.isEmpty(textInput.getText()));
    }

    @Override
    public boolean setLastImeOptionsWidget() {
        textInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }

    @Override
    public boolean requestFocus() {
        return textInput.requestFocus();
    }

    @Override
    public void clearFocus() {
        if (textInput.hasFocus()) {
            textInput.clearFocus();
        }
    }

    @Override
    public void setValidation() {

        if (textInput.hasFocus() || TextUtils.isEmpty(getValue())) {
            setInputLayoutState(VALIDATION_UNKNOWN, false, null);
            return;
        }
        validate();
    }

    @Override
    public boolean validate() {
        ValidationResult result = presenter.validate(name, getValue(), null);
        return setValidationResult(result);
    }

    @Override
    public void putValue(Operation operation) throws PaymentException {
        String val = getValue();
        if (!TextUtils.isEmpty(val)) {
            operation.putStringValue(name, val);
        }
    }

    void setEndIcon(int mode, int resourceId) {
        textLayout.setEndIconMode(mode);
        textLayout.setEndIconDrawable(resourceId);
        textLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnEndIconClicked();
            }
        });
    }

    void removeEndIcon() {
        textLayout.setEndIconMode(END_ICON_NONE);
        textLayout.setEndIconOnClickListener(null);
    }

    void handleOnFocusChange(boolean hasFocus) {
        if (hasFocus) {
            textLayout.setHelperText(helperText);
            setInputLayoutState(VALIDATION_UNKNOWN, false, null);
            presenter.showKeyboard(textInput);
        } else {
            textLayout.setHelperText(null);
            if (state == VALIDATION_UNKNOWN && !TextUtils.isEmpty(getValue())) {
                validate();
            }
        }
    }

    void handleOnEndIconClicked() {
    }

    boolean handleOnKeyboardNext() {
        textInput.clearFocus();
        if (!presenter.requestFocusNextWidget(this)) {
            presenter.hideKeyboard();
        }
        return true;
    }

    boolean handleOnKeyboardDone() {
        textInput.clearFocus();
        presenter.hideKeyboard();
        return true;
    }

    void setTextInputMode(EditTextInputMode mode) {
        if (this.mode != null) {
            this.mode.reset();
        }
        this.mode = mode;
        mode.apply(textInput);
    }

    String getValue() {
        CharSequence cs = textInput.getText();
        String val = (cs != null) ? cs.toString().trim() : "";
        return (mode != null) ? mode.normalize(val) : val;
    }

    boolean setValidationResult(ValidationResult result) {
        if (result == null) {
            return false;
        }
        if (result.isError()) {
            setInputLayoutState(VALIDATION_ERROR, true, result.getMessage());
            return false;
        }
        setInputLayoutState(VALIDATION_OK, false, null);
        return true;
    }

    void setInputLayoutState(int state, boolean errorEnabled, String message) {
        setValidationState(state);
        textLayout.setError(errorEnabled ? message : null);
    }
}
