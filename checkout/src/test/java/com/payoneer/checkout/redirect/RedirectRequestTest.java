/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.redirect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.model.OperationResult;
import com.payoneer.checkout.model.Redirect;
import com.payoneer.checkout.test.util.TestUtils;

public class RedirectRequestTest {

    @Test
    public void fromOperationResult_missingRedirect() {
        Assertions.assertThrows(PaymentException.class, () -> {
            int requestCode = 1;
            OperationResult operationResult = new OperationResult();
            operationResult.setLinks(createLinks());
            RedirectRequest.fromOperationResult(requestCode, operationResult);
        });
    }

    @Test
    public void fromOperationResult_missingLink() {
        Assertions.assertThrows(PaymentException.class, () -> {
            int requestCode = 1;
            OperationResult operationResult = new OperationResult();
            operationResult.setRedirect(new Redirect());
            RedirectRequest.fromOperationResult(requestCode, operationResult);
        });
    }

    @Test
    public void fromOperationResult_success() throws PaymentException {
        OperationResult operationResult = new OperationResult();
        Redirect redirect = new Redirect();
        Map<String, URL> links = createLinks();
        operationResult.setRedirect(redirect);
        operationResult.setLinks(links);
        int requestCode = 1;

        RedirectRequest request = RedirectRequest.fromOperationResult(requestCode, operationResult);
        assertNotNull(request);
        assertEquals(requestCode, request.getRequestCode());
        assertEquals(redirect, request.getRedirect());
        assertEquals(links.get("redirect"), request.getLink());
    }

    private Map<String, URL> createLinks() {
        return Collections.singletonMap("redirect", TestUtils.createDefaultURL());
    }
}
