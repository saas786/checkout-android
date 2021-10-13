/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import com.payoneer.checkout.model.OperationResult;

/**
 * Listener to be called by the OperationService to inform about operation updates.
 */
public interface OperationListener {

    /**
     * Called when the account was successfully deleted.
     *
     * @param operationResult containing the result of the deletion request.
     */
    void onDeleteAccountSuccess(OperationResult operationResult);

    /**
     * Called when an error occurred while deleting the account.
     *
     * @param cause describing the reason of failure
     */
    void onDeleteAccountError(Throwable cause);

    /**
     * Called when the operation was successfully posted.
     *
     * @param operationResult containing the result of the performed operation
     */
    void onOperationSuccess(OperationResult operationResult);

    /**
     * Called when an error occurred while performing the operation.
     *
     * @param cause describing the reason of failure
     */
    void onOperationError(Throwable cause);
}
