/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.examplecheckout;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.After;
import org.junit.Before;

import com.payoneer.checkout.sharedtest.service.ListService;
import com.payoneer.checkout.sharedtest.service.ListSettings;
import com.payoneer.checkout.sharedtest.view.ActivityHelper;
import com.payoneer.checkout.sharedtest.view.UiDeviceHelper;
import com.payoneer.checkout.ui.page.PaymentListActivity;

import android.content.Context;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;

class AbstractTest {

    public final static String CHROME_CLOSE_BUTTON = "com.android.chrome:id/close_button";

    @Before
    public void beforeTest() {
        Intents.init();
    }

    @After
    public void afterTest() {
        Intents.release();
    }

    void enterListUrl(String listUrl) {
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
    }

    IdlingResource getResultIdlingResource() {
        ExampleCheckoutActivity activity = (ExampleCheckoutActivity) ActivityHelper.getCurrentActivity();
        return activity.getResultHandledIdlingResource();
    }

    void matchResultInteraction(String interactionCode, String interactionReason) {
        onView(withId(R.id.text_interactioncode)).check(matches(withText(interactionCode)));
        onView(withId(R.id.text_interactionreason)).check(matches(withText(interactionReason)));
    }

    ListSettings createDefaultListSettings() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ListSettings settings = new ListSettings(com.payoneer.checkout.examplecheckout.test.R.raw.listtemplate);
        return settings.setAppId(context.getPackageName());
    }

    String createListUrl() {
        return createListUrl(createDefaultListSettings());
    }

    String createListUrl(ListSettings settings) {
        String baseUrl = BuildConfig.baseurl;
        String authHeader = BuildConfig.authheader;
        return ListService.createListWithSettings(baseUrl, authHeader, settings);
    }

    void clickActionButton() {
        onView(withId(R.id.button_action)).perform(click());
        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.layout_paymentlist)).check(matches(isDisplayed()));
    }

    void register(IdlingResource resource) {
        IdlingRegistry.getInstance().register(resource);
    }

    void unregister(IdlingResource resource) {
        IdlingRegistry.getInstance().unregister(resource);
    }

    void clickDeviceCollectionPagePageButton(String buttonId) {
        clickBrowserPageButton("simulation of Device Data Collection (DDC) page", buttonId);
    }

    void clickCustomerDecisionPageButton(String buttonId) {
        clickBrowserPageButton("customer decision page", buttonId);
    }

    void clickBrowserPageButton(String textOnPage, String buttonId) {
        UiDeviceHelper.checkUiObjectContainsText(textOnPage);
        UiDeviceHelper.clickUiObjectByResourceName(buttonId);
    }

    void waitForAppRelaunch() {
        UiDeviceHelper.waitUiObjectHasPackage("com.payoneer.checkout.examplecheckout");
    }
}
