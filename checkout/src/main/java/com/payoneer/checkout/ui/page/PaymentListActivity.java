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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.test.espresso.IdlingResource;

/**
 * The PaymentListActivity showing available payment methods in a list.
 */
public final class PaymentListActivity extends BasePaymentActivity implements PaymentListView {

    private PaymentListPresenter presenter;
    private PaymentList paymentList;
    private SwipeRefreshLayout swipeRefreshLayout;

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

        initSwipeRefreshlayout();
        initToolbar();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PaymentActivityResult result = PaymentActivityResult.fromActivityResult(requestCode, resultCode, data);
        presenter.setPaymentActivityResult(result);
    }

    @Override
    public void onPause() {
        super.onPause();
        paymentList.onStop();
        presenter.onStop();
        resetSwipeRefreshLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            close();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        swipeRefreshLayout.setRefreshing(false);
        overridePendingTransition(R.anim.no_animation, R.anim.no_animation);
    }

    @Override
    public void clearPaymentList() {
        paymentList.clear();
        resetSwipeRefreshLayout();
    }

    @Override
    public void showPaymentSession(PaymentSession session) {
        progressView.setVisible(false);
        setToolbar(Localization.translate(LIST_TITLE));
        paymentList.showPaymentSession(session);
        swipeRefreshLayout.setEnabled(session.swipeRefresh());
        idlingResources.setLoadIdlingState(true);
    }

    @Override
    public void showChargePaymentScreen(int requestCode, Operation operation) {
        Intent intent = ChargePaymentActivity.createStartIntent(this, operation);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(ChargePaymentActivity.getStartTransition(), R.anim.no_animation);
        idlingResources.setCloseIdlingState(true);
    }

    private void initSwipeRefreshlayout() {
        swipeRefreshLayout = findViewById(R.id.layout_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                presenter.onRefresh(paymentList.hasUserInputData());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void setToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }

    private void resetSwipeRefreshLayout() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
    }
}
