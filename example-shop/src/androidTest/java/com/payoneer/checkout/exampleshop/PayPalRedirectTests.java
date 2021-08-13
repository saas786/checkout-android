/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package com.payoneer.checkout.exampleshop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.checkout.exampleshop.checkout.CheckoutActivity;
import com.payoneer.checkout.exampleshop.settings.SettingsActivity;
import com.payoneer.checkout.exampleshop.summary.SummaryActivity;
import com.payoneer.checkout.model.NetworkOperationType;
import com.payoneer.checkout.sharedtest.checkout.PaymentListHelper;
import com.payoneer.checkout.sharedtest.view.UiDeviceHelper;

import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class PayPalRedirectTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void testPayPalRedirect_directCharge_customerAccept() {
        int networkCardIndex = 3;
        CheckoutActivity checkoutActivity = openCheckoutActivity(NetworkOperationType.CHARGE);
        IdlingResource resultHandledIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);
        clickDecisionPageButton("customer-accept");

        waitForConfirmActivityLoaded(resultHandledIdlingResource);
        unregister(resultHandledIdlingResource);
    }

    @Test
    public void testPayPalRedirect_presetFlow_customerAccept() {
        int networkCardIndex = 3;
        CheckoutActivity checkoutActivity = openCheckoutActivity(NetworkOperationType.PRESET);
        IdlingResource checkoutPaymentResultIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);

        register(checkoutPaymentResultIdlingResource);
        waitForSummaryActivityLoaded();
        unregister(checkoutPaymentResultIdlingResource);

        SummaryActivity summaryActivity = waitForSummaryActivityLoaded();
        IdlingResource summaryPaymentResultIdlingResource = summaryActivity.getResultHandledIdlingResource();
        clickSummaryPayButton();
        clickDecisionPageButton("customer-accept");

        waitForConfirmActivityLoaded(summaryPaymentResultIdlingResource);
        unregister(summaryPaymentResultIdlingResource);
    }

    private void clickDecisionPageButton(String buttonId) {
        UiDeviceHelper.checkUiObjectContainsText("customer decision page");
        UiDeviceHelper.clickUiObjectByResourceName(buttonId);
        UiDeviceHelper.waitUiObjectHasPackage("com.payoneer.checkout.exampleshop");
    }
}
