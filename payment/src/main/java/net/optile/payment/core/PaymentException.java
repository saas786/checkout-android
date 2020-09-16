/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.core;

import net.optile.payment.model.ErrorInfo;

/**
 * The PaymentException containing the details of the payment error
 */
public class PaymentException extends Exception {

    /** The error info obtained from the payment API */
    private ErrorInfo errorInfo;

    /** Is the exception caused by a network failure, e.g. poor wifi connection */
    private boolean networkFailure;

    /**
     * {@inheritDoc}
     *
     * @param message the extra error info
     */
    public PaymentException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     *
     * @param cause the cause of this exception
     */
    public PaymentException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * {@inheritDoc}
     *
     * @param message containing a description of the error
     * @param cause of the error
     */
    public PaymentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new PaymentException with the cause and networkFailure flag
     *
     * @param cause of the error
     * @param networkFailure indicates if the exception was caused by a communication failure
     */
    public PaymentException(final Throwable cause, boolean networkFailure) {
        super(cause);
        this.networkFailure = networkFailure;
    }

    /**
     * Constructs a new PaymentException
     *
     * @param errorInfo information about the error
     */
    public PaymentException(final ErrorInfo errorInfo) {
        super(errorInfo.getResultInfo());
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public boolean getNetworkFailure() {
        return networkFailure;
    }
}
