/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.localization;

import com.payoneer.checkout.model.Interaction;

import android.text.TextUtils;

/**
 * Class storing information for localizing interactions.
 */
public final class InteractionMessage {

    private final static String DELETE = "DELETE";
    private final Interaction interaction;
    private final String flow;

    /**
     * Construct a new InteractionMessage with the optional flow description.
     *
     * @param interaction containing the code and reason of the interaction
     * @param flow optional value describing the flow, e.g. delete or operation
     */
    private InteractionMessage(Interaction interaction, String flow) {
        this.interaction = interaction;
        this.flow = flow;
    }

    /**
     * Create a default InteractionMessage for the provided interaction without a specific flow.
     *
     * @param interaction containing a recommendation how to proceed
     * @return newly created InteractionMessage
     */
    public static InteractionMessage fromInteraction(Interaction interaction) {
        if (interaction == null) {
            throw new IllegalArgumentException("interaction cannot be null");
        }
        return new InteractionMessage(interaction, null);
    }

    /**
     * Create an InteractionMessage for the interaction that was generated during the deletion of
     * a registered account.
     *
     * @param interaction containing a recommendation how to proceed
     * @return newly created InteractionMessage
     */
    public static InteractionMessage fromDeleteFlow(Interaction interaction) {
        if (interaction == null) {
            throw new IllegalArgumentException("interaction cannot be null");
        }
        return new InteractionMessage(interaction, DELETE);
    }

    /**
     * Create an InteractionMessage for the interaction that was generated during the flow described by
     * the operationType.
     *
     * @param interaction containing a recommendation how to proceed
     * @param operationType describing the flow of the interaction, e.g. CHARGE, PAYOUT, UPDATE
     * @return newly created InteractionMessage
     */
    public static InteractionMessage fromOperationFlow(Interaction interaction, String operationType) {
        if (interaction == null) {
            throw new IllegalArgumentException("interaction cannot be null");
        }
        if (TextUtils.isEmpty(operationType)) {
            throw new IllegalArgumentException("operationType cannot be null or empty");
        }
        return new InteractionMessage(interaction, operationType);
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public String getFlow() {
        return flow;
    }

    public boolean hasFlow() {
        return !TextUtils.isEmpty(flow);
    }
}
