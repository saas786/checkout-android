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
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.payoneer.checkout.sharedtest.view.PaymentActions.actionOnViewInPaymentCard;
import static com.payoneer.checkout.sharedtest.view.PaymentActions.actionOnViewInWidget;
import static com.payoneer.checkout.sharedtest.view.PaymentActions.clickClickableSpan;
import static com.payoneer.checkout.sharedtest.view.PaymentMatchers.hasTextInputLayoutError;
import static com.payoneer.checkout.sharedtest.view.PaymentMatchers.hasTextInputLayoutValue;
import static com.payoneer.checkout.sharedtest.view.PaymentMatchers.isCardWithTestId;
import static com.payoneer.checkout.sharedtest.view.PaymentMatchers.isViewInPaymentCard;
import static com.payoneer.checkout.sharedtest.view.PaymentMatchers.isViewInWidget;
import static com.payoneer.checkout.sharedtest.view.PaymentMatchers.linearLayoutWithChildCount;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Map;

import org.hamcrest.Matcher;

import com.payoneer.checkout.R;
import com.payoneer.checkout.sharedtest.view.ActivityHelper;
import com.payoneer.checkout.ui.page.PaymentListActivity;
import com.payoneer.checkout.ui.page.idlingresource.PaymentIdlingResources;

import android.view.View;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;

public final class PaymentListHelper {

    public static PaymentListActivity waitForPaymentListLoaded(int count) {
        intended(hasComponent(PaymentListActivity.class.getName()), times(count));
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        PaymentIdlingResources idlingResources = listActivity.getPaymentIdlingResources();
        IdlingResource loadIdlingResource = idlingResources.getLoadIdlingResource();

        IdlingRegistry.getInstance().register(loadIdlingResource);
        onView(withId(R.id.recyclerview_paymentlist)).check(matches(isDisplayed()));

        idlingResources.resetLoadIdlingResource();
        IdlingRegistry.getInstance().unregister(loadIdlingResource);
        return listActivity;
    }

    public static void waitForPaymentListDialog() {
        intended(hasComponent(PaymentListActivity.class.getName()));
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        PaymentIdlingResources idlingResources = listActivity.getPaymentIdlingResources();
        IdlingResource dialogIdlingResource = idlingResources.getDialogIdlingResource();

        IdlingRegistry.getInstance().register(dialogIdlingResource);
        onView(ViewMatchers.withId(R.id.alertTitle)).
            inRoot(withDecorView(not(is(listActivity.getWindow().getDecorView())))).
            check(matches(isDisplayed()));

        idlingResources.resetDialogIdlingResource();
        IdlingRegistry.getInstance().unregister(dialogIdlingResource);
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

    public static void clickExtraElementLinkWithText(int cardIndex, String widgetName, String linkText) {
        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.recyclerview_paymentlist))
            .perform(actionOnViewInWidget(cardIndex, clickClickableSpan(linkText), widgetName, R.id.label_checkbox));
    }

    public static void clickPaymentListCardButton(int cardIndex) {
        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.recyclerview_paymentlist)).perform(actionOnViewInWidget(cardIndex, click(), "uielement.button", R.id.button));
    }

    public static void clickPaymentListCardIcon(int cardIndex) {
        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.recyclerview_paymentlist)).perform(actionOnViewInPaymentCard(cardIndex, click(), R.id.viewswitcher_icon));
    }

    public static void matchesPaymentCardTitle(int cardIndex, String title) {
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isViewInPaymentCard(cardIndex, withText(title), R.id.text_title)));
    }

    public static void matchesPaymentDialogTitle(String title) {
        onView(ViewMatchers.withId(R.id.alertTitle)).check(matches(withText(title)));
    }

    public static void matchesCardGroupCount(int cardIndex, int count) {
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isViewInPaymentCard(cardIndex, linearLayoutWithChildCount(count), R.id.layout_logos)));
    }

    public static void matchesErrorTextInWidget(int cardIndex, String widgetName, String errorText) {
        intended(hasComponent(PaymentListActivity.class.getName()));
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isViewInWidget(cardIndex, hasTextInputLayoutError(errorText), widgetName, R.id.textinputlayout)));
    }

    public static void matchesInputTextInWidget(int cardIndex, String widgetName, String value) {
        intended(hasComponent(PaymentListActivity.class.getName()));
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isViewInWidget(cardIndex, hasTextInputLayoutValue(value), widgetName, R.id.textinputlayout)));
    }
}
