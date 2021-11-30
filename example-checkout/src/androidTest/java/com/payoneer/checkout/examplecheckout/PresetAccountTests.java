/*
 *
 *   Copyright (c) 2021 Payoneer Germany GmbH
 *   https://www.payoneer.com
 *
 *   This file is open source and available under the MIT license.
 *   See the LICENSE file for more information.
 *
 */

package com.payoneer.checkout.examplecheckout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.checkout.model.InteractionCode;
import com.payoneer.checkout.model.InteractionReason;
import com.payoneer.checkout.model.NetworkOperationType;
import com.payoneer.checkout.sharedtest.checkout.MagicNumbers;
import com.payoneer.checkout.sharedtest.checkout.PaymentListHelper;
import com.payoneer.checkout.sharedtest.checkout.TestDataProvider;
import com.payoneer.checkout.sharedtest.service.ListSettings;

import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@SuppressWarnings("deprecation")
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PresetAccountTests extends AbstractTest {

    @Rule
    public ActivityTestRule<ExampleCheckoutActivity> rule = new ActivityTestRule<>(ExampleCheckoutActivity.class);

    @Test
    public void testMissingPresetAccount_ABORT_CLIENTSIDE_ERROR() {
        IdlingResource resultIdlingResource = getResultIdlingResource();

        ListSettings settings = createDefaultListSettings();
        settings.setOperationType(NetworkOperationType.PRESET);
        settings.setAmount(MagicNumbers.CHARGE_TRY_OTHER_ACCOUNT);
        enterListUrl(createListUrl(settings));
        clickChargePresetAccountButton();

        register(resultIdlingResource);
        matchResultInteraction(InteractionCode.ABORT, InteractionReason.CLIENTSIDE_ERROR);
        unregister(resultIdlingResource);
    }

    @Test
    public void testChargePresetAccount_PROCEED_OK() {
        IdlingResource resultIdlingResource = getResultIdlingResource();

        ListSettings settings = createDefaultListSettings();
        settings.setOperationType(NetworkOperationType.PRESET);
        enterListUrl(createListUrl(settings));
        clickShowPaymentListButton();

        int groupCardIndex = 1;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(groupCardIndex, "card.group");
        PaymentListHelper.fillPaymentListCard(groupCardIndex, TestDataProvider.amexCardTestData());
        PaymentListHelper.clickPaymentListCardButton(groupCardIndex);

        register(resultIdlingResource);
        matchResultInteraction(InteractionCode.PROCEED, InteractionReason.OK);
        unregister(resultIdlingResource);

        clickChargePresetAccountButton();
        register(resultIdlingResource);
        matchResultInteraction(InteractionCode.PROCEED, InteractionReason.OK);
        unregister(resultIdlingResource);
    }

    @Test
    public void testChargingPresetAccountWithRedirectNetwork_PROCEED_OK() {
        IdlingResource resultIdlingResource = getResultIdlingResource();
        ListSettings settings = createDefaultListSettings();
        settings.setOperationType(NetworkOperationType.PRESET);
        enterListUrl(createListUrl(settings));
        clickShowPaymentListButton();

        int networkCardIndex = 3;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card.network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);

        register(resultIdlingResource);
        matchResultInteraction(InteractionCode.PROCEED, InteractionReason.OK);
        unregister(resultIdlingResource);

        clickChargePresetAccountButton();
        register(resultIdlingResource);
        clickCustomerDecisionPageButton("customer-accept");
        waitForAppRelaunch();
    }

    @Test
    public void testChargingPresetAccountWith3DS2_PROCEED_OK() {
        IdlingResource resultIdlingResource = getResultIdlingResource();

        ListSettings settings = createDefaultListSettings();
        settings.setOperationType(NetworkOperationType.PRESET);
        settings.setAmount(MagicNumbers.THREE3DS2);
        enterListUrl(createListUrl(settings));
        clickShowPaymentListButton();

        int groupCardIndex = 1;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(groupCardIndex, "card.group");
        PaymentListHelper.fillPaymentListCard(groupCardIndex, TestDataProvider.amexCardTestData());
        PaymentListHelper.clickPaymentListCardButton(groupCardIndex);

        register(resultIdlingResource);
        matchResultInteraction(InteractionCode.PROCEED, InteractionReason.OK);
        unregister(resultIdlingResource);

        clickChargePresetAccountButton();
        clickDeviceCollectionPagePageButton("customer-accept");
        waitForAppRelaunch();

        register(resultIdlingResource);
        matchResultInteraction(InteractionCode.PROCEED, InteractionReason.OK);
        unregister(resultIdlingResource);
    }
}
