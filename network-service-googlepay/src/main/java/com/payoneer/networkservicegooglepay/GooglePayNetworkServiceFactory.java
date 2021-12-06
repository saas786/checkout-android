/*
 *
 *  * Copyright (c) 2021 Payoneer Germany GmbH
 *  * https://www.payoneer.com
 *  *
 *  * This file is open source and available under the MIT license.
 *  * See the LICENSE file for more information.
 *  *
 */

package com.payoneer.networkservicegooglepay;

import com.payoneer.checkout.ui.service.NetworkService;
import com.payoneer.checkout.ui.service.NetworkServiceFactory;

import android.content.Context;

public class GooglePayNetworkServiceFactory implements NetworkServiceFactory {
    @Override
    public boolean supports(final String code, final String method) {
        return false;
    }

    @Override
    public NetworkService createService(final Context context) {
        return null;
    }
}
