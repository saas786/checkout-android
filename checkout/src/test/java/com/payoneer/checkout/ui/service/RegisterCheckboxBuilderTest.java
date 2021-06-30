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
import com.payoneer.checkout.ui.model.CheckboxSettings;

@RunWith(RobolectricTestRunner.class)
public class RegisterCheckboxBuilderTest {

    @Test(expected = IllegalStateException.class)
    public void buildRegistrationCheckboxSettings_missingOperationType() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder();
        builder.setRegistration(RegistrationType.OPTIONAL);
        builder.buildRegistrationCheckboxSettings();
    }

    @Test(expected = IllegalStateException.class)
    public void buildRegistrationCheckboxSettings_missingRegistration() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder();
        builder.setOperationType(NetworkOperationType.UPDATE);
        builder.buildRegistrationCheckboxSettings();
    }

    @Test(expected = IllegalStateException.class)
    public void buildRecurrenceCheckboxSettings_missingOperationType() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder();
        builder.setRecurrence(RegistrationType.OPTIONAL);
        builder.buildRecurrenceCheckboxSettings();
    }

    @Test(expected = IllegalStateException.class)
    public void buildRecurrenceCheckboxSettings_missingRecurrence() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder();
        builder.setOperationType(NetworkOperationType.UPDATE);
        builder.buildRecurrenceCheckboxSettings();
    }
    
    @Test
    public void buildCheckboxSettings_CHARGE_OPTIONAL() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setRegistration(RegistrationType.OPTIONAL)
            .setRecurrence(RegistrationType.OPTIONAL);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.OPTIONAL);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_OPTIONAL);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.OPTIONAL);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_OPTIONAL);
    }

    @Test
    public void buildCheckboxSettings_CHARGE_OPTIONAL_PRESELECTED() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setRegistration(RegistrationType.OPTIONAL_PRESELECTED)
            .setRecurrence(RegistrationType.OPTIONAL_PRESELECTED);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.OPTIONAL_PRESELECTED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_OPTIONAL);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.OPTIONAL_PRESELECTED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_OPTIONAL);
    }

    @Test
    public void buildRecurrenceCheckboxSettings_CHARGE_FORCED() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setRegistration(RegistrationType.FORCED)
            .setRecurrence(RegistrationType.FORCED);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRecurrenceCheckboxSettings_CHARGE_FORCED_DISPLAYED() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setRegistration(RegistrationType.FORCED_DISPLAYED)
            .setRecurrence(RegistrationType.FORCED_DISPLAYED);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED_DISPLAYED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED_DISPLAYED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRecurrenceCheckboxSettings_CHARGE_NONE() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.CHARGE)
            .setRegistration(RegistrationType.NONE)
            .setRecurrence(RegistrationType.NONE);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.NONE);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.NONE);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildCheckboxSettings_UPDATE_OPTIONAL() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setRegistration(RegistrationType.OPTIONAL)
            .setRecurrence(RegistrationType.OPTIONAL);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }
    
    @Test
    public void buildCheckboxSettings_UPDATE_OPTIONAL_PRESELECTED() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setRegistration(RegistrationType.OPTIONAL_PRESELECTED)
            .setRecurrence(RegistrationType.OPTIONAL_PRESELECTED);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRecurrenceCheckboxSettings_UPDATE_FORCED() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setRegistration(RegistrationType.FORCED)
            .setRecurrence(RegistrationType.FORCED);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRecurrenceCheckboxSettings_UPDATE_FORCED_DISPLAYED() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setRegistration(RegistrationType.FORCED_DISPLAYED)
            .setRecurrence(RegistrationType.FORCED_DISPLAYED);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.FORCED);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }

    @Test
    public void buildRecurrenceCheckboxSettings_UPDATE_NONE() {
        RegisterCheckboxBuilder builder = new RegisterCheckboxBuilder()
            .setOperationType(NetworkOperationType.UPDATE)
            .setRegistration(RegistrationType.NONE)
            .setRecurrence(RegistrationType.NONE);
        
        CheckboxSettings settings = builder.buildRegistrationCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.NONE);
        assertEquals(settings.getLabelKey(), LocalizationKey.AUTO_REGISTRATION_FORCED);

        settings = builder.buildRecurrenceCheckboxSettings();
        assertEquals(settings.getMode(), CheckboxMode.NONE);
        assertEquals(settings.getLabelKey(), LocalizationKey.ALLOW_RECURRENCE_FORCED);
    }
}
