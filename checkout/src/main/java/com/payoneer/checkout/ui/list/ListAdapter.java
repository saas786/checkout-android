/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import com.payoneer.checkout.ui.model.AccountCard;
import com.payoneer.checkout.ui.model.NetworkCard;
import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.model.PresetCard;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * The ListAdapter handling the items in this RecyclerView list
 */
final class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final PaymentCardListener cardListener;
    private final PaymentItemList itemList;

    ListAdapter(PaymentCardListener cardListener, PaymentItemList itemList) {
        this.cardListener = cardListener;
        this.itemList = itemList;
    }

    @Override
    public @NonNull
    ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItem item = itemList.getItemWithViewType(viewType);
        if (HeaderItem.isHeaderItem(item)) {
            return HeaderViewHolder.createInstance(parent);
        }
        PaymentCard card = ((PaymentCardItem)item).getPaymentCard();
        if (card instanceof NetworkCard) {
            return NetworkCardViewHolder.createInstance(this, (NetworkCard) card, parent);
        } else if (card instanceof AccountCard) {
            return AccountCardViewHolder.createInstance(this, (AccountCard) card, parent);
        } else {
            return PresetCardViewHolder.createInstance(this, (PresetCard) card, parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = itemList.getItem(position);

        if (HeaderItem.isHeaderItem(item)) {
            ((HeaderViewHolder) holder).onBind((HeaderItem) item);
        } else {
            PaymentCardViewHolder ph = (PaymentCardViewHolder) holder;
            ph.onBind();
            ph.expand(itemList.getSelectedIndex() == position);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.getItemViewType(position);
    }

    PaymentCardListener getCardListener() {
        return cardListener;
    }

    boolean validPosition(int position) {
        return itemList.validIndex(position);
    }

}
