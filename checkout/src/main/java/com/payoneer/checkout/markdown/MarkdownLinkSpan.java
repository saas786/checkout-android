/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.markdown;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.redirect.ChromeCustomTabs;
import com.payoneer.checkout.redirect.RedirectUriBuilder;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

/**
 * Implementation of a ClickableSpan containing a clickable link. When the user clicks the link,
 * a ChromeCustomTab will be opened using the URL of the link.
 */
public final class MarkdownLinkSpan extends ClickableSpan {

    private final String url;

    /**
     * Construct a new MarkdownLinkSpan with the provided url
     *
     * @param url to be opened when the span is clicked
     */
    public MarkdownLinkSpan(String url) {
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        openUrl(widget.getContext(), url);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
    }

    private void openUrl(Context context, String url) {
        try {
            ChromeCustomTabs.open(context, RedirectUriBuilder.fromString(url));
        } catch (PaymentException e) {
            Log.w("checkout", e);
        }
    }
}
