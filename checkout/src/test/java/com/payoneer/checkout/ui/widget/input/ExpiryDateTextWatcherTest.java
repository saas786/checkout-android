/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget.input;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class ExpiryDateTextWatcherTest {

    @Test(expected = NullPointerException.class)
    public void createExpiryDateTextWatcher_invalid_EditText() {
        new ExpiryDateTextWatcher(null);
    }

    @Test
    public void testDateOneDigitMonth() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        ExpiryDateTextWatcher watcher = new ExpiryDateTextWatcher(editText);
        validateTextInput(watcher, "", "156", "01 / 56");
    }

    @Test
    public void testDateTwoDigitsMonth() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        ExpiryDateTextWatcher watcher = new ExpiryDateTextWatcher(editText);
        validateTextInput(watcher, "", "1234", "12 / 34");
    }

    @Test
    public void testDateZeroFirst() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        ExpiryDateTextWatcher watcher = new ExpiryDateTextWatcher(editText);
        validateTextInput(watcher, "", "0345", "03 / 45");
    }

    @Test
    public void testInvalidDoubleZero() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        ExpiryDateTextWatcher watcher = new ExpiryDateTextWatcher(editText);
        validateTextInput(watcher, "", "00123", "0");
    }

    @Test
    public void testLongInput() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        ExpiryDateTextWatcher watcher = new ExpiryDateTextWatcher(editText);
        validateTextInput(watcher, "", "9123456", "09 / 12");
    }

    @Test
    public void testFirstCharZero() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        ExpiryDateTextWatcher watcher = new ExpiryDateTextWatcher(editText);
        validateTextInput(watcher, "", "0", "0");
    }

    @Test
    public void testFirstCharOne() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        ExpiryDateTextWatcher watcher = new ExpiryDateTextWatcher(editText);
        validateTextInput(watcher, "", "1", "1");
    }

    @Test
    public void testFirstCharTwo() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        ExpiryDateTextWatcher watcher = new ExpiryDateTextWatcher(editText);
        validateTextInput(watcher, "", "2", "02 / ");
    }

    @Test
    public void testMonthTen() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        ExpiryDateTextWatcher watcher = new ExpiryDateTextWatcher(editText);
        validateTextInput(watcher, "", "10", "10 / ");
    }

    private static void validateTextInput(TextWatcher watcher, String current, String input, String result) {
        String val = simulateTextInput(watcher, current, input);
        assertEquals(result, val);
    }

    private static String simulateTextInput(TextWatcher tw, String original, String input) {
        int originalLength = original.length();
        tw.beforeTextChanged(original, originalLength, 0, input.length());
        String newText = original + input;

        Editable editable = new SpannableStringBuilder(newText);
        tw.afterTextChanged(editable);
        return editable.toString();
    }
}
