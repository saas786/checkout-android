/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import static com.payoneer.checkout.core.PaymentInputType.EXPIRY_DATE;
import static com.payoneer.checkout.core.PaymentInputType.EXPIRY_MONTH;
import static com.payoneer.checkout.core.PaymentInputType.EXPIRY_YEAR;
import static com.payoneer.checkout.core.PaymentInputType.VERIFICATION_CODE;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.payoneer.checkout.R;
import com.payoneer.checkout.model.InputElement;
import com.payoneer.checkout.model.InputElementType;
import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.widget.ButtonWidget;
import com.payoneer.checkout.ui.widget.CheckBoxWidget;
import com.payoneer.checkout.ui.widget.DateWidget;
import com.payoneer.checkout.ui.widget.FormWidget;
import com.payoneer.checkout.ui.widget.SelectWidget;
import com.payoneer.checkout.ui.widget.TextInputWidget;
import com.payoneer.checkout.ui.widget.VerificationCodeWidget;
import com.payoneer.checkout.util.NetworkLogoLoader;
import com.payoneer.checkout.util.PaymentUtils;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The PaymentCardViewHolder holding the header and input widgets
 */
public abstract class PaymentCardViewHolder extends RecyclerView.ViewHolder {

    final static String BUTTON_WIDGET = "buttonWidget";
    final ViewGroup formLayout;
    final Map<String, FormWidget> widgets;
    final ImageView cardLogoView;
    final IconView iconView;
    final PaymentCard paymentCard;
    final CardEventHandler cardHandler;
    final ListAdapter adapter;

    PaymentCardViewHolder(ListAdapter adapter, View parent, PaymentCard paymentCard) {
        super(parent);

        this.adapter = adapter;
        this.paymentCard = paymentCard;
        this.cardHandler = new CardEventHandler(this, adapter);
        this.formLayout = parent.findViewById(R.id.layout_form);
        this.widgets = new LinkedHashMap<>();
        this.cardLogoView = parent.findViewById(R.id.image_logo);
        this.iconView = new IconView(parent);

        View view = parent.findViewById(R.id.layout_header);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardHandler.onCardClicked();
            }
        });
    }

    public FormWidget getFormWidget(String name) {
        return widgets.get(name);
    }

    PaymentCard getPaymentCard() {
        return paymentCard;
    }

    Map<String, FormWidget> getFormWidgets() {
        return widgets;
    }

    boolean hasValidPosition() {
        return adapter.validPosition(getAdapterPosition());
    }

    void addButtonWidget() {
        if (!widgets.containsKey(BUTTON_WIDGET)) {
            addWidget(new ButtonWidget(BUTTON_WIDGET));
        }
    }

    void addVerificationCodeWidget() {
        if (!widgets.containsKey(VERIFICATION_CODE)) {
            addWidget(new VerificationCodeWidget(VERIFICATION_CODE));
        }
    }

    void addExpiryDateWidget() {
        if (!widgets.containsKey(EXPIRY_DATE)) {
            addWidget(new DateWidget(EXPIRY_DATE));
        }
    }

    void addWidget(FormWidget widget) {
        String name = widget.getName();
        if (!widgets.containsKey(name)) {
            widget.setPresenter(cardHandler);
            widgets.put(name, widget);
        }
    }

    void handleCardClicked() {
        cardHandler.onCardClicked();
    }

    void addElementWidgets(PaymentCard card) {
        String code = card.getNetworkCode();
        List<InputElement> elements = card.getInputElements();
        boolean elementsContainExpiryDate = PaymentUtils.containsExpiryDate(elements);

        for (InputElement element : elements) {
            String name = element.getName();
            if (cardHandler.isInputTypeHidden(code, name)) {
                continue;
            }
            switch (element.getName()) {
                case VERIFICATION_CODE:
                    addVerificationCodeWidget();
                    break;
                case EXPIRY_MONTH:
                case EXPIRY_YEAR:
                    if (elementsContainExpiryDate) {
                        addExpiryDateWidget();
                        break;
                    }
                default:
                    addElementWidget(element);
            }
        }
    }

    private void addElementWidget(InputElement element) {
        String name = element.getName();
        FormWidget widget;
        switch (element.getType()) {
            case InputElementType.SELECT:
                widget = new SelectWidget(name);
            case InputElementType.CHECKBOX:
                widget = new CheckBoxWidget(name);
            default:
                widget = new TextInputWidget(name);
        }
        addWidget(widget);
    }


    void layoutWidgets() {
        ViewGroup rowLayout = null;
        boolean rowAdded = false;

        if (checkLayoutWidgetsInRow(VERIFICATION_CODE, EXPIRY_DATE)) {
            LayoutInflater inflater = LayoutInflater.from(formLayout.getContext());
            rowLayout = (ViewGroup) inflater.inflate(R.layout.layout_widget_row, formLayout, false);
        }
        for (FormWidget widget : widgets.values()) {
            String name = widget.getName();

            if (rowLayout != null && (VERIFICATION_CODE.equals(name) || EXPIRY_DATE.equals(name))) {
                layoutWidgetInRow(widget, rowLayout);
                if (!rowAdded) {
                    formLayout.addView(rowLayout);
                    rowAdded = true;
                }
                continue;
            }
            formLayout.addView(widget.inflate(formLayout));
        }
    }

    private void layoutWidgetInRow(FormWidget widget, ViewGroup rowLayout) {
        View view = widget.inflate(rowLayout);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.weight = 1;
        view.setLayoutParams(params);
        rowLayout.addView(view);
    }

    /**
     * Check if two widgets can be placed next to eachother in a row
     *
     * @param name1 contains name of the first widget
     * @param name2 contains name of the second widget
     * @return true when they can be combined into one row, false otherwise
     */
    private boolean checkLayoutWidgetsInRow(String name1, String name2) {
        String nextName = null;
        for (String name : widgets.keySet()) {
            if (nextName != null) {
                return name.equals(nextName);
            }
            if (name.equals(name1)) {
                nextName = name2;
            } else if (name.equals(name2)) {
                nextName = name1;
            }
        }
        return false;
    }

    boolean requestFocusNextWidget(FormWidget currentWidget) {
        boolean requestFocus = false;
        for (Map.Entry<String, FormWidget> entry : widgets.entrySet()) {
            FormWidget widget = entry.getValue();
            if (requestFocus) {
                if (widget.requestFocus()) {
                    return true;
                }
            } else {
                requestFocus = (widget == currentWidget);
            }
        }
        return false;
    }

    void expand(boolean expand) {
        if (!paymentCard.hasEmptyForm()) {
            formLayout.setVisibility(expand ? View.VISIBLE : View.GONE);
        }
    }

    void onBind() {
        for (Map.Entry<String, FormWidget> entry : widgets.entrySet()) {
            FormWidget widget = entry.getValue();

            switch (entry.getKey()) {
                case BUTTON_WIDGET:
                    bindButtonWidget((ButtonWidget) widget, paymentCard);
                    break;
                case VERIFICATION_CODE:
                    bindVerificationCodeWidget((VerificationCodeWidget) widget, paymentCard);
                    break;
                case EXPIRY_DATE:
                    bindDateWidget((DateWidget) widget, paymentCard);
                    break;
                default:
                    bindElementWidget(widget, paymentCard);
            }
        }
    }

    void bindButtonWidget(ButtonWidget widget, PaymentCard card) {
        widget.onBind(card.getButton());
    }

    void bindVerificationCodeWidget(VerificationCodeWidget widget, PaymentCard card) {
        InputElement element = card.getInputElement(VERIFICATION_CODE);
        widget.onBind(card.getNetworkCode(), element);
    }

    void bindDateWidget(DateWidget widget, PaymentCard card) {
        InputElement month = card.getInputElement(EXPIRY_MONTH);
        InputElement year = card.getInputElement(EXPIRY_YEAR);
        widget.onBind(card.getNetworkCode(), month, year);
    }

    void bindElementWidget(FormWidget widget, PaymentCard card) {
        InputElement element = card.getInputElement(widget.getName());
        String code = card.getNetworkCode();

        if (widget instanceof SelectWidget) {
            ((SelectWidget) widget).onBind(code, element);
        } else if (widget instanceof TextInputWidget) {
            ((TextInputWidget) widget).onBind(code, element);
        }
    }

    void bindLabel(TextView view, String label, boolean hideWhenEmpty) {
        view.setText(label);
        if (hideWhenEmpty && TextUtils.isEmpty(label)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    void bindCardLogo(int logoResId) {
        cardLogoView.setImageResource(logoResId);
    }

    void bindCardLogo(String networkCode, URL url) {
        if (networkCode == null || url == null) {
            return;
        }
        NetworkLogoLoader.loadNetworkLogo(cardLogoView, networkCode, url);
    }

    void setLastImeOptions() {
        List<String> keys = new ArrayList<>(widgets.keySet());
        Collections.reverse(keys);

        for (String key : keys) {
            FormWidget widget = widgets.get(key);
            if (widget != null && widget.setLastImeOptionsWidget()) {
                break;
            }
        }
    }
}
