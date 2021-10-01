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
import static com.payoneer.checkout.sharedtest.checkout.MagicNumbers.CHARGE_PROCEED_OK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.checkout.model.InteractionCode;
import com.payoneer.checkout.model.InteractionReason;
import com.payoneer.checkout.sharedtest.checkout.ChargePaymentHelper;
import com.payoneer.checkout.sharedtest.checkout.PaymentDialogHelper;
import com.payoneer.checkout.sharedtest.checkout.PaymentListHelper;
import com.payoneer.checkout.sharedtest.service.ListSettings;
import com.payoneer.checkout.sharedtest.view.UiDeviceHelper;
import com.payoneer.checkout.ui.page.PaymentListActivity;

import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class ExtraElementTests extends AbstractTest {

    private final static String DIVISION = "ExtraElements";
    private final static String EXTRAELEMENTS_BOTTOM_CONFIG = "UITests-ExtraElements-Bottom";
    private final static String EXTRAELEMENTS_TOP_CONFIG = "UITests-ExtraElements-Top";
    private final static String EXTRAELEMENTS_TOPBOTTOM_CONFIG = "UITests-ExtraElements-TopBottom";
    
    @Rule
    public ActivityTestRule<ExampleCheckoutActivity> rule = new ActivityTestRule<>(ExampleCheckoutActivity.class);

    @Test
    public void testGroupedNetworks_topElement_clickLink() {
        ListSettings settings = createDefaultListSettings();
        settings.setDivision(DIVISION);
        settings.setCheckoutConfigurationName(EXTRAELEMENTS_TOP_CONFIG);
        enterListUrl(createListUrl(settings));
        clickActionButton();

        int networkCardIndex = 1;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.group");

        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.topelement1", "Number 1");
        clickBrowserPageButton("one", CHROME_CLOSE_BUTTON);
    }

    @Test
    public void testSingleNetwork_bottomElement_clickLink() {
        ListSettings settings = createDefaultListSettings();
        settings.setDivision(DIVISION);
        settings.setCheckoutConfigurationName(EXTRAELEMENTS_BOTTOM_CONFIG);
        enterListUrl(createListUrl(settings));
        clickActionButton();

        int networkCardIndex = 2;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.network");

        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.bottomelement2", "Number 2");
        clickBrowserPageButton("two", CHROME_CLOSE_BUTTON);
    }

    @Test
    public void testSingleNetwork_bottomElement_clickBothLinks() {
        ListSettings settings = createDefaultListSettings();
        settings.setDivision(DIVISION);
        settings.setCheckoutConfigurationName(EXTRAELEMENTS_BOTTOM_CONFIG);
        enterListUrl(createListUrl(settings));
        clickActionButton();

        int networkCardIndex = 2;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.network");

        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.bottomelement3", "Number3A");
        clickBrowserPageButton("3A", CHROME_CLOSE_BUTTON);
        waitForAppRelaunch();
        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.bottomelement3", "Number3B");
        clickBrowserPageButton("3B", CHROME_CLOSE_BUTTON);
    }

    @Test
    public void testSingleNetwork_topBottomElements_clickTopBottomLinks() {
        ListSettings settings = createDefaultListSettings();
        settings.setDivision(DIVISION);
        settings.setCheckoutConfigurationName(EXTRAELEMENTS_TOPBOTTOM_CONFIG);
        enterListUrl(createListUrl(settings));
        clickActionButton();

        int networkCardIndex = 2;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.network");

        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.topelement1", "Number 1");
        clickBrowserPageButton("one", CHROME_CLOSE_BUTTON);
        waitForAppRelaunch();
        PaymentListHelper.clickExtraElementLinkWithText(networkCardIndex, "extraelement.bottomelement2", "Number 2");
        clickBrowserPageButton("two", CHROME_CLOSE_BUTTON);
    }
}
