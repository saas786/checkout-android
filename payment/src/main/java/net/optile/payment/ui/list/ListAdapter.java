/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.list;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.validation.ValidationResult;

/**
 * The ListAdapter handling the items in this RecyclerView list
 */
final class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ListItem> items;
    private final PaymentList list;

    ListAdapter(PaymentList list, List<ListItem> items) {
        this.list = list;
        this.items = items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull
    ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaymentCard card = getItemWithViewType(viewType).getPaymentCard();

        if (card == null) {
            return HeaderViewHolder.createInstance(this, parent);
        } else if (card instanceof NetworkCard) {
            return NetworkCardViewHolder.createInstance(this, (NetworkCard) card, parent);
        } else {
            return AccountCardViewHolder.createInstance(this, (AccountCard) card, parent);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = items.get(position);

        if (item.hasPaymentCard()) {
            PaymentCardViewHolder ph = (PaymentCardViewHolder) holder;
            ph.onBind(item.getPaymentCard());
            ph.expand(list.getSelected() == position);
        } else {
            ((HeaderViewHolder) holder).onBind((HeaderItem) item);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemViewType(int position) {
        return items.get(position).viewType;
    }

    void onItemClicked(int position) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.onItemClicked(position);
    }

    void hideKeyboard(int position) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.hideKeyboard();
    }

    void showKeyboard(int position) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.showKeyboard();
    }

    void showDialogFragment(int position, DialogFragment dialog, String tag) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.showDialogFragment(dialog, tag);
    }

    void onActionClicked(int position) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.onActionClicked(position);
    }

    void onHintClicked(int position, String type) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.onHintClicked(position, type);
    }
    
    Context getContext() {
        return list.getContext();
    }

    void onTextInputChanged(int position, String type, String text) {
        if (isInvalidPosition(position)) {
            return;
        }
        ListItem item = items.get(position);

        if (item.hasPaymentCard()) {
            PaymentCard card = item.getPaymentCard();

            if (card.onTextInputChanged(type, text)) {
                notifyItemChanged(position);
            }
        }
    }

    ValidationResult validate(int position, String type, String value1, String value2) {
        if (isInvalidPosition(position)) {
            return null;
        }
        return list.validate(position, type, value1, value2);
    }

    PaymentTheme getPaymentTheme() {
        return PaymentUI.getInstance().getPaymentTheme();
    }

    LanguageFile getPageLanguageFile() {
        return list.getPaymentSession().getLang();
    }

    LayoutInflater getLayoutInflater() {
        return list.getLayoutInflater();
    }

    ListItem getItemFromIndex(int index) {
        return index >= 0 && index < items.size() ? items.get(index) : null;
    }

    private ListItem getItemWithViewType(int viewType) {

        for (ListItem item : items) {
            if (item.viewType == viewType) {
                return item;
            }
        }
        return null;
    }

    private boolean isInvalidPosition(int position) {
        return (position < 0) || (position >= items.size());
    }
}
