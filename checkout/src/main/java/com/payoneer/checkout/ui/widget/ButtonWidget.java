/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import com.payoneer.checkout.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Widget for showing the Submit (pay) button
 */
public final class ButtonWidget extends FormWidget {

    private Button button;

    public ButtonWidget(String category, String name) {
        super(category, name);
    }

    @Override
    public View inflate(ViewGroup parent) {
        inflateWidgetView(parent, R.layout.widget_button);

        button = widgetView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnClick();
            }
        });
        return widgetView;
    }

    public void onBind(String label) {
        button.setText(label);
    }

    private void handleOnClick() {
        presenter.onActionClicked();
    }
}
