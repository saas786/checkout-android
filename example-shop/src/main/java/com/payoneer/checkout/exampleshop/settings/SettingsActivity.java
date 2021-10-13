/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.exampleshop.settings;

import com.payoneer.checkout.exampleshop.R;
import com.payoneer.checkout.exampleshop.checkout.CheckoutActivity;
import com.payoneer.checkout.exampleshop.shared.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * This is the main Activity of this shop app in which users can paste a listUrl and start the shop.
 */
public final class SettingsActivity extends BaseActivity {

    private EditText listInput;

    /**
     * Create an Intent to launch this settings activity
     *
     * @param context base for creating the start intent
     * @return the newly created intent
     */
    public static Intent createStartIntent(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.listInput = findViewById(R.id.input_listurl);

        if (listUrl != null) {
            listInput.setText(listUrl);
        }
        Button button = findViewById(R.id.button_settings);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClicked();
            }
        });
    }

    private void onButtonClicked() {
        closeKeyboard();
        String listUrl = listInput.getText().toString().trim();

        if (TextUtils.isEmpty(listUrl) || !Patterns.WEB_URL.matcher(listUrl).matches()) {
            showErrorDialog(R.string.dialog_error_listurl_invalid);
            return;
        }
        Intent intent = CheckoutActivity.createStartIntent(this, listUrl);
        startActivity(intent);
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            IBinder binder = listInput.getWindowToken();
            imm.hideSoftInputFromWindow(binder, 0);
        }
    }
}
