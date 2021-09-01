/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.exampleshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import java.util.Map;

import org.junit.After;
import org.junit.Before;

import com.payoneer.checkout.exampleshop.checkout.CheckoutActivity;
import com.payoneer.checkout.exampleshop.confirm.ConfirmActivity;
import com.payoneer.checkout.exampleshop.summary.SummaryActivity;
import com.payoneer.checkout.sharedtest.checkout.PaymentListHelper;
import com.payoneer.checkout.sharedtest.checkout.TestDataProvider;
import com.payoneer.checkout.sharedtest.service.ListService;
import com.payoneer.checkout.sharedtest.service.ListSettings;
import com.payoneer.checkout.sharedtest.view.ActivityHelper;
import com.payoneer.checkout.sharedtest.view.PaymentActions;
import com.payoneer.checkout.ui.page.ChargePaymentActivity;

import android.content.Context;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;

class AbstractTest {

    @Before
    public void beforeTest() {
        Intents.init();
    }

    @After
    public void afterTest() {
        Intents.release();
    }

    CheckoutActivity openCheckoutActivity(String operationType) {
        String listUrl = createListUrl(operationType);

        onView(withId(R.id.layout_settings)).check(matches(isDisplayed()));
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_settings)).perform(click());
        intended(hasComponent(CheckoutActivity.class.getName()));
        return (CheckoutActivity) ActivityHelper.getCurrentActivity();
    }

    void clickCheckoutButton() {
        intended(hasComponent(CheckoutActivity.class.getName()));
        onView(withId(R.id.button_checkout)).perform(PaymentActions.scrollToView(), click());
    }

    void fillPaymentListCardData(int cardIndex) {
        Map<String, String> cardData = TestDataProvider.visaCardTestData();
        PaymentListHelper.fillPaymentListCard(cardIndex, cardData);
    }

    SummaryActivity waitForSummaryActivityLoaded() {
        intended(hasComponent(SummaryActivity.class.getName()));
        SummaryActivity summaryActivity = (SummaryActivity) ActivityHelper.getCurrentActivity();
        IdlingResource loadIdlingResource = summaryActivity.getLoadIdlingResource();
        register(loadIdlingResource);
        onView(withId(R.id.layout_coordinator)).check(matches(isDisplayed()));
        unregister(loadIdlingResource);
        return summaryActivity;
    }

    void clickSummaryPayButton() {
        intended(hasComponent(SummaryActivity.class.getName()));
        onView(withId(R.id.button_pay)).perform(PaymentActions.scrollToView(), click());
    }

    void clickSummaryEditButton() {
        intended(hasComponent(SummaryActivity.class.getName()));
        onView(withId(R.id.button_edit)).perform(PaymentActions.scrollToView(), click());
    }

    void waitForChargePaymentActivityDisplayed() {
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(withId(R.id.layout_chargepayment)).check(matches(isDisplayed()));
    }

    ConfirmActivity waitForConfirmActivityLoaded(IdlingResource resultHandledIdlingResource) {
        register(resultHandledIdlingResource);
        intended(hasComponent(ConfirmActivity.class.getName()));
        onView(withId(R.id.layout_confirm)).check(matches(isDisplayed()));
        return (ConfirmActivity) ActivityHelper.getCurrentActivity();
    }

    void register(IdlingResource resource) {
        IdlingRegistry.getInstance().register(resource);
    }

    void unregister(IdlingResource resource) {
        IdlingRegistry.getInstance().unregister(resource);
    }

    private String createListUrl(String operationType) {
        String baseUrl = BuildConfig.baseurl;
        String authHeader = BuildConfig.authheader;
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ListSettings settings = new ListSettings(com.payoneer.checkout.exampleshop.test.R.raw.listtemplate)
            .setAppId(context.getPackageName())
            .setOperationType(operationType);
        return ListService.createListWithSettings(baseUrl, authHeader, settings);
    }
}
