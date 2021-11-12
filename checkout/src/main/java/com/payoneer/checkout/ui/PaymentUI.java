/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui;

import com.payoneer.checkout.R;
import com.payoneer.checkout.localization.LocalLocalizationHolder;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.ui.page.ChargePaymentActivity;
import com.payoneer.checkout.ui.page.PaymentListActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.util.Patterns;

/**
 * The PaymentUI is the controller to initialize and launch the Payment Page.
 * The Payment Page shows payment methods which can be used to finalize payments through the Payment API.
 */
public final class PaymentUI {

    /** The orientation of the Payment page, by default it is in locked mode */
    private int orientation;

    /** The url pointing to the current list */
    private String listUrl;

    /** The cached payment theme */
    private PaymentTheme theme;

    private PaymentUI() {
        this.orientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED;
    }

    /**
     * Get the instance of this PaymentUI
     *
     * @return the instance of this PaymentUI
     */
    public static PaymentUI getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Get the listUrl in this PaymentUI
     *
     * @return the listUrl or null if not previously set
     */
    public String getListUrl() {
        return listUrl;
    }

    /**
     * Set the listUrl in this PaymentUI
     *
     * @param listUrl the listUrl to be set in this paymentUI
     */
    public void setListUrl(String listUrl) {

        if (TextUtils.isEmpty(listUrl)) {
            throw new IllegalArgumentException("listUrl may not be null or empty");
        }
        if (!Patterns.WEB_URL.matcher(listUrl).matches()) {
            throw new IllegalArgumentException("listUrl does not have a valid url format");
        }
        this.listUrl = listUrl;
    }

    /**
     * Get the orientation mode for the PaymentPage, by default the ActivityInfo.SCREEN_ORIENTATION_LOCKED is used.
     *
     * @return orientation mode
     */
    public int getOrientation() {
        return this.orientation;
    }

    /**
     * Set the orientation of the Payment Page, the following orientation modes are supported:
     *
     * ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
     * ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
     * ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
     * ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
     * ActivityInfo.SCREEN_ORIENTATION_LOCKED
     *
     * The SCREEN_ORIENTATION_LOCKED is by default used.
     *
     * @param orientation mode for the Payment Page
     */
    public void setOrientation(int orientation) {

        switch (orientation) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
            case ActivityInfo.SCREEN_ORIENTATION_LOCKED:
                this.orientation = orientation;
                break;
            default:
                throw new IllegalArgumentException("Orientation mode is not supported: " + orientation);
        }
    }

    /**
     * Get the PaymentTheme set in this PaymentUI. This method is not Thread safe and must be called from the Main UI Thread.
     *
     * @return the set PaymentTheme or the default PaymentTheme
     */
    public PaymentTheme getPaymentTheme() {
        return theme;
    }

    /**
     * Set the payment theme
     *
     * @param theme containing the Payment theme
     */
    public void setPaymentTheme(PaymentTheme theme) {
        this.theme = theme;
    }

    /**
     * Open the PaymentPage and instruct the page to immediately charge the PresetAccount.
     * If no PresetAccount is set in the ListResult then an error will be returned.
     *
     * @param activity the activity that will be notified when this PaymentPage is finished
     * @param requestCode the requestCode to be used for identifying results in the parent activity
     */
    public void chargePresetAccount(Activity activity, int requestCode) {
        Intent intent = ChargePaymentActivity.createStartIntent(activity);
        launchActivity(activity, intent, requestCode);
        activity.overridePendingTransition(ChargePaymentActivity.getStartTransition(), R.anim.no_animation);
    }

    /**
     * Open the PaymentPage containing the list of supported payment methods.
     *
     * @param activity the activity that will be notified when this PaymentPage is finished
     * @param requestCode the requestCode to be used for identifying results in the parent activity
     */
    public void showPaymentPage(Activity activity, int requestCode) {
        Intent intent = PaymentListActivity.createStartIntent(activity);
        launchActivity(activity, intent, requestCode);
        activity.overridePendingTransition(PaymentListActivity.getStartTransition(), R.anim.no_animation);
    }

    /**
     * Validate Android SDK Settings and Localization before launching the Activity.
     *
     * @param activity the activity that will be notified when this PaymentPage is finished
     * @param requestCode the requestCode to be used for identifying results in the parent activity
     */
    private void launchActivity(Activity activity, Intent intent, int requestCode) {
        if (listUrl == null) {
            throw new IllegalStateException("listUrl must be set before showing the PaymentPage");
        }
        if (activity == null) {
            throw new IllegalArgumentException("activity may not be null");
        }
        if (intent == null) {
            throw new IllegalArgumentException("intent may not be null");
        }
        initLocalization(activity);

        if (theme == null) {
            setPaymentTheme(PaymentTheme.createDefault());
        }
        activity.finishActivity(requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    private void initLocalization(Activity activity) {
        Localization loc = new Localization(new LocalLocalizationHolder(activity), null);
        Localization.setInstance(loc);
    }

    private static class InstanceHolder {
        static final PaymentUI INSTANCE = new PaymentUI();
    }
}
