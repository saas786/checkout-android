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
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.model.CheckboxMode;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Widget for showing the CheckBox input element
 */
public class CheckboxWidget extends FormWidget {

    private SwitchMaterial value;
    private TextView label;
    private CheckboxSettings settings;
    
    /**
     * Construct a new CheckBoxWidget
     *
     * @param name name identifying this widget
     */
    public CheckboxWidget(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View inflate(ViewGroup parent) {
        inflateWidgetView(parent, R.layout.widget_checkbox);
        label = widgetView.findViewById(R.id.label_value);
        value = widgetView.findViewById(R.id.checkbox_value);
        return widgetView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {
        Log.i("AAAAAAA", "put value: " + name + ", " + value.isChecked());
        operation.putBooleanValue(name, value.isChecked());
    }

    public void onBind(CheckboxSettings settings) {
        label.setText(Localization.translate(settings.getLabelKey()));
        switch (settings.getCheckboxMode()) {
            case CheckboxMode.OPTIONAL:
            case CheckboxMode.REQUIRED:
                setVisible(true);
                value.setVisibility(View.VISIBLE);
                value.setChecked(false);
                break;
            case CheckboxMode.OPTIONAL_PRESELECTED:
            case CheckboxMode.REQUIRED_PRESELECTED:
                setVisible(true);
                value.setVisibility(View.VISIBLE);                
                value.setChecked(true);
                break;
            case CheckboxMode.FORCED:
                setVisible(false);
                value.setVisibility(View.GONE);                
                value.setChecked(true);
                break;
            case CheckboxMode.FORCED_DISPLAYED:
                setVisible(true);
                value.setVisibility(View.GONE);                
                value.setChecked(true);
                break;
            default:
                setVisible(false);
                value.setVisibility(View.GONE);                
                value.setChecked(false);
        }
    }
}
