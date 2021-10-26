/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import com.payoneer.checkout.R;
import com.payoneer.checkout.util.PaymentUtils;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * The HeaderViewHolder holding and binding views for a header in the RecyclerView
 */
public final class HeaderViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView tvPresetWarningText;

    HeaderViewHolder(View parent) {
        super(parent);
        this.title = parent.findViewById(R.id.text_title);
        tvPresetWarningText = parent.findViewById(R.id.text_preset_warning);
    }

    static ViewHolder createInstance(View parent) {
        View view = View.inflate(parent.getContext(), R.layout.list_item_header, null);
        return new HeaderViewHolder(view);
    }

    void onBind(HeaderItem item) {
        PaymentUtils.setTestId(itemView, "label", "header");
        title.setText(item.getTitle());
        if (!TextUtils.isEmpty(item.getMessage())) {
            tvPresetWarningText.setVisibility(View.VISIBLE);
            tvPresetWarningText.setText(item.getMessage());
        }
    }
}
