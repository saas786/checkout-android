/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.examplecheckout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.checkout.model.NetworkOperationType;
import com.payoneer.checkout.sharedtest.checkout.MagicNumbers;
import com.payoneer.checkout.sharedtest.checkout.PaymentDialogHelper;
import com.payoneer.checkout.sharedtest.checkout.PaymentListHelper;
import com.payoneer.checkout.sharedtest.checkout.TestDataProvider;
import com.payoneer.checkout.sharedtest.service.ListSettings;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class RegistrationTests extends AbstractTest {

    @Rule
    public ActivityTestRule<ExampleCheckoutActivity> rule = new ActivityTestRule<>(ExampleCheckoutActivity.class);

    @Test
    public void testRegistration_PROCEED_OK() {
        ListSettings settings = createDefaultListSettings();
        settings.setOperationType(NetworkOperationType.UPDATE);
        enterListUrl(createListUrl(settings));
        clickActionButton();

        int cardIndex = 1;

        // First register an applicable network
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(cardIndex, "card.group");
        PaymentListHelper.fillPaymentListCard(cardIndex, TestDataProvider.visaCardTestData());
        PaymentListHelper.clickPaymentListCardButton(cardIndex);

        // Check if a new card has been added with the title
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.matchesPaymentCardTitle(cardIndex, "41 *** 1111");
    }

    //@Test
    public void testRegistration_PROCEED_PENDING() {
        ListSettings settings = createDefaultListSettings();
        settings.setOperationType(NetworkOperationType.UPDATE);
        settings.setAmount(MagicNumbers.UPDATE_PROCEED_PENDING);
        enterListUrl(createListUrl(settings));
        clickActionButton();

        int cardIndex = 1;

        // First register an applicable network
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(cardIndex, "card.group");
        PaymentListHelper.fillPaymentListCard(cardIndex, TestDataProvider.visaCardTestData());
        PaymentListHelper.clickPaymentListCardButton(cardIndex);

        // Check if a popup dialog is shown with the pending message
        PaymentListHelper.waitForPaymentListDialog();
        PaymentListHelper.matchesPaymentDialogTitle("Payment method pending");
    }

    //@Test
    public void testDeleteRegistration_success() {
        ListSettings settings = createDefaultListSettings();
        settings.setOperationType(NetworkOperationType.UPDATE);
        enterListUrl(createListUrl(settings));
        clickActionButton();

        int cardIndex = 1;

        // First register an ApplicableNetwork
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(cardIndex, "card.group");
        PaymentListHelper.fillPaymentListCard(cardIndex, TestDataProvider.visaCardTestData());
        PaymentListHelper.clickPaymentListCardButton(cardIndex);

        // Open the newly registered account and click the delete button
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(cardIndex, "card.account");
        PaymentListHelper.clickPaymentListCardIcon(cardIndex);
        PaymentListHelper.waitForPaymentListDialog();
        PaymentDialogHelper.clickPaymentDialogButton("Delete");

        // Open the first card in the list which should be the group card
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.matchesPaymentCardTitle(cardIndex, "Cards");
    }
}

