/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.widget.FormWidget;
import com.payoneer.checkout.ui.widget.WidgetPresenter;
import com.payoneer.checkout.validation.ValidationResult;
import com.payoneer.checkout.validation.Validator;

import android.view.View;

/**
 * Internal class taking care of handling and routing events that happened inside a card including its widgets.
 * For example, if a user clicked the Pay button inside the card, this eventHandler will forward this event by calling the
 * appropiate message in the CardListener.
 */
class CardEventHandler implements WidgetPresenter {

    private final PaymentCardViewHolder holder;
    private final ListAdapter adapter;

    CardEventHandler(PaymentCardViewHolder holder, ListAdapter adapter) {
        this.holder = holder;
        this.adapter = adapter;
    }

    @Override
    public boolean requestFocusNextWidget(FormWidget currentWidget) {
        if (holder.hasValidPosition()) {
            return holder.requestFocusNextWidget(currentWidget);
        }
        return false;
    }

    @Override
    public void onActionClicked() {
        if (!holder.hasValidPosition()) {
            return;
        }
        boolean error = false;
        for (FormWidget widget : holder.getFormWidgets().values()) {
            if (!widget.validate()) {
                error = true;
            }
            widget.clearFocus();
        }
        if (!error) {
            getCardListener().onActionClicked(holder.getPaymentCard(), holder.getFormWidgets());
        }
    }

    @Override
    public void onHintClicked(String type) {
        if (holder.hasValidPosition()) {
            PaymentCard card = holder.getPaymentCard();
            getCardListener().onHintClicked(card.getCode(), type);
        }
    }

    @Override
    public void hideKeyboard() {
        if (holder.hasValidPosition()) {
            getCardListener().onHideKeyboard();
        }
    }

    @Override
    public void showKeyboard(View view) {
        if (holder.hasValidPosition()) {
            getCardListener().onShowKeyboard(view);
        }
    }

    @Override
    public int getMaxInputLength(String code, String type) {
        if (holder.hasValidPosition()) {
            Validator validator = Validator.getInstance();
            return validator.getMaxInputLength(code, type);
        }
        return -1;
    }

    @Override
    public ValidationResult validate(String type, String value1, String value2) {
        if (holder.hasValidPosition()) {
            PaymentCard card = holder.getPaymentCard();
            Validator validator = Validator.getInstance();
            ValidationResult result = validator.validate(card.getPaymentMethod(), card.getCode(), type, value1, value2);

            if (result.isError()) {
                result.setMessage(Localization.translateError(card.getCode(), result.getError()));
            }
            return result;
        }
        return null;
    }

    @Override
    public void onTextInputChanged(String type, String text) {
        if (holder.hasValidPosition()) {
            PaymentCard card = holder.getPaymentCard();

            if (card.onTextInputChanged(type, text)) {
                adapter.notifyItemChanged(holder.getAdapterPosition());
            }
        }
    }

    void onDeleteClicked() {
        if (holder.hasValidPosition()) {
            getCardListener().onDeleteClicked(holder.getPaymentCard());
        }
    }

    void onCardClicked() {
        if (holder.hasValidPosition()) {
            getCardListener().onCardClicked(holder.getAdapterPosition());
        }
    }

    boolean isInputTypeHidden(String code, String type) {
        Validator validator = Validator.getInstance();
        return validator.isHidden(code, type);
    }

    private PaymentCardListener getCardListener() {
        return adapter.getCardListener();
    }
}
