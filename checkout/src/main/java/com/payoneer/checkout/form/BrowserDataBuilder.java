/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.form;

import java.util.Locale;
import java.util.TimeZone;

import com.payoneer.checkout.model.BrowserData;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Class for building the BrowserData
 */
public final class BrowserDataBuilder {
    private final static int COLOR_DEPTH = 24;
    private Boolean javaEnabled;
    private String language;
    private Integer colorDepth;
    private String timeZone;
    private Integer browserScreenHeight;
    private Integer browserScreenWidth;

    /**
     * Build a new BrowserData
     *
     * @return the newly build BrowserData
     */
    public BrowserData build() {
        BrowserData data = new BrowserData();
        data.setJavaEnabled(javaEnabled);
        data.setLanguage(language);
        data.setTimezone(timeZone);
        data.setColorDepth(colorDepth);
        data.setBrowserScreenWidth(browserScreenWidth);
        data.setBrowserScreenHeight(browserScreenHeight);
        return data;
    }

    public static BrowserData createFromContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        Context appContext = context.getApplicationContext();
        Configuration config = appContext.getResources().getConfiguration();

        return new BrowserDataBuilder().
            setJavaEnabled(false).
            setLanguage(Locale.getDefault().toLanguageTag()).
            setTimeZone(TimeZone.getDefault().getID()).
            setColorDepth(COLOR_DEPTH).
            setBrowserScreenHeight(config.screenHeightDp).
            setBrowserScreenWidth(config.screenWidthDp).build();
    }

    public BrowserDataBuilder setJavaEnabled(Boolean javaEnabled) {
        this.javaEnabled = javaEnabled;
        return this;
    }

    public BrowserDataBuilder setLanguage(String language) {
        this.language = language;
        return this;
    }

    public BrowserDataBuilder setColorDepth(Integer colorDepth) {
        this.colorDepth = colorDepth;
        return this;
    }

    public BrowserDataBuilder setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public BrowserDataBuilder setBrowserScreenWidth(Integer browserScreenWidth) {
        this.browserScreenWidth = browserScreenWidth;
        return this;
    }

    public BrowserDataBuilder setBrowserScreenHeight(Integer browserScreenHeight) {
        this.browserScreenHeight = browserScreenHeight;
        return this;
    }
}
