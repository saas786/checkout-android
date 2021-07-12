/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.page;

import com.payoneer.checkout.localization.InteractionMessage;
import com.payoneer.checkout.ui.PaymentActivityResult;
import com.payoneer.checkout.ui.PaymentResult;
import com.payoneer.checkout.ui.dialog.PaymentDialogFragment.PaymentDialogListener;

import android.app.Activity;

/**
 * The view (MVP) interface for screens that handle payments
 */
interface BasePaymentView {

    /**
     * Show the progress animation.
     *
     * @param visible show the progress when true, false otherwise
     */
    void showProgress(boolean visible);

    /**
     * Show the connection error dialog to the user, notify the listener of events in this dialog.
     *
     * @param listener to be notified of dialog events
     */
    void showConnectionErrorDialog(PaymentDialogListener listener);

    /**
     * Show the delete account dialog to the user, notify the listener of events in this dialog.
     *
     * @param listener to be notified of dialog events
     * @param accountLabel label of the account this is being deleted
     */
    void showDeleteAccountDialog(PaymentDialogListener listener, String accountLabel);

    /**
     * Show the refresh dialog to the user, notify the listener of events in this dialog.
     *
     * @param listener to be notified of dialog events
     */
    void showRefreshAccountDialog(PaymentDialogListener listener);
    
    /**
     * Show the interaction text to the user, notify the listener of events in this dialog.
     * When there is no localization for the interaction then the default error will be shown to the user.
     *
     * @param interactionMessage used to show the proper localization message to the user
     * @param listener to be notified of dialog events
     */
    void showInteractionDialog(InteractionMessage interactionMessage, PaymentDialogListener listener);

    /**
     * Show a warning message to the user
     *
     * @param message The message to be shown
     */
    void showWarningMessage(String message);

    /**
     * Show Hint dialog
     *
     * @param networkCode Code if the network
     * @param type type if input field
     * @param listener listening to events of this Hint Dialog.
     */
    void showHintDialog(String networkCode, String type, PaymentDialogListener listener);

    /**
     * Set the current activity payment result, this is either PaymentUI.RESULT_CODE_OK,
     * PaymentUI.RESULT_CODE_ERROR
     *
     * @param resultCode the current resultCode
     * @param result containing the Payment result state
     */
    void setPaymentResult(int resultCode, PaymentResult result);

    /**
     * Pass on the ActivityResult to the activity that started this View.
     *
     * @param paymentActivityResult to be pass on
     */
    void passOnActivityResult(PaymentActivityResult paymentActivityResult);

    /**
     * Get the Android Context of this view.
     *
     * @return the Context of this view
     */
    Activity getActivity();

    /**
     * Close this payment view
     */
    void close();
}
