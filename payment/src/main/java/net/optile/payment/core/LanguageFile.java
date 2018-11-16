/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.core;

import java.util.Properties;

import net.optile.payment.model.Interaction;

/**
 * Class holding the language entries for the payment page, ApplicableNetwork or AccountRegistration
 */
public final class LanguageFile {

    public final static String KEY_BUTTON_DATE = "button.update.label";
    public final static String KEY_AUTO_REGISTRATION = "autoRegistrationLabel";
    public final static String KEY_ALLOW_RECURRENCE = "allowRecurrenceLabel";

    private final Properties lang;

    /**
     * Construct an empty LanguageFile
     */
    public LanguageFile() {
        this.lang = new Properties();
    }

    public String translate(String key, String defValue) {
        return key != null ? lang.getProperty(key, defValue) : defValue;
    }

    public String translateError(String error) {
        return translate("error.".concat(error), null);
    }

    public String translateAccountLabel(String type) {
        return translate("account.".concat(type).concat(".label"), null);
    }

    public String translateInteraction(Interaction interaction) {
        StringBuilder sb = new StringBuilder("interaction.");
        sb.append(interaction.getCode()).append(".").append(interaction.getReason());
        return translate(sb.toString(), null);
    }

    public Properties getProperties() {
        return lang;
    }
}
