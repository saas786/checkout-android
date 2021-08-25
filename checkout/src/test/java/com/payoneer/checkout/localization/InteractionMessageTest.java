/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.localization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.checkout.model.Interaction;
import com.payoneer.checkout.model.InteractionCode;
import com.payoneer.checkout.model.InteractionReason;
import com.payoneer.checkout.model.NetworkOperationType;

@RunWith(RobolectricTestRunner.class)
public class InteractionMessageTest {

    @Test(expected = IllegalArgumentException.class)
    public void fromInteraction_invalidInteraction_IllegalArgumentException() {
        InteractionMessage.fromInteraction(null);
    }

    @Test
    public void fromInteraction_success() {
        Interaction interaction = new Interaction(InteractionCode.PROCEED, InteractionReason.OK);
        InteractionMessage message = InteractionMessage.fromInteraction(interaction);
        assertEquals(interaction, message.getInteraction());
        assertFalse(message.hasFlow());
        assertNull(message.getFlow());
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromDeleteFlow_invalidInteraction_IllegalArgumentException() {
        InteractionMessage.fromDeleteFlow(null);
    }

    @Test
    public void fromDeleteFlow_success() {
        Interaction interaction = new Interaction(InteractionCode.PROCEED, InteractionReason.OK);
        InteractionMessage message = InteractionMessage.fromDeleteFlow(interaction);
        assertEquals(interaction, message.getInteraction());
        assertTrue(message.hasFlow());
        assertEquals("DELETE", message.getFlow());
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromOperationFlow_invalidInteraction_IllegalArgumentException() {
        String operationType = NetworkOperationType.CHARGE;
        InteractionMessage.fromOperationFlow(null, operationType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromOperationFlow_invalidOperationType_IllegalArgumentException() {
        Interaction interaction = new Interaction(InteractionCode.PROCEED, InteractionReason.OK);
        InteractionMessage.fromOperationFlow(interaction, "");
    }

    @Test
    public void fromOperationFlow_success() {
        String operationType = NetworkOperationType.CHARGE;
        Interaction interaction = new Interaction(InteractionCode.PROCEED, InteractionReason.OK);
        InteractionMessage message = InteractionMessage.fromOperationFlow(interaction, operationType);
        assertEquals(interaction, message.getInteraction());
        assertTrue(message.hasFlow());
        assertEquals(operationType, message.getFlow());
    }
}