/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.redirect.RedirectRequest;
import com.payoneer.checkout.ui.PaymentResult;

/**
 * Presenter to be called by the NetworkService to inform about payment updates and to show i.e. a progress view or progress dialog.
 */
public interface NetworkServiceListener {

    /**
     * Notify the presenter that the service is in progress and requires a progress indicator
     *
     * @param visible true to show the progress indicator, false to hide the progress
     */
    void showProgress(boolean visible);

    /**
     * Ask the network service to redirect the payment to an external address
     *
     * @param request containing the redirect data
     */
    void redirect(RedirectRequest request) throws PaymentException;

    /**
     * Called when NetworkService is done processing the request.
     *
     * @param resultCode code describing the state of the paymentResult
     * @param paymentResult containing the information describing the result
     */
    void onProcessPaymentResult(int resultCode, PaymentResult paymentResult);

    /**
     * Called when NetworkService is done deleting the account.
     *
     * @param resultCode code describing the state of the paymentResult
     * @param paymentResult containing the information describing the result
     */
    void onDeleteAccountResult(int resultCode, PaymentResult paymentResult);
}
