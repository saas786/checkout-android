/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.markdown;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.text.SpannableStringBuilder;

@RunWith(RobolectricTestRunner.class)
public class MarkdownSpannableStringBuilderTest {

    @Test
    public void createFromText_whenInputIsEmpty_shouldReturnEmpty() {
        final String input = "";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        assertEquals(result.toString(), input);
    }

    @Test
    public void testParse_whenInputContains0Links_shouldReturnAttributedStringWithInput() {
        final String input = "Lorem ipsum dolor sit er elit lamet";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        assertEquals(input, result.toString());
    }

    @Test
    public void testParse_whenInputContainsLinks_shouldReturnStringContainingLinkText() {
        final String input = "By clicking the button, you agree to the [Terms of Service](https://www.apple.com/) and [Privacy Policy](https://www.google.com/).";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        assertEquals("By clicking the button, you agree to the Terms of Service and Privacy Policy.", result.toString());
    }

    @Test
    public void testParse_whenInputContainsLinkWithOptionalTitle_shouldReturnStringContainingLinkText() {
        final String input = "By clicking the button, you agree to the [Terms of Service](https://www.apple.com/ \"optional title\").";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        assertEquals("By clicking the button, you agree to the Terms of Service.", result.toString());
    }

    @Test
    public void testParseLinks_whenInputContains1Link_shouldReturnAttributedStringWith1Link() {
        final String input = "[Terms of Service](https://www.apple.com/).";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        MarkdownLinkSpan[] spans = result.getSpans(0, result.length(), MarkdownLinkSpan.class);
        assertEquals(1, spans.length);
        assertEquals("https://www.apple.com/", spans[0].getUrl());
    }

    @Test
    public void testParseLinks_whenInputContainsTextAndLink_shouldReturnAttributedStringWith1Link() {
        final String input = "By clicking the button, you agree to the [Terms of Service](https://www.apple.com/).";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        MarkdownLinkSpan[] spans = result.getSpans(0, result.length(), MarkdownLinkSpan.class);
        assertEquals(1, spans.length);
        assertEquals("https://www.apple.com/", spans[0].getUrl());
    }

    @Test
    public void testParseLinks_whenInputContains2Links_shouldReturnAttributedStringWith2Links() {
        final String input = "By clicking the button, you agree to the [Terms of Service](https://www.apple.com/) and [Privacy Policy](https://www.google.com/).";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        MarkdownLinkSpan[] spans = result.getSpans(0, result.length(), MarkdownLinkSpan.class);
        assertEquals(2, spans.length);
        assertEquals("https://www.apple.com/", spans[0].getUrl());
        assertEquals("https://www.google.com/", spans[1].getUrl());
    }

    @Test
    public void testParseLinks_whenInputContains2LinksTogether_shouldReturnAttributedStringWith2Links() {
        final String input = "By clicking the button, you agree to the [Terms of Service](https://www.apple.com/)[Privacy Policy](https://www.google.com/).";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        MarkdownLinkSpan[] spans = result.getSpans(0, result.length(), MarkdownLinkSpan.class);
        assertEquals(2, spans.length);
        assertEquals("https://www.apple.com/", spans[0].getUrl());
        assertEquals("https://www.google.com/", spans[1].getUrl());
    }

    @Test
    public void testParseLinks_whenInputContains2LinksWithoutWhitespace_shouldReturnAttributedStringWith2Links() {
        final String input = "By clicking the button, you agree to the [Terms of Service](https://www.apple.com/)and[Privacy Policy](https://www.google.com/).";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        MarkdownLinkSpan[] spans = result.getSpans(0, result.length(), MarkdownLinkSpan.class);
        assertEquals(2, spans.length);
        assertEquals("https://www.apple.com/", spans[0].getUrl());
        assertEquals("https://www.google.com/", spans[1].getUrl());
    }

    @Test
    public void testParseLinks_whenInputContainsLinkWithOptionalTitle_shouldOnlyParseURL() {
        final String input = "By clicking the button, you agree to the [Terms of Service](https://www.apple.com/ \"optional title\").";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        MarkdownLinkSpan[] spans = result.getSpans(0, result.length(), MarkdownLinkSpan.class);
        assertEquals(1, spans.length);
        assertEquals("https://www.apple.com/", spans[0].getUrl());
    }

    @Test
    public void testParseLinks_whenInputContains2LinksWithOptionalTitle_shouldReturn2Links() {
        final String input = "By clicking the button, you agree to the [Terms of Service](https://www.apple.com/ \"optional title\") and [Privacy Policy](https://www.google.com/ \"optionaltitle\").";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        MarkdownLinkSpan[] spans = result.getSpans(0, result.length(), MarkdownLinkSpan.class);
        assertEquals(2, spans.length);
        assertEquals("https://www.apple.com/", spans[0].getUrl());
        assertEquals("https://www.google.com/", spans[1].getUrl());
    }

    @Test
    public void testParseLinks_whenInvalidURL_shouldReturn0Links() {
        final String input = "By clicking the button, you agree to the [Terms of Service]().";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        MarkdownLinkSpan[] spans = result.getSpans(0, result.length(), MarkdownLinkSpan.class);
        assertEquals(0, spans.length);
    }

    @Test
    public void testParseLinks_whenInputContains2LinksWithNoSpaces_shouldReturn2LinksWithLinkTexts() {
        final String input = "Byclickingthebutton,youagreetothe[TermsofService](https://www.apple.com/ \"optionaltitle\")and[PrivacyPolicy](https://www.google.com/ \"optionaltitle\").";
        SpannableStringBuilder result = MarkdownSpannableStringBuilder.createFromText(input);
        MarkdownLinkSpan[] spans = result.getSpans(0, result.length(), MarkdownLinkSpan.class);
        assertEquals(2, spans.length);
        assertEquals("https://www.apple.com/", spans[0].getUrl());
        assertEquals("https://www.google.com/", spans[1].getUrl());
        assertEquals("Byclickingthebutton,youagreetotheTermsofServiceandPrivacyPolicy.", result.toString());
    }
}
