/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.redirect;

import java.net.URL;
import java.util.Map;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.model.OperationResult;
import com.payoneer.checkout.model.Redirect;

/**
 * Class holding the data for making a redirect
 */
public final class RedirectRequest {

    private final Redirect redirect;
    private final URL link;
    private final int requestCode;

    /**
     * Construct a new RedirectRequest
     *
     * @param requestCode for the sender that will be associated with the redirect result when it is returned. Allowing the sender to identify incoming results.
     * @param redirect contains information to redirect the request using an external browser window
     * @param link pointing to the redirect api endpoint
     */
    public RedirectRequest(int requestCode, Redirect redirect, URL link) {
        this.requestCode = requestCode;
        this.redirect = redirect;
        this.link = link;
    }

    /**
     * Get the requestCode, this requestCode can be used to associate incoming results with this request
     *
     * @return the requestCode identifying incoming results for this request
     */
    public int getRequestCode() {
        return requestCode;
    }

    /**
     * Get the redirect method
     *
     * @return the redirect method
     */
    public String getRedirectMethod() {
        return redirect.getMethod();
    }

    /**
     * Get the Redirect Object
     *
     * @return the redirect object
     */
    public Redirect getRedirect() {
        return redirect;
    }

    /**
     * Get the redirect link
     *
     * @return the redirect link
     */
    public URL getLink() {
        return link;
    }

    /**
     * Create a RedirectRequest from the provided OperationResult
     *
     * @param requestCode used to identify redirect results with the request
     * @param operationResult containing the redirect request information
     * @return newly created RedirectRequest
     * @throws PaymentException when the OperationResult does not contain the required information
     */
    public static RedirectRequest fromOperationResult(int requestCode, OperationResult operationResult) throws PaymentException {
        Redirect redirect = operationResult.getRedirect();
        if (redirect == null) {
            throw new PaymentException("OperationResult must contain a Redirect object");
        }
        Map<String, URL> links = operationResult.getLinks();
        if (links == null || !links.containsKey("redirect")) {
            throw new PaymentException("OperationResult must contain a redirect link URL");
        }
        return new RedirectRequest(requestCode, redirect, links.get("redirect"));
    }
}
