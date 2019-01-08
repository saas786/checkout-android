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

package net.optile.payment.ui.model;

import net.optile.payment.core.LanguageFile;
import net.optile.payment.model.InputElement;

import java.net.URL;
import java.util.List;

/**
 * Interface for payment cards like AccountCard and NetworkCard
 */
public interface PaymentCard {

    /**
     * Get the operation link, this link can be used to make i.e. a charge request
     *
     * @return the operation link or null if it does not exist
     */
    URL getOperationLink();

    /**
     * Get the paymentMethod of this PaymentCard
     *
     * @return paymentMethod
     */
    String getPaymentMethod();

    /**
     * Get the code of this PaymentCard, this may return null if a PaymentCard contains multiple PaymentMethods
     * and none has been selected.
     *
     * @return code
     */
    String getCode();

    /**
     * Get the language file of this PaymentCard
     *
     * @return language file
     */
    LanguageFile getLang();

    /**
     * Is this card preselected by the Payment API
     *
     * @return true when preselected, false otherwise
     */
    boolean isPreselected();

    /**
     * Get the action button label
     *
     * @return the action button label or null if not found
     */
    String getButton();

    /**
     * Get the list of input elements supported by this  payment card
     *
     * @return list of InputElements
     */
    List<InputElement> getInputElements();

    /** 
     * Get the InputElement given the name
     * 
     * @param name of the InputElement to be returned
     * @return the InputElement with the given name or null if not found 
     */
    InputElement getInputElement(String name);

    /** 
     * Notify the PaymentCard that the text input has changed for one of the input fields in this PaymentCard. 
     * 
     * @param type the type of the TextInput field
     * @param text to smart select the payment option inside the PaymentCard.
     * @return true when this PaymentCard has changed its appearance given the new TextInput, false otherwise
     */
    boolean onTextInputChanged(String type, String text);
}
