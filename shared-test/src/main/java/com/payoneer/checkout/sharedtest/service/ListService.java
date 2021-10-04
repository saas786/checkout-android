/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.sharedtest.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.model.ListResult;
import com.payoneer.checkout.network.ListConnection;
import com.payoneer.checkout.util.PaymentUtils;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Class for creating a new ListUrl
 */
public final class ListService {

    private final String url;
    private final String auth;
    private final ListConnection conn;

    private ListService(Context context, String url, String auth) {
        this.url = url;
        this.auth = auth;
        this.conn = new ListConnection(context);
    }

    /**
     * Helper method to create list with the provided settings
     *
     * @param baseUrl url pointing to the Payment API Backend
     * @param authHeader containing the authentication header value
     * @param settings used to create the list
     * @return the self url of the newly created list
     */
    public static String createListWithSettings(String baseUrl, String authHeader, ListSettings settings) throws ListServiceException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ListService service = new ListService(context, baseUrl, authHeader);
        return service.createListUrl(baseUrl, authHeader, settings);
    }

    private String createListUrl(String baseUrl, String authHeader, ListSettings settings) throws ListServiceException {
        try {
            String listBody = createListRequestBody(settings);
            ListResult result = conn.createPaymentSession(baseUrl, authHeader, listBody);
            Map<String, URL> links = result.getLinks();
            URL selfUrl = links != null ? links.get("self") : null;

            if (selfUrl == null) {
                throw new ListServiceException("Error creating payment session, missing self url");
            }
            return selfUrl.toString();
        } catch (PaymentException | JSONException | IOException e) {
            throw new ListServiceException("Error creating payment session", e);
        }
    }

    private String createListRequestBody(ListSettings settings) throws JSONException, IOException {
        JSONObject json = loadJSONTemplate(settings.getListResId());
        String language = settings.getLanguage();
        if (language != null) {
            JSONObject style = json.getJSONObject("style");
            style.put("language", language);
        }
        BigDecimal amount = settings.getAmount();
        if (amount != null) {
            JSONObject payment = json.getJSONObject("payment");
            payment.put("amount", amount);
        }
        String appId = settings.getAppId();
        if (appId != null) {
            JSONObject callback = json.getJSONObject("callback");
            callback.put("appId", appId);
        }
        String operationType = settings.getOperationType();
        if (operationType != null) {
            json.put("operationType", operationType);
        }
        String checkoutConfigurationName = settings.getCheckoutConfigurationName();
        if (checkoutConfigurationName != null) {
            json.put("checkoutConfigurationName", checkoutConfigurationName);
        }
        String division = settings.getDivision();
        if (division != null) {
            json.put("division", division);
        }
        return json.toString();
    }

    private JSONObject loadJSONTemplate(int jsonResId) throws JSONException, IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String fileContent = PaymentUtils.readRawResource(context.getResources(), jsonResId);
        return new JSONObject(fileContent);
    }
}
