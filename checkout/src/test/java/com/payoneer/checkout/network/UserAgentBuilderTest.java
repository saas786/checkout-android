/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import android.content.Context;
import android.text.TextUtils;
import androidx.test.core.app.ApplicationProvider;

public class UserAgentBuilderTest {

    private final String SDK_VERSION_NAME = "5.3.0";
    private final Integer SDK_VERSION_CODE = 51;
    private final String APP_VERSION_NAME = "6.1.0";
    private final Integer APP_VERSION_CODE = 73;
    private final String APP_PACKAGE_NAME = "app.package.name";
    private final String APP_NAME = "App Name";
    private final Integer BUILD_VERSION_SDK_INT = 28;
    private final String BUILD_MANUFACTURER = "Google";
    private final String BUILD_MODEL = "Android SDK built for x86_64";
    private final String BUILD_VERSION_RELEASE = "9";

    @Test
    public void createFromContext_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            UserAgentBuilder.createFromContext(null);
        });
    }

    @Test
    public void createFromContext_success() {
        final Context context = ApplicationProvider.getApplicationContext();
        final String value = UserAgentBuilder.createFromContext(context);
        assertFalse(TextUtils.isEmpty(value));
    }

    @Test
    public void testBuild_withAllSections() {
        final String result = new UserAgentBuilder().
            setSdkVersionName(SDK_VERSION_NAME).
            setSdkVersionCode(SDK_VERSION_CODE).
            setAppVersionName(APP_VERSION_NAME).
            setAppVersionCode(APP_VERSION_CODE).
            setAppPackageName(APP_PACKAGE_NAME).
            setAppName(APP_NAME).
            setBuildManufacturer(BUILD_MANUFACTURER).
            setBuildModel(BUILD_MODEL).
            setBuildVersionSdkInt(BUILD_VERSION_SDK_INT).
            setBuildVersionRelease(BUILD_VERSION_RELEASE).build();

        final String expected =
            "AndroidSDK/5.3.0 (51) AndroidApp/6.1.0 (app.package.name; App Name; 73) AndroidPlatform/28 (Google; Android SDK built for x86_64; 9)";
        assertEquals(expected, result);
    }

    @Test
    public void testBuild_missingAppVersionName() {
        final String result = new UserAgentBuilder().
            setSdkVersionCode(SDK_VERSION_CODE).
            setAppVersionName(APP_VERSION_NAME).
            setAppVersionCode(APP_VERSION_CODE).
            setAppPackageName(APP_PACKAGE_NAME).
            setAppName(APP_NAME).
            setBuildManufacturer(BUILD_MANUFACTURER).
            setBuildModel(BUILD_MODEL).
            setBuildVersionSdkInt(BUILD_VERSION_SDK_INT).
            setBuildVersionRelease(BUILD_VERSION_RELEASE).build();

        final String expected =
            "AndroidApp/6.1.0 (app.package.name; App Name; 73) AndroidPlatform/28 (Google; Android SDK built for x86_64; 9)";
        assertEquals(expected, result);
    }

    @Test
    public void testBuild_missingSectionValues() {
        final String result = new UserAgentBuilder().
            setSdkVersionName(SDK_VERSION_NAME).
            setSdkVersionCode(SDK_VERSION_CODE).
            setAppVersionName(APP_VERSION_NAME).
            setAppVersionCode(APP_VERSION_CODE).
            setAppPackageName(APP_PACKAGE_NAME).
            setBuildVersionSdkInt(BUILD_VERSION_SDK_INT).build();

        final String expected =
            "AndroidSDK/5.3.0 (51) AndroidApp/6.1.0 (app.package.name; ; 73) AndroidPlatform/28 (; ; )";
        assertEquals(expected, result);
    }

    @Test
    public void testBuild_withoutSectionVersions() {
        final String result = new UserAgentBuilder().
            setSdkVersionCode(SDK_VERSION_CODE).
            setAppVersionCode(APP_VERSION_CODE).
            setAppPackageName(APP_PACKAGE_NAME).
            setAppName(APP_NAME).
            setBuildManufacturer(BUILD_MANUFACTURER).
            setBuildModel(BUILD_MODEL).
            setBuildVersionRelease(BUILD_VERSION_RELEASE).build();

        assertNull(result);
    }
}
