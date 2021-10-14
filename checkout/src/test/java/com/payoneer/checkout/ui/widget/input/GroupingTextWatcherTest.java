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
public class GroupingTextWatcherTest {

    @Test(expected = NullPointerException.class)
    public void createGroupingTextWatcher_invalid_EditText() {
        new GroupingTextWatcher(4, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createGroupingTextWatcher_invalid_Grouping() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        new GroupingTextWatcher(0, editText);
    }

    @Test
    public void pasteNumberTest() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        GroupingTextWatcher watcher = new GroupingTextWatcher(4, editText);
        validateTextInput(watcher, "", "1234567890", "1234 5678 90");
    }

    @Test
    public void groupTest() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);

        GroupingTextWatcher watcher = new GroupingTextWatcher(2, editText);
        validateTextInput(watcher, "", "1234567890", "12 34 56 78 90");

        watcher = new GroupingTextWatcher(16, editText);
        validateTextInput(watcher, "", "1234567890", "1234567890");
    }

    @Test
    public void typeCharacterTest() {
        Context context = ApplicationProvider.getApplicationContext();
        EditText editText = new EditText(context);
        GroupingTextWatcher watcher = new GroupingTextWatcher(4, editText);

        String result = "";
        result = validateTextInput(watcher, result, "1", "1");
        result = validateTextInput(watcher, result, "2", "12");
        result = validateTextInput(watcher, result, "3", "123");
        result = validateTextInput(watcher, result, "4", "1234");
        result = validateTextInput(watcher, result, "5", "1234 5");
        result = validateTextInput(watcher, result, "6", "1234 56");
        result = validateTextInput(watcher, result, "7", "1234 567");
        result = validateTextInput(watcher, result, "8", "1234 5678");
        validateTextInput(watcher, result, "9", "1234 5678 9");
    }

    private static String validateTextInput(TextWatcher watcher, String current, String input, String result) {
        String val = simulateTextInput(watcher, current, input);
        assertEquals(result, val);
        return val;
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
