/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a custom extra element that the merchant can define to be visualised on a payment page.
 * The element could be a label or a checkbox.
 */
@Getter
@Setter
@ToString
public class ExtraElement {

    /** The name of the extra element. Required */
    private String name;

    /** The label text that should be visualised for this element. Required */
    private String label;

    /** Determines if this extra element is a checkbox, and its additional properties. Optional */
    private Checkbox checkbox;
}