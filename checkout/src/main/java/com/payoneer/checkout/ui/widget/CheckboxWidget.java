/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.payoneer.checkout.R;
import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.model.CheckboxMode;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Widget for showing the CheckBox input element
 */
public class CheckboxWidget extends FormWidget {

    private SwitchMaterial switchView;
    private TextView labelView;

    /**
     * Construct a new CheckBoxWidget
     *
     * @param name name identifying this widget
     */
    public CheckboxWidget(String name) {
        super(name);
    }

    @Override
    public View inflate(ViewGroup parent) {
        inflateWidgetView(parent, R.layout.widget_checkbox);
        labelView = widgetView.findViewById(R.id.label_value);
        switchView = widgetView.findViewById(R.id.checkbox_value);
        return widgetView;
    }

    @Override
    public void putValue(Operation operation) throws PaymentException {
        operation.putBooleanValue(name, switchView.isChecked());
    }

    /**
     * Bind this CheckboxWidget to the mode and label.
     * For now the required and required preselected are not handled client-side and will result
     * in a server-side error if the user did not select it.
     *
     * @param mode checkbox mode
     * @param label shown to the user
     */
    public void onBind(String mode, String label) {
        labelView.setText(label);
        switch (mode) {
            case CheckboxMode.OPTIONAL:
            case CheckboxMode.REQUIRED:
                setVisible(true);
                switchView.setVisibility(View.VISIBLE);
                switchView.setChecked(false);
                break;
            case CheckboxMode.OPTIONAL_PRESELECTED:
            case CheckboxMode.REQUIRED_PRESELECTED:
                setVisible(true);
                switchView.setVisibility(View.VISIBLE);
                switchView.setChecked(true);
                break;
            case CheckboxMode.FORCED:
                setVisible(false);
                switchView.setVisibility(View.GONE);
                switchView.setChecked(true);
                break;
            case CheckboxMode.FORCED_DISPLAYED:
                setVisible(true);
                switchView.setVisibility(View.GONE);
                switchView.setChecked(true);
                break;
            default:
                setVisible(false);
                switchView.setVisibility(View.GONE);
                switchView.setChecked(false);
        }
    }
}
