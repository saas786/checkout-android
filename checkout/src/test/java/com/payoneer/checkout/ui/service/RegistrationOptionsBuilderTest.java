/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.checkout.localization.LocalizationKey;
import com.payoneer.checkout.model.CheckboxMode;
import com.payoneer.checkout.model.NetworkOperationType;
import com.payoneer.checkout.model.RegistrationType;
import com.payoneer.checkout.ui.model.RegistrationOptions;

@RunWith(RobolectricTestRunner.class)
public class RegistrationOptionsBuilderTest {

    @Test(expected = IllegalStateException.class)
    public void buildRegistrationOption_missingOperationType() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder();
        builder.setAutoRegistration(RegistrationType.OPTIONAL);
        builder.buildAutoRegistrationOption();
    }

    @Test(expected = IllegalStateException.class)
    public void buildRegistrationOption_missingRegistration() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder();
        builder.setOperationType(NetworkOperationType.UPDATE);
        builder.buildAutoRegistrationOption();
    }

    @Test(expected = IllegalStateException.class)
    public void buildRecurrenceOption_missingOperationType() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder();
        builder.setAllowRecurrence(RegistrationType.OPTIONAL);
        builder.buildAllowRecurrenceOption();
    }

    @Test(expected = IllegalStateException.class)
    public void buildRecurrenceOption_missingRecurrence() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder();
        builder.setOperationType(NetworkOperationType.UPDATE);
        builder.buildAllowRecurrenceOption();
    }

    @Test
    public void buildRegistrationOptions_CHARGE_OPTIONAL() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setAutoRegistration(RegistrationType.OPTIONAL)
            .setAllowRecurrence(RegistrationType.OPTIONAL);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.OPTIONAL);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_OPTIONAL);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.OPTIONAL);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_OPTIONAL);
    }

    @Test
    public void buildRegistrationOptions_CHARGE_OPTIONAL_PRESELECTED() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setAutoRegistration(RegistrationType.OPTIONAL_PRESELECTED)
            .setAllowRecurrence(RegistrationType.OPTIONAL_PRESELECTED);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.OPTIONAL_PRESELECTED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_OPTIONAL);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.OPTIONAL_PRESELECTED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_OPTIONAL);
    }

    @Test
    public void buildRegistrationOptions_CHARGE_FORCED() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setAutoRegistration(RegistrationType.FORCED)
            .setAllowRecurrence(RegistrationType.FORCED);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRegistrationOptions_CHARGE_FORCED_DISPLAYED() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setAutoRegistration(RegistrationType.FORCED_DISPLAYED)
            .setAllowRecurrence(RegistrationType.FORCED_DISPLAYED);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED_DISPLAYED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED_DISPLAYED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRegistrationOptions_CHARGE_NONE() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setAutoRegistration(RegistrationType.NONE)
            .setAllowRecurrence(RegistrationType.NONE);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.NONE);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.NONE);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRegistrationOptions_UPDATE_OPTIONAL() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setAutoRegistration(RegistrationType.OPTIONAL)
            .setAllowRecurrence(RegistrationType.OPTIONAL);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRegistrationOptions_UPDATE_OPTIONAL_PRESELECTED() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setAutoRegistration(RegistrationType.OPTIONAL_PRESELECTED)
            .setAllowRecurrence(RegistrationType.OPTIONAL_PRESELECTED);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRegistrationOptions_UPDATE_UPDATE_FORCED() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setAutoRegistration(RegistrationType.FORCED)
            .setAllowRecurrence(RegistrationType.FORCED);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRegistrationOptions_UPDATE_FORCED_DISPLAYED() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setAutoRegistration(RegistrationType.FORCED_DISPLAYED)
            .setAllowRecurrence(RegistrationType.FORCED_DISPLAYED);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRegistrationOptions_UPDATE_NONE() {
        RegistrationOptionsBuilder builder = new RegistrationOptionsBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setAutoRegistration(RegistrationType.NONE)
            .setAllowRecurrence(RegistrationType.NONE);

        RegistrationOptions settings = builder.buildAutoRegistrationOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.NONE);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildAllowRecurrenceOption();
        assertEquals(settings.getCheckboxMode(), CheckboxMode.NONE);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }
}
