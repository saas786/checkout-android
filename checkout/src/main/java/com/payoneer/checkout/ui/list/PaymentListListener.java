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

/**
 * Implement this interface for listing to PaymentList events e.g onActionClicked.
 */
public interface PaymentListListener {

    /**
     * Notify that the user clicked the action button in the payment card.
     *
     * @param paymentCard in which the action was clicked
     * @param widgets map of widgets inside this PaymentCard
     */
    void onActionClicked(PaymentCard paymentCard, Map<String, FormWidget> widgets);

    /**
     * Notify that the user clicked the delete button in the payment card.
     *
     * @param paymentCard that should be deleted
     */
    void onDeleteClicked(PaymentCard paymentCard);

    /**
     * Notify that the user clicked the hint button in the payment card.
     *
     * @param code the code of the input field
     * @param type of the input field
     */
    void onHintClicked(String code, String type);
}
