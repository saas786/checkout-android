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
import com.payoneer.checkout.localization.LocalizationKey;
import com.payoneer.checkout.model.InputElement;
import com.payoneer.checkout.ui.widget.input.EditTextInputModeFactory;

import android.view.View;
import android.view.ViewGroup;

/**
 * Widget for showing the verification code input
 */
public final class VerificationCodeWidget extends InputLayoutWidget {

    /**
     * Construct a new VerificationCodeWidget
     *
     * @param name name identifying this widget
     */
    public VerificationCodeWidget(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View inflate(ViewGroup parent) {
        super.inflate(parent);
        setEndIcon(END_ICON_CUSTOM, R.drawable.ic_tooltip);
        return widgetView;
    }

    /**
     * Bind this verification code widget to the InputElement.
     *
     * @param code of the payment network this widget belongs to
     * @param element to bind this widget to
     */
    public void onBind(String code, InputElement element) {
        int maxLength = presenter.getMaxInputLength(code, name);
        setTextInputMode(EditTextInputModeFactory.createMode(maxLength, element));
        setValidation();
        setLabel(Localization.translate(code, LocalizationKey.VERIFICATIONCODE_GENERIC_PLACEHOLDER));
        setHelperText(Localization.translate(code, LocalizationKey.VERIFICATIONCODE_SPECIFIC_PLACEHOLDER));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void handleOnEndIconClicked() {
        presenter.onHintClicked(name);
    }
}
