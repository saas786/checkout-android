/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.page;

import com.payoneer.checkout.R;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.model.Interaction;
import com.payoneer.checkout.ui.PaymentActivityResult;
import com.payoneer.checkout.ui.PaymentResult;
import com.payoneer.checkout.ui.PaymentTheme;
import com.payoneer.checkout.ui.PaymentUI;
import com.payoneer.checkout.ui.dialog.PaymentDialogFragment;
import com.payoneer.checkout.ui.dialog.PaymentDialogFragment.PaymentDialogListener;
import com.payoneer.checkout.ui.dialog.PaymentDialogHelper;
import com.payoneer.checkout.ui.page.idlingresource.SimpleIdlingResource;
import com.payoneer.checkout.util.PaymentResultHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.IdlingResource;

/**
 * The base activity for payment activities.
 */
abstract class BasePaymentActivity extends AppCompatActivity implements BasePaymentView {

    ProgressView progressView;

    /** For testing only */
    SimpleIdlingResource closeIdlingResource;
    SimpleIdlingResource dialogIdlingResource;
    boolean closed;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(PaymentUI.getInstance().getOrientation());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        closed = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress(boolean visible) {
        progressView.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showWarningMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            PaymentDialogHelper.createSnackbar(getRootView(), message).show();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showConnectionErrorDialog(PaymentDialogListener listener) {
        progressView.setVisible(false);
        PaymentDialogFragment dialog = PaymentDialogHelper.createConnectionErrorDialog(listener);
        showPaymentDialog(dialog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDeleteAccountDialog(PaymentDialogListener listener, String displayLabel) {
        PaymentDialogFragment dialog = PaymentDialogHelper.createDeleteAccountDialog(listener, displayLabel);
        showPaymentDialog(dialog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPendingAccountDialog(PaymentDialogListener listener) {
        PaymentDialogFragment dialog = PaymentDialogHelper.createPendingAccountDialog(listener);
        showPaymentDialog(dialog);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void showInteractionDialog(Interaction interaction, PaymentDialogListener listener) {
        progressView.setVisible(false);
        PaymentDialogFragment dialog;
        if (Localization.hasInteraction(interaction)) {
            dialog = PaymentDialogHelper.createInteractionDialog(interaction, listener);
        } else {
            dialog = PaymentDialogHelper.createDefaultErrorDialog(listener);
        }
        showPaymentDialog(dialog);
    }

    public void showHintDialog(String networkCode, String type, PaymentDialogListener listener) {
        PaymentDialogFragment dialog = PaymentDialogHelper.createHintDialog(networkCode, type, listener);
        showPaymentDialog(dialog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaymentResult(int resultCode, PaymentResult result) {
        setResultIntent(resultCode, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void passOnActivityResult(PaymentActivityResult paymentActivityResult) {
        setResultIntent(paymentActivityResult.getResultCode(), paymentActivityResult.getPaymentResult());
        supportFinishAfterTransition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        supportFinishAfterTransition();
        setOverridePendingTransition();

        // for automated testing
        setCloseIdleState();
    }

    /**
     * Show a dialog fragment to the user
     *
     * @param dialog to be shown
     */
    public void showPaymentDialog(PaymentDialogFragment dialog) {
        dialog.show(getSupportFragmentManager());

        // For automated testing
        if (dialogIdlingResource != null) {
            dialogIdlingResource.setIdleState(true);
        }
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
     * Only called from test, creates and returns a new dialog IdlingResource
     */
    @VisibleForTesting
    public IdlingResource getDialogIdlingResource() {
        if (dialogIdlingResource == null) {
            dialogIdlingResource = new SimpleIdlingResource(getClass().getSimpleName() + "-dialogIdlingResource");
        }
        dialogIdlingResource.reset();
        return dialogIdlingResource;
    }

    /**
     * Only called from test, creates and returns a new close IdlingResource
     */
    @VisibleForTesting
    public IdlingResource getCloseIdlingResource() {
        if (closeIdlingResource == null) {
            closeIdlingResource = new SimpleIdlingResource(getClass().getSimpleName() + "-closeIdlingResource");
        }
        if (closed) {
            closeIdlingResource.setIdleState(true);
        } else {
            closeIdlingResource.reset();
        }
        return closeIdlingResource;
    }

    /**
     * Set the close idle state for the closeIdlingResource
     */
    void setCloseIdleState() {
        closed = true;
        if (closeIdlingResource != null) {
            closeIdlingResource.setIdleState(true);
        }
    }
}
