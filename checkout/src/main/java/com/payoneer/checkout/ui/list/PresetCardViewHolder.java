/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import com.google.android.material.card.MaterialCardView;
import com.payoneer.checkout.R;
import com.payoneer.checkout.model.AccountMask;
import com.payoneer.checkout.ui.model.PresetCard;
import com.payoneer.checkout.util.PaymentUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The PresetCardViewHolder class holding and binding views for an PresetCard
 */
final class PresetCardViewHolder extends PaymentCardViewHolder {

    private final TextView title;
    private final TextView subtitle;
    private final MaterialCardView card;

    private PresetCardViewHolder(ListAdapter adapter, View parent, PresetCard presetCard) {
        super(adapter, parent, presetCard);
        title = parent.findViewById(R.id.text_title);
        subtitle = parent.findViewById(R.id.text_subtitle);
        card = parent.findViewById(R.id.card_preset);
        card.setCheckable(true);

        addButtonWidget();
        layoutWidgets();
    }

    static RecyclerView.ViewHolder createInstance(ListAdapter adapter, PresetCard presetCard, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_presetcard, parent, false);
        return new PresetCardViewHolder(adapter, view, presetCard);
    }

    void onBind() {
        super.onBind();

        PaymentUtils.setTestId(itemView, "card", "preset");
        PresetCard card = (PresetCard) paymentCard;
        subtitle.setVisibility(View.GONE);
        title.setText(card.getLabel());

        AccountMask mask = card.getMaskedAccount();
        if (mask != null) {
            setExpiryDateSubtitle(subtitle, mask);
        }
        bindCardLogo(paymentCard.getCode(), card.getLink("logo"));
    }

    void expand(boolean expand) {
        super.expand(expand);
        card.setChecked(expand);
    }
}
