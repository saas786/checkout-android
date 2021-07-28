/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import java.util.Map;

import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.model.PaymentSection;
import com.payoneer.checkout.ui.model.PaymentSession;
import com.payoneer.checkout.ui.widget.FormWidget;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * The PaymentList showing available payment methods and accounts in a list
 */
public final class PaymentList {
    private final Activity activity;
    private final PaymentListListener listener;
    private final RecyclerView recyclerView;
    private final ListAdapter adapter;
    private final PaymentItemList itemList;

    private PaymentSession session;
    private int nextViewType;

    /**
     * Construct a new PaymentList handling the RecyclerView
     *
     * @param activity that contains this PaymentList
     * @param listener notified about events from this PaymentList
     * @param recyclerView for showing the list of payment options
     */
    public PaymentList(Activity activity, PaymentListListener listener, RecyclerView recyclerView) {
        this.activity = activity;
        this.listener = listener;
        this.recyclerView = recyclerView;

        this.itemList = new PaymentItemList();
        this.adapter = new ListAdapter(createCardListener(), itemList);

        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();

        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    public void onStop() {
        hideKeyboard();
    }

    public void clear() {
        if (session != null) {
            session.reset();
            session = null;
        }
        itemList.clear();
        adapter.notifyDataSetChanged();
    }

    public boolean hasUserInputData() {
        return session != null ? session.hasUserInputData() : false;
    }

    public void showPaymentSession(PaymentSession session) {
        if (this.session == session) {
            setVisible(true);
            return;
        }
        clear();
        this.session = session;
        setPaymentSessionItems(session);

        setVisible(true);
        adapter.notifyDataSetChanged();

        recyclerView.scrollToPosition(itemList.getSelectedIndex());
    }

    public void setVisible(boolean visible) {
        recyclerView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private PaymentCardListener createCardListener() {
        return new PaymentCardListener() {
            @Override
            public void onHideKeyboard() {
                hideKeyboard();
            }

            @Override
            public void onShowKeyboard(View view) {
                showKeyboard(view);
            }

            @Override
            public void onDeleteClicked(PaymentCard paymentCard) {
                handleDeleteClicked(paymentCard);
            }

            @Override
            public void onHintClicked(String networkCode, String type) {
                handleHintClicked(networkCode, type);
            }

            @Override
            public void onActionClicked(PaymentCard paymentCard, Map<String, FormWidget> widgets) {
                handleActionClicked(paymentCard, widgets);
            }

            @Override
            public void onCardClicked(int position) {
                handleCardClicked(position);
            }
        };
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            View curFocus = activity.getCurrentFocus();
            IBinder binder = curFocus != null ? curFocus.getWindowToken() : recyclerView.getWindowToken();
            imm.hideSoftInputFromWindow(binder, 0);
        }
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    private void handleDeleteClicked(PaymentCard paymentCard) {
        hideKeyboard();
        listener.onDeleteClicked(paymentCard);
    }

    private void handleHintClicked(String networkCode, String type) {
        listener.onHintClicked(networkCode, type);
    }

    private void handleActionClicked(PaymentCard paymentCard, Map<String, FormWidget> widgets) {
        hideKeyboard();
        listener.onActionClicked(paymentCard, widgets);
    }

    private void handleCardClicked(int position) {
        int curIndex = itemList.getSelectedIndex();
        if (position == curIndex) {
            itemList.setSelectedIndex(-1);
            adapter.notifyItemChanged(position);
            hideKeyboard();
        } else {
            itemList.setSelectedIndex(position);
            adapter.notifyItemChanged(curIndex);
            adapter.notifyItemChanged(position);
            scrollAndCloseKeyboard(position);
        }
    }

    private void scrollAndCloseKeyboard(int position) {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(activity) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected void onStop() {
                hideKeyboard();
            }
        };
        smoothScroller.setTargetPosition(position);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if (manager != null) {
            manager.startSmoothScroll(smoothScroller);
        }
    }

    private void setPaymentSessionItems(PaymentSession paymentSession) {
        for (PaymentSection section : paymentSession.getPaymentSections()) {
            addPaymentSectionItems(section);
        }
    }

    private void addPaymentSectionItems(PaymentSection section) {
        itemList.addItem(new HeaderItem(nextViewType(), section.getLabel()), false);
        for (PaymentCard card : section.getPaymentCards()) {
            PaymentCardItem item = new PaymentCardItem(nextViewType(), card);
            itemList.addItem(item, card.isPreselected());
        }
    }

    private int nextViewType() {
        return nextViewType++;
    }
}
