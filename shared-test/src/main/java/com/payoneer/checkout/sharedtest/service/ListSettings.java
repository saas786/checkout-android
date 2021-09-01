/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.sharedtest.service;

import java.math.BigDecimal;

/**
 * Class holding the settings to create a list request
 */
public final class ListSettings {

    private final int listResId;
    private String language;
    private BigDecimal amount;
    private String appId;
    private String operationType;

    public ListSettings(int listResId) {
        this.listResId = listResId;
    }

    public int getListResId() {
        return listResId;
    }

    public ListSettings setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ListSettings setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public ListSettings setOperationType(String operationType) {
        this.operationType = operationType;
        return this;
    }

    public String getOperationType() {
        return operationType;
    }

    public ListSettings setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getAppId() {
        return appId;
    }
}
