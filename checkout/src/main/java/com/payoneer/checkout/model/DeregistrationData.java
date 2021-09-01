/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.model;

import lombok.Getter;
import lombok.Setter;

/**
 * An information about deregistration options.
 */
@Getter
@Setter
public class DeregistrationData {
    /** Simple API, optional - deregister one-click registrations. */
    private Boolean deleteRegistration;
    /** Simple API, optional - deregister recurring registrations. */
    private Boolean deleteRecurrence;
}
