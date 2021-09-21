/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.network;

import com.payoneer.checkout.BuildConfig;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

/**
 * Class for constructing the custom UserAgent header value
 */
final class UserAgentBuilder {

    private String sdkVersionName;
    private Integer sdkVersionCode;
    private String appVersionName;
    private Integer appVersionCode;
    private String appPackageName;
    private String appName;
    private String buildManufacturer;
    private String buildModel;
    private Integer buildVersionSdkInt;
    private String buildVersionRelease;

    String build() {
        String value = "";
        if (!TextUtils.isEmpty(sdkVersionName)) {
            value += ("AndroidSDK/" + sdkVersionName + " (" +
                toDetailsString(sdkVersionCode) + ") ");
        }
        if (!TextUtils.isEmpty(appVersionName)) {
            value += ("AndroidApp/" + appVersionName + " (" +
                toDetailsString(appPackageName) + "; " +
                toDetailsString(appName) + "; " +
                toDetailsString(appVersionCode) + ") ");
        }
        if (buildVersionSdkInt != null) {
            value += ("AndroidPlatform/" + buildVersionSdkInt + " (" +
                toDetailsString(buildManufacturer) + "; " +
                toDetailsString(buildModel) + "; " +
                toDetailsString(buildVersionRelease) + ")");
        }
        return value.isEmpty() ? null : value.trim();
    }

    private String toDetailsString(Object value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Construct a new UserAgent value from the provided Context
     *
     * @param context used to construct the default UserAgent
     * @return the newly created UserAgent value or null when it could not be created
     */
    @SuppressWarnings( "deprecation" )
    static String createFromContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        UserAgentBuilder builder = new UserAgentBuilder().
            setSdkVersionName(BuildConfig.VERSION_NAME).
            setSdkVersionCode(BuildConfig.VERSION_CODE).
            setBuildManufacturer(android.os.Build.MANUFACTURER).
            setBuildModel(android.os.Build.MODEL).
            setBuildVersionSdkInt(android.os.Build.VERSION.SDK_INT).
            setBuildVersionRelease(android.os.Build.VERSION.RELEASE);

        try {
            Context appContext = context.getApplicationContext();
            String appPackageName = appContext.getPackageName();
            PackageManager packageManager = appContext.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(appPackageName, 0);
            PackageInfo packageInfo = packageManager.getPackageInfo(appPackageName, 0);

            int versionCode;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = (int) packageInfo.getLongVersionCode();
            } else {
                versionCode = packageInfo.versionCode;
            }
            builder.setAppVersionName(packageInfo.versionName).
                setAppVersionCode(versionCode).
                setAppPackageName(appPackageName).
                setAppName(packageManager.getApplicationLabel(appInfo).toString());

        } catch (final PackageManager.NameNotFoundException e) {
            Log.w("Checkout", e);
        }
        return builder.build();
    }

    UserAgentBuilder setSdkVersionName(String sdkVersionName) {
        this.sdkVersionName = sdkVersionName;
        return this;
    }

    UserAgentBuilder setSdkVersionCode(Integer sdkVersionCode) {
        this.sdkVersionCode = sdkVersionCode;
        return this;
    }

    UserAgentBuilder setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
        return this;
    }

    UserAgentBuilder setAppVersionCode(Integer appVersionCode) {
        this.appVersionCode = appVersionCode;
        return this;
    }

    UserAgentBuilder setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
        return this;
    }

    UserAgentBuilder setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    UserAgentBuilder setBuildManufacturer(String buildManufacturer) {
        this.buildManufacturer = buildManufacturer;
        return this;
    }

    UserAgentBuilder setBuildModel(String buildModel) {
        this.buildModel = buildModel;
        return this;
    }

    UserAgentBuilder setBuildVersionSdkInt(Integer buildVersionSdkInt) {
        this.buildVersionSdkInt = buildVersionSdkInt;
        return this;
    }

    UserAgentBuilder setBuildVersionRelease(String buildVersionRelease) {
        this.buildVersionRelease = buildVersionRelease;
        return this;
    }
}
