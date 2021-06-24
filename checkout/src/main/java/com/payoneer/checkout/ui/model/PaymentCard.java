/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import static com.payoneer.checkout.ui.model.PaymentSession.LINK_LOGO;
import static com.payoneer.checkout.ui.model.PaymentSession.LINK_OPERATION;

import java.net.URL;
import java.util.List;
import java.util.Map;

import com.payoneer.checkout.model.InputElement;

/**
 * Base class for all payment cards like the AccountCard, PresetCard and NetworkCard
 */
public abstract class PaymentCard {

    private final boolean hideInputForm;
    private final boolean checkable;

    /**
     * Construct a PaymentCard
     *
     * @param hideInputForm should the input form be hidden from the user
     * @param checkable is the PaymentCard checkable
     */
    public PaymentCard(boolean hideInputForm, boolean checkable) {
        this.hideInputForm = hideInputForm;
        this.checkable = checkable;
    }

    /**
     * Should the input form be hidden from the user
     *
     * @return true when the input form should be hidden, false otherwise
     */
    public boolean hideInputForm() {
        return hideInputForm;
    }

    /**
     * Is this payment card checkable. When the card is checked, a highlighted border
     * is drawn around the payment card.
     *
     * @return true when checkable, false otherwise
     */
    public boolean isCheckable() {
        return checkable;
    }

    /**
     * Notify that text input has changed for one of the input fields in this PaymentCard.
     *
     * @param type the type of the TextInput field
     * @param text new text of the input field
     * @return true when this PaymentCard has changed its appearance because of the new input, false otherwise
     */
    public boolean onTextInputChanged(String type, String text) {
        return false;
    }

    /**
     * Get the operation link from the active network in this card,
     * this link can be used to make i.e. a charge request
     *
     * @return the operation link or null if it does not exist
     */
    public URL getOperationLink() {
        return getLink(LINK_OPERATION);
    }

    /**
     * Get the logo link from the active network in this card,
     * this link points to the logo image
     *
     * @return the logo link or null if it does not exist
     */
    public URL getLogoLink() {
        return getLink(LINK_LOGO);
    }

    /**
     * Get the link from this PaymentCard with the given name
     *
     * @param name of the link that should be returned
     * @return the link or null if not available
     */
    public URL getLink(String name) {
        return getLinks().get(name);
    }

    /**
     * Get the InputElement given the name
     *
     * @param name of the InputElement to be returned
     * @return the InputElement with the given name or null if not found
     */
    public InputElement getInputElement(String name) {
        for (InputElement element : getInputElements()) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }

    /**
     * Check if this card contains a link with the provided name. If the card contains multiple networks then
     * all networks must be checked if at least one of them contains the link.
     *
     * @param name of the link
     * @param url that should match
     * @return true when it contains the link, false otherwise
     */
    public abstract boolean containsLink(String name, URL url);

    /**
     * Get the action button label
     *
     * @return the action button label or null if not set
     */
    public abstract String getButton();

    /**
     * Put all language links stored in the payment card into the links map.
     * The key is the name of the network the URL belongs to.
     *
     * @param links map into which the links should be stored
     */
    public abstract void putLanguageLinks(Map<String, URL> links);

    /**
     * Get the operation type, e.g. OperationType.CHARGE or OperationType.PRESET
     *
     * @return the operationType of this payment card
     */
    public abstract String getOperationType();

    /**
     * Get the paymentMethod of this PaymentCard
     *
     * @return paymentMethod of this PaymentCard
     */
    public abstract String getPaymentMethod();

    /**
     * Get the network code of this PaymentCard.
     *
     * @return network code of this PaymentCard
     */
    public abstract String getNetworkCode();

    /**
     * Is this card preselected
     *
     * @return true when preselected, false otherwise
     */
    public abstract boolean isPreselected();

    /**
     * Get the label of this PaymentCard
     *
     * @return label of this PaymentCard
     */
    public abstract String getLabel();

    /**
     * Get the list of links. This method must not return null.
     *
     * @return the list of links
     */
    public abstract Map<String, URL> getLinks();

    /**
     * Get the list of input elements supported by this  payment card.
     * This method must not return null.
     *
     * @return list of InputElements
     */
    public abstract List<InputElement> getInputElements();
}
