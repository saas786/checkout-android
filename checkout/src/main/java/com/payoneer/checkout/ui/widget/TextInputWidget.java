/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM;

import com.payoneer.checkout.R;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.model.InputElement;
import com.payoneer.checkout.ui.widget.input.EditTextInputModeFactory;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

/**
 * Widget for handling text input
 */
public final class TextInputWidget extends InputLayoutWidget {

    public TextInputWidget(String category, String name) {
        super(category, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View inflate(ViewGroup parent) {
        super.inflate(parent);
        textInput.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextInputChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        return widgetView;
    }

    /**
     * Set the InputElement into this TextInputWidget
     *
     * @param code of the network
     * @param element to be set in this widget
     */
    public void onBind(String code, InputElement element) {
        int maxLength = presenter.getMaxInputLength(code, name);
        setTextInputMode(EditTextInputModeFactory.createMode(maxLength, element));
        setValidation();
        setLabel(Localization.translateAccountLabel(code, name));
        setHelperText(Localization.translateAccountPlaceholder(code, name));
    }

    @Override
    void handleOnFocusChange(boolean hasFocus) {
        super.handleOnFocusChange(hasFocus);
        setClearIcon(getValue(), hasFocus);
    }

    @Override
    void handleOnEndIconClicked() {
        textInput.getText().clear();
    }

    @Override
    void onTextInputChanged() {
        super.onTextInputChanged();
        setClearIcon(getValue(), textInput.hasFocus());
    }

    private void setClearIcon(String value, boolean hasFocus) {
        if (!TextUtils.isEmpty(value) && hasFocus) {
            setEndIcon(END_ICON_CUSTOM, R.drawable.ic_cancel);
        } else {
            removeEndIcon();
        }
    }
}
