/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.model;

import java.net.URL;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * An information about preset account.
 */
@Getter
@Setter
public class PresetAccount {
    /** Links (Simple API, always present) */
    private Map<String, URL> links;
    /** Network code (Simple API, always present) */
    private String code;
    /** Simple API, always present */
    @NetworkOperationType.Definition
    private String operationType;
    /** Masked account (Simple API, optional) */
    private AccountMask maskedAccount;
    /** PCI API, optional */
    private Redirect redirect;
    /** Simple API, always present */
    @PaymentMethod.Definition
    private String method;
    /** The following three booleans determine the visibility of the preset warning text */
    private boolean registered;
    private boolean autoRegistration;
    private boolean allowRecurrence;
}
