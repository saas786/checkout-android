/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import com.payoneer.checkout.R;
import com.payoneer.checkout.util.PaymentUtils;

import android.view.View;
import android.widget.ViewSwitcher;

/**
 * Class displaying the icon in a payment card
 */
class IconView {
    private final ViewSwitcher switcher;

    /**
     * Construct a new IconView
     *
     * @param parent of this view
     */
    IconView(View parent) {
        switcher = parent.findViewById(R.id.viewswitcher_icon);
        PaymentUtils.setTestId(switcher, "card", "iconview");
    }

    void setListener(final IconClickListener listener) {
        switcher.setClickable(true);
        switcher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                listener.onIconClick(switcher.getDisplayedChild());
            }
        });
    }

    void hide() {
        switcher.setVisibility(View.GONE);
    }

    void showIcon(int index) {
        switcher.setVisibility(View.VISIBLE);
        switcher.setDisplayedChild(index);
    }

    interface IconClickListener {
        void onIconClick(int index);
    }
}
