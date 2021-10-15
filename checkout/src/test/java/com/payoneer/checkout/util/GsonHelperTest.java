/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.google.gson.reflect.TypeToken;
import com.payoneer.checkout.core.PaymentInputType;
import com.payoneer.checkout.model.InputElement;
import com.payoneer.checkout.model.InputElementType;

/**
 * Class for testing the GsonHelper methods
 */
@RunWith(RobolectricTestRunner.class)
public class GsonHelperTest {

    private final static String jsonArray =
        "[{\"name\":\"expiryMonth\",\"type\":\"select\"}, {\"name\":\"expiryYear\",\"type\":\"select\"}]";
    private final static String jsonItem = "{\"name\":\"expiryMonth\",\"type\":\"select\"}";

    @Test
    public void getInstance() {
        assertNotNull(GsonHelper.getInstance());
    }

    @Test
    public void toJson() {
        InputElement element = new InputElement();
        element.setName(PaymentInputType.EXPIRY_MONTH);
        element.setType(InputElementType.SELECT);
        String val = GsonHelper.getInstance().toJson(element);
        assertEquals(val, jsonItem);
    }

    @Test
    public void fromJson_class() {
        InputElement element = GsonHelper.getInstance().fromJson(jsonItem, InputElement.class);
        assertNotNull(element);
        assertEquals(element.getName(), PaymentInputType.EXPIRY_MONTH);
        assertEquals(element.getType(), InputElementType.SELECT);
    }

    @Test
    public void fromJson_type() {
        Type listType = new TypeToken<ArrayList<InputElement>>() { }.getType();
        List<InputElement> items = GsonHelper.getInstance().fromJson(jsonArray, listType);
        assertNotNull(items);
        assertEquals(items.size(), 2);
    }
}
