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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.payoneer.checkout.sharedtest.view.PaymentActions.actionOnViewInWidget;
import static com.payoneer.checkout.sharedtest.view.PaymentMatchers.isCardWithTestId;

import java.util.Map;

import org.hamcrest.Matcher;

import com.payoneer.checkout.R;
import com.payoneer.checkout.sharedtest.view.ActivityHelper;
import com.payoneer.checkout.ui.page.PaymentListActivity;

import android.view.View;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;

public final class PaymentListHelper {

    public static PaymentListActivity waitForPaymentListLoaded(int count) {
        intended(hasComponent(PaymentListActivity.class.getName()), times(count));
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        IdlingResource loadIdlingResource = listActivity.getLoadIdlingResource();

        IdlingRegistry.getInstance().register(loadIdlingResource);
        onView(withId(R.id.recyclerview_paymentlist)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(loadIdlingResource);

        return listActivity;
    }

    public static void openPaymentListCard(int cardIndex, String cardTestId) {
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isCardWithTestId(cardIndex, cardTestId)));
        onView(list).perform(actionOnItemAtPosition(cardIndex, click()));
    }

    public static void fillPaymentListCard(int cardIndex, Map<String, String> values) {
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);

        for (Map.Entry<String, String> pair : values.entrySet()) {
            onView(list).perform(actionOnViewInWidget(cardIndex, typeText(pair.getValue()), pair.getKey(),
                R.id.textinputedittext), closeSoftKeyboard());
        }
    }

    public static void clickPaymentListCardButton(int cardIndex) {
        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.recyclerview_paymentlist)).perform(actionOnViewInWidget(cardIndex, click(), "buttonWidget", R.id.button));
    }
}
