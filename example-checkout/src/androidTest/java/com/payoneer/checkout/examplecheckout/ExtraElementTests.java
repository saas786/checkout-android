/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.examplecheckout;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.checkout.model.InteractionCode;
import com.payoneer.checkout.model.InteractionReason;
import com.payoneer.checkout.sharedtest.checkout.ChargePaymentHelper;
import com.payoneer.checkout.sharedtest.checkout.PaymentDialogHelper;
import com.payoneer.checkout.sharedtest.checkout.PaymentListHelper;
import com.payoneer.checkout.sharedtest.view.UiDeviceHelper;
import com.payoneer.checkout.ui.page.PaymentListActivity;

import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class ExtraElementTests extends AbstractTest {

    private final String LISTRESULT_BOTTOM = "https://raw.githubusercontent.com/optile/checkout-android/PCX-2004/shared-test/lists/listresult_extraelements_bottom.json";
    private final String LISTRESULT_TOP = "https://raw.githubusercontent.com/optile/checkout-android/PCX-2004/shared-test/lists/listresult_extraelements_top.json";
    private final String LISTRESULT_TOPBOTTOM = "https://raw.githubusercontent.com/optile/checkout-android/PCX-2004/shared-test/lists/listresult_extraelements_topbottom.json";

    @Rule
    public ActivityTestRule<ExampleCheckoutActivity> rule = new ActivityTestRule<>(ExampleCheckoutActivity.class);

    @Test
    public void testBottomExtraElements_clickBottomLink_openBrowserWindow() {
        enterListUrl(LISTRESULT_BOTTOM);
        clickActionButton();

        int networkCardIndex = 1;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.group");
        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.bottomelement2", "Number 2");
        clickBrowserPageButton("two", CHROME_CLOSE_BUTTON);
    }

    @Test
    public void testTopExtraElements_clickTopLink_openBrowserWindow() {
        enterListUrl(LISTRESULT_TOP);
        clickActionButton();

        int networkCardIndex = 1;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.group");
        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.topelement1", "Number 1");
        clickBrowserPageButton("one", CHROME_CLOSE_BUTTON);
    }

    @Test
    public void testTopBottomExtraElements_clickTopLink_openBrowserWindow() {
        enterListUrl(LISTRESULT_TOPBOTTOM);
        clickActionButton();

        int networkCardIndex = 1;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.group");
        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.topelement1", "Number 1");
        clickBrowserPageButton("one", CHROME_CLOSE_BUTTON);
    }

    @Test
    public void testTopBottomExtraElements_clickBottomLink_openBrowserWindow() {
        enterListUrl(LISTRESULT_TOPBOTTOM);
        clickActionButton();

        int networkCardIndex = 1;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.group");
        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.bottomelement3", "Number3B");
        clickBrowserPageButton("3B", CHROME_CLOSE_BUTTON);
    }
}
