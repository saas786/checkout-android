/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.page;

import static com.payoneer.checkout.localization.LocalizationKey.LIST_TITLE;

import com.payoneer.checkout.R;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.ui.PaymentActivityResult;
import com.payoneer.checkout.ui.list.PaymentList;
import com.payoneer.checkout.ui.model.PaymentSession;
import com.payoneer.checkout.ui.page.idlingresource.SimpleIdlingResource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.IdlingResource;

/**
 * The PaymentListActivity showing available payment methods in a list.
 */
public final class PaymentListActivity extends BasePaymentActivity implements PaymentListView {

    private PaymentListPresenter presenter;
    private PaymentList paymentList;

    // For automated testing
    private boolean loadCompleted;
    private SimpleIdlingResource loadIdlingResource;

    /**
     * Create the start intent for this PaymentListActivity.
     *
     * @param context Context to create the intent
     * @return newly created start intent
     */
    public static Intent createStartIntent(Context context) {
        return new Intent(context, PaymentListActivity.class);
    }

    /**
     * Get the transition used when this Activity is being started
     *
     * @return the start transition of this activity
     */
    public static int getStartTransition() {
        return R.anim.no_animation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = getPaymentTheme().getPaymentListTheme();
        if (theme != 0) {
            setTheme(theme);
        }
        setContentView(R.layout.activity_paymentlist);
        progressView = new ProgressView(findViewById(R.id.layout_progress));
        presenter = new PaymentListPresenter(this);
        paymentList = new PaymentList(this, presenter, findViewById(R.id.recyclerview_paymentlist));

        initToolbar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PaymentActivityResult result = PaymentActivityResult.fromActivityResult(requestCode, resultCode, data);
        presenter.setPaymentActivityResult(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        paymentList.onStop();
        presenter.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        loadCompleted = false;
        presenter.onStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            close();
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_animation, R.anim.no_animation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearPaymentList() {
        paymentList.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentSession(PaymentSession session) {
        progressView.setVisible(false);
        setToolbar(Localization.translate(LIST_TITLE));
        paymentList.showPaymentSession(session);

        // For automated UI testing
        this.loadCompleted = true;
        if (loadIdlingResource != null) {
            loadIdlingResource.setIdleState(loadCompleted);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showChargePaymentScreen(int requestCode, Operation operation) {
        Intent intent = ChargePaymentActivity.createStartIntent(this, operation);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(ChargePaymentActivity.getStartTransition(), R.anim.no_animation);

        // for automated testing
        setCloseIdleState();
    }

    /**
     * Only called from test, creates and returns a new IdlingResource
     */
    @VisibleForTesting
    public IdlingResource getLoadIdlingResource() {
        if (loadIdlingResource == null) {
            loadIdlingResource = new SimpleIdlingResource(getClass().getSimpleName() + "-loadIdlingResource");
        }
        if (loadCompleted) {
            loadIdlingResource.setIdleState(loadCompleted);
        } else {
            loadIdlingResource.reset();
        }
        return loadIdlingResource;
    }

    /**
     * Initialize the toolbar in this PaymentList
     */
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    /**
     * Set the action bar with a title and optional back arrow.
     *
     * @param title of the action bar
     */
    private void setToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }
}
