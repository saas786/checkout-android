/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.redirect;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.checkout.model.HttpMethod;
import com.payoneer.checkout.model.Redirect;
import com.payoneer.checkout.test.util.TestUtils;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class RedirectServiceTest {

    @Test
    public void supports() {
        Context context = ApplicationProvider.getApplicationContext();
        Redirect redirect = new Redirect();
        redirect.setMethod(HttpMethod.GET);

        URL link = TestUtils.createDefaultURL();
        int requestType = 1;
        RedirectRequest request = new RedirectRequest(requestType, redirect, link);
        assertTrue(RedirectService.supports(context, request));

        redirect.setMethod(HttpMethod.POST);
        assertTrue(RedirectService.supports(context, request));
    }
}
