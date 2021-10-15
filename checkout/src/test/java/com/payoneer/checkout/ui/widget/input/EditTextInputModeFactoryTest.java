/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget.input;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.payoneer.checkout.core.PaymentInputType;
import com.payoneer.checkout.model.InputElement;
import com.payoneer.checkout.model.InputElementType;

public class EditTextInputModeFactoryTest {

    @Test
    public void createMode() {
        validateTextInputMode(PaymentInputType.ACCOUNT_NUMBER, AccountNumberInputMode.class);
        validateTextInputMode(PaymentInputType.HOLDER_NAME, HolderNameInputMode.class);
        validateTextInputMode(PaymentInputType.VERIFICATION_CODE, VerificationCodeInputMode.class);
        validateTextInputMode(PaymentInputType.IBAN, IBANInputMode.class);
        validateTextInputMode(PaymentInputType.BIC, BICInputMode.class);
        validateTextInputMode(PaymentInputType.BANK_CODE, ElementInputMode.class);
        validateTextInputMode("FOO", ElementInputMode.class);
    }

    private static void validateTextInputMode(String name, Class<?> clazz) {
        InputElement element = new InputElement();
        element.setName(name);
        element.setType(InputElementType.STRING);

        EditTextInputMode mode = EditTextInputModeFactory.createMode(10, element);
        assertTrue(clazz.isInstance(mode));
    }
}
