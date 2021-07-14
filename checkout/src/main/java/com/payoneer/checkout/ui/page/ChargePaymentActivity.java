/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.page;

import static com.payoneer.checkout.localization.LocalizationKey.CHARGE_TEXT;
import static com.payoneer.checkout.localization.LocalizationKey.CHARGE_TITLE;

import java.net.URL;
import java.util.Map;

import com.payoneer.checkout.R;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.model.PresetAccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

/**
 * The ChargePaymentActivity is the view displaying the loading animation while posting the operation.
 * The presenter of this view will post the PresetAccount operation to the Payment API.
 */
public final class ChargePaymentActivity extends BasePaymentActivity implements BasePaymentView {

    private final static String EXTRA_OPERATION = "operation";
    private ChargePaymentPresenter presenter;
    private Operation operation;

    /**
     * Create the start intent for this ChargePaymentActivity
     *
     * @param context Context to create the intent
     * @return newly created start intent
     */
    public static Intent createStartIntent(Context context, Operation operation) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        if (operation == null) {
            throw new IllegalArgumentException("operation may not be null");
        }
        Intent intent = new Intent(context, ChargePaymentActivity.class);
        intent.putExtra(EXTRA_OPERATION, operation);
        return intent;
    }

    /**
     * Create the start intent for this ChargePaymentActivity
     *
     * @param context Context to create the intent
     * @param account the preset account that should be processed
     * @return newly created start intent
     */
    public static Intent createStartIntent(Context context, PresetAccount account) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account may not be null");
        }
        Map<String, URL> links = account.getLinks();
        URL url = links != null ? links.get("operation") : null;

        if (url == null) {
            throw new IllegalArgumentException("PresetAccount does not contain an operation url");
        }
        Operation operation = new Operation(account.getCode(), account.getMethod(), account.getOperationType(), url);
        return createStartIntent(context, operation);
    }

    /**
     * Get the transition used when this Activity is being started
     *
     * @return the start transition of this activity
     */
    public static int getStartTransition() {
        return R.anim.fade_in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = getPaymentTheme().getChargePaymentTheme();
        if (theme != 0) {
            setTheme(theme);
        }
        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        if (bundle != null) {
            this.operation = bundle.getParcelable(EXTRA_OPERATION);
        }
        setContentView(R.layout.activity_chargepayment);

        progressView = new ProgressView(findViewById(R.id.layout_progress));
        this.presenter = new ChargePaymentPresenter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (this.operation != null) {
            savedInstanceState.putParcelable(EXTRA_OPERATION, this.operation);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        presenter.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        presenter.onStart(operation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress(boolean visible) {
        super.showProgress(visible);
        progressView.setLabels(Localization.translate(CHARGE_TITLE), Localization.translate(CHARGE_TEXT));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        if (!presenter.onBackPressed()) {
            super.onBackPressed();
            setOverridePendingTransition();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void setOverridePendingTransition() {
        overridePendingTransition(R.anim.no_animation, R.anim.fade_out);
    }
}
