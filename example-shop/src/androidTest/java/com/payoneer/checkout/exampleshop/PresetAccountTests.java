/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package com.payoneer.checkout.exampleshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.payoneer.checkout.sharedtest.view.PaymentMatchers.isViewInPaymentCard;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.checkout.exampleshop.checkout.CheckoutActivity;
import com.payoneer.checkout.exampleshop.settings.SettingsActivity;
import com.payoneer.checkout.model.NetworkOperationType;
import com.payoneer.checkout.sharedtest.checkout.PaymentListHelper;

import android.view.View;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class PresetAccountTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void testPresetAccountWithoutAccountMask() {
        int presetCardIndex = 1;
        int networkCardIndex = 3;

        CheckoutActivity checkoutActivity = openCheckoutActivity(NetworkOperationType.PRESET);
        IdlingResource checkoutResultHandledIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);

        register(checkoutResultHandledIdlingResource);
        waitForSummaryActivityLoaded();
        unregister(checkoutResultHandledIdlingResource);

        onView(ViewMatchers.withId(R.id.label_title)).check(matches(withText("PAYPAL")));
        clickSummaryEditButton();

        PaymentListHelper.waitForPaymentListLoaded(2);
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isViewInPaymentCard(presetCardIndex, withText("PayPal"), R.id.text_title)));
    }
}
