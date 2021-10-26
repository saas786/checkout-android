/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

/**
 * Class representing a header in the PaymentList
 */
final class HeaderItem extends ListItem {

    private final String title;
    private final String message;

    HeaderItem(int viewType, String title, String message) {
        super(viewType);
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
