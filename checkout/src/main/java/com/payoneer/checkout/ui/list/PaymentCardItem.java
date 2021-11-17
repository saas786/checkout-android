/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import com.payoneer.checkout.ui.model.PaymentCard;

/**
 * Class representing a PaymentCard item in the RecyclerView
 */
final class PaymentCardItem extends ListItem {

    final PaymentCard paymentCard;

    PaymentCardItem(int viewType, PaymentCard paymentCard) {
        super(viewType);
        this.paymentCard = paymentCard;
    }

    PaymentCard getPaymentCard() {
        return paymentCard;
    }
}
