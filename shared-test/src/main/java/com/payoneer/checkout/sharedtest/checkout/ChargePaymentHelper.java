/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.sharedtest.checkout;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import com.payoneer.checkout.R;
import com.payoneer.checkout.sharedtest.view.ActivityHelper;
import com.payoneer.checkout.ui.page.ChargePaymentActivity;
import com.payoneer.checkout.ui.page.idlingresource.PaymentIdlingResources;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;

public final class ChargePaymentHelper {

    public static void waitForChargePaymentDialog() {
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        ChargePaymentActivity chargeActivity = (ChargePaymentActivity) ActivityHelper.getCurrentActivity();
        PaymentIdlingResources idlingResources = chargeActivity.getPaymentIdlingResources();
        IdlingResource dialogIdlingResource = idlingResources.getDialogIdlingResource();

        IdlingRegistry.getInstance().register(dialogIdlingResource);
        onView(ViewMatchers.withId(R.id.alertTitle)).check(matches(isDisplayed()));

        idlingResources.resetDialogIdlingResource();
        IdlingRegistry.getInstance().unregister(dialogIdlingResource);
    }
}
