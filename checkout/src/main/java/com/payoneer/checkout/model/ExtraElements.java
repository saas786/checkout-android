/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a collection of custom extra elements that the merchant can define to be visualised on payment pages.
 */
@Getter
@Setter
@ToString
public class ExtraElements {

    /**
     * Elements that should be displayed at the top of the payment page.
     * The display order of the elements is defined by the order in which they appear in the collection.
     */
    private List<ExtraElement> top;

    /**
     * Elements that should be displayed at the bottom of the payment page.
     * The display order of the elements is defined by the order in which they appear in the collection.
     */
    private List<ExtraElement> bottom;
}