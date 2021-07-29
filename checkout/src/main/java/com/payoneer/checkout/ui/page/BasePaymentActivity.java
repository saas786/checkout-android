/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.page;

import com.payoneer.checkout.R;
import com.payoneer.checkout.localization.InteractionMessage;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.ui.PaymentActivityResult;
import com.payoneer.checkout.ui.PaymentResult;
import com.payoneer.checkout.ui.PaymentTheme;
import com.payoneer.checkout.ui.PaymentUI;
import com.payoneer.checkout.ui.dialog.PaymentDialogFragment;
import com.payoneer.checkout.ui.dialog.PaymentDialogFragment.PaymentDialogListener;
import com.payoneer.checkout.ui.dialog.PaymentDialogHelper;
import com.payoneer.checkout.ui.page.idlingresource.PaymentIdlingResources;
import com.payoneer.checkout.util.PaymentResultHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The base activity for payment activities.
 */
abstract class BasePaymentActivity extends AppCompatActivity implements BasePaymentView {

    ProgressView progressView;

    // Automated testing
    PaymentIdlingResources idlingResources;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(PaymentUI.getInstance().getOrientation());
        idlingResources = new PaymentIdlingResources(getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        idlingResources.setCloseIdlingState(false);
    }

    @Override
    public void showProgress(boolean visible) {
        progressView.setVisible(visible);
    }

    @Override
    public void showWarningMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            PaymentDialogHelper.createSnackbar(getRootView(), message).show();
        }
    }

    @Override
    public void showConnectionErrorDialog(PaymentDialogListener listener) {
        progressView.setVisible(false);
        PaymentDialogFragment dialog = PaymentDialogHelper.createConnectionErrorDialog(listener);
        showPaymentDialog(dialog);
    }

    @Override
    public void showDeleteAccountDialog(PaymentDialogListener listener, String displayLabel) {
        PaymentDialogFragment dialog = PaymentDialogHelper.createDeleteAccountDialog(listener, displayLabel);
        showPaymentDialog(dialog);
    }

    @Override
    public void showRefreshAccountDialog(PaymentDialogListener listener) {
        PaymentDialogFragment dialog = PaymentDialogHelper.createRefreshAccountDialog(listener);
        showPaymentDialog(dialog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showInteractionDialog(InteractionMessage interactionMessage, PaymentDialogListener listener) {
        progressView.setVisible(false);
        PaymentDialogFragment dialog;
        if (Localization.hasInteractionMessage(interactionMessage)) {
            dialog = PaymentDialogHelper.createInteractionDialog(interactionMessage, listener);
        } else {
            dialog = PaymentDialogHelper.createDefaultErrorDialog(listener);
        }
        showPaymentDialog(dialog);
    }

    @Override
    public void showHintDialog(String networkCode, String type, PaymentDialogListener listener) {
        PaymentDialogFragment dialog = PaymentDialogHelper.createHintDialog(networkCode, type, listener);
        showPaymentDialog(dialog);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setPaymentResult(int resultCode, PaymentResult result) {
        setResultIntent(resultCode, result);
    }

    @Override
    public void passOnActivityResult(PaymentActivityResult paymentActivityResult) {
        setResultIntent(paymentActivityResult.getResultCode(), paymentActivityResult.getPaymentResult());
        supportFinishAfterTransition();
        idlingResources.setCloseIdlingState(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        idlingResources.setCloseIdlingState(true);
    }
    
    @Override
    public void close() {
        supportFinishAfterTransition();
        setOverridePendingTransition();
        idlingResources.setCloseIdlingState(true);
    }

    void showPaymentDialog(PaymentDialogFragment dialog) {
        dialog.show(getSupportFragmentManager());
        idlingResources.setDialogIdlingState(true);
    }
    
    /**
     * Get the current PaymentTheme from the PaymentUI.
     *
     * @return the current PaymentTheme
     */
    PaymentTheme getPaymentTheme() {
        return PaymentUI.getInstance().getPaymentTheme();
    }

    /**
     * Get the root view of this Activity.
     *
     * @return the root view
     */
    View getRootView() {
        return ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * Set the ActivityResult with the given resultCode and PaymentResult.
     *
     * @param resultCode of the ActivityResult
     * @param result to be added as extra to the intent
     */
    void setResultIntent(int resultCode, PaymentResult result) {
        Intent intent = new Intent();
        PaymentResultHelper.putIntoResultIntent(result, intent);
        setResult(resultCode, intent);
    }

    /**
     * Set the overridePendingTransition that will be used when moving back to another Activity
     */
    void setOverridePendingTransition() {
        overridePendingTransition(R.anim.no_animation, R.anim.no_animation);
    }

    /**
     * Only called from UI tests, returns the PaymentIdlingResources instance
     *
     * @return PaymentIdlingResources containing the IdlingResources used in this Activity
     */
    @VisibleForTesting
    public PaymentIdlingResources getPaymentIdlingResources() {
        return idlingResources;
    }
}
