/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * Class containing the different payment input categories i.e. inputelement, extraelement or registration values.
 */
public class PaymentInputCategory {
    public final static String INPUTELEMENT = "inputelement";
    public final static String EXTRAELEMENT = "extraelement";
    public final static String REGISTRATION = "registration";

    /**
     * Check if the given category is a valid payment input value category
     *
     * @param category the payment input category to validate
     * @return true when valid, false otherwise
     */
    public static boolean isValid(final String category) {

        if (category != null) {
            switch (category) {
                case INPUTELEMENT:
                case EXTRAELEMENT:
                case REGISTRATION:
                    return true;
            }
        }
        return false;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        INPUTELEMENT,
        EXTRAELEMENT,
        REGISTRATION
    })
    public @interface Definition { }
}
