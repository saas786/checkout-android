/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import com.payoneer.checkout.form.DeleteAccount;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.model.OperationResult;
import com.payoneer.checkout.redirect.RedirectRequest;

/**
 * Interface for network services, a NetworkService is responsible for activating and
 * processing a payment through the supported payment network.
 */
public abstract class NetworkService {

    protected NetworkServiceListener listener;

    /**
     * Stop this NetworkService
     */
    public void stop() {
    }

    /**
     * Set the listener in this NetworkService
     *
     * @param listener the listener to be set
     */
    public void setListener(NetworkServiceListener listener) {
        this.listener = listener;
    }

    /**
     * Process the payment through this NetworkService.
     *
     * @param operation that should be processed
     */
    public void processPayment(Operation operation) {
    }

    /**
     * Delete the AccountRegistration through this NetworkService.
     *
     * @param account to be deleted from this network
     */
    public void deleteAccount(DeleteAccount account) {
    }

    /**
     * Notify the network service that the payment has been redirected
     *
     * @param request the original redirect request that triggered this result
     * @param result optional redirect result
     */
    public void onRedirectResult(RedirectRequest request, OperationResult result) {
    }
}
