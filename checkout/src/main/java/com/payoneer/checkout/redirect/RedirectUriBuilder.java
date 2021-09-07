/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.redirect;

import java.net.URL;
import java.util.List;

import com.payoneer.checkout.model.Parameter;
import com.payoneer.checkout.model.Redirect;

import android.net.Uri;

/**
 * Class for constructing a Uri from a Redirect model class
 */
public final class RedirectUriBuilder {
    /**
     * Create a Uri from the provided redirect
     *
     * @param redirect to be converted into a Uri
     * @return the created Uri
     */
    public static Uri fromRedirect(Redirect redirect) {
        Uri uri = fromURL(redirect.getUrl());
        List<Parameter> params = redirect.getParameters();
        if (params == null || params.size() == 0) {
            return uri;
        }
        Uri.Builder builder = uri.buildUpon();
        for (Parameter param : params) {
            builder.appendQueryParameter(param.getName(), param.getValue());
        }
        return builder.build();
    }

    /**
     * Create a Uri from the provided url
     *
     * @param url to be converted to a Uri
     * @return the newly created uri
     */
    public static Uri fromURL(URL url) {
        return Uri.parse(url.toString());
    }

    public static Uri fromString(String url) {
        return Uri.parse(url);
    }
}
