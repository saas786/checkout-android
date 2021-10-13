/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import java.util.Map;

import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.widget.FormWidget;

import android.view.View;

/**
 * Implement this interface for listening to PaymentCard events.
 */
interface PaymentCardListener {

    /**
     * Notify the listener that the keyboard should be hidden
     */
    void onHideKeyboard();

    /**
     * Notify the listener that the keyboard should be shown for the given view
     *
     * @param view that needs the keyboard
     */
    void onShowKeyboard(View view);

    /**
     * Notify this listener that the payment card should be deleted
     *
     * @param paymentCard that should be deleted
     */
    void onDeleteClicked(PaymentCard paymentCard);

    /**
     * Notify this listener that the hint icon is clicked for the given networkCoce and type
     *
     * @param networkCode code of the hint
     * @param type of the hint
     */
    void onHintClicked(String networkCode, String type);

    /**
     * Notify that an action is required for the paymentCard
     *
     * @param paymentCard that initiated the action
     * @param widgets containing values that should be send
     */
    void onActionClicked(PaymentCard paymentCard, Map<String, FormWidget> widgets);

    /**
     * The card at the given position has been clicked.
     *
     * @param position of the card in the adapter
     */
    void onCardClicked(int position);
}
