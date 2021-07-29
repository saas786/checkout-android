/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.sharedtest.checkout;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.payoneer.checkout.R;

import androidx.test.espresso.matcher.ViewMatchers;

public final class PaymentDialogHelper {

    public static void clickPaymentDialogButton(String buttonLabel) {
        onView(withText(buttonLabel)).perform(click());
    }

    public static void matchesPaymentDialogTitle(String title) {
        onView(ViewMatchers.withId(R.id.alertTitle)).check(matches(withText(title)));
    }
}
