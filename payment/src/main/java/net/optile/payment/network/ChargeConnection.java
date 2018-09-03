/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.network;

import android.util.Log;
import android.net.Uri;
import android.text.TextUtils;

import java.io.IOException;
import java.net.URL;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import org.json.JSONObject;
import org.json.JSONException;

import com.google.gson.JsonParseException;

import com.btelligent.optile.pds.api.rest.model.payment.enterprise.extensible.Charge;

/**
 * Class implementing the communication with the Charge payment API
 * <p>
 * All requests in this class are blocking calls and should be
 * executed in a separate thread to avoid blocking the main application thread.
 * These methods are not thread safe and must not be called by different threads
 * at the same time.
 */
public final class ChargeConnection extends BaseConnection {

    /**
     * Create a new charge through the Payment API
     *
     * @param url  the url of the charge 
     * @param data the data containing the request body for the charge request
     * @return     the NetworkResponse containing either an error or the List
     */
    public NetworkResponse createCharge(URL url, String data) {

        String source = "ChargeConnection[createCharge]";
        
        if (url == null) {
            return NetworkResponse.newInvalidValueResponse(source + " - url cannot be null"); 
        }
        if (TextUtils.isEmpty(data)) {
            return NetworkResponse.newInvalidValueResponse(source + " - data cannot be null or empty"); 
        }
        
        HttpURLConnection conn = null;
        NetworkResponse resp = null;

        try {

            conn = createPostConnection(url);
            conn.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_VND_JSON);
            conn.setRequestProperty(HEADER_ACCEPT, VALUE_VND_JSON);

            writeToOutputStream(conn, data);

            conn.connect();
            int rc = conn.getResponseCode();

            switch (rc) {
            case HttpURLConnection.HTTP_OK:
                resp = handleCreateChargeOk(readFromInputStream(conn));
                break;
            default:
                resp = handleAPIErrorResponse(source, rc, conn);
            }
        } catch (JsonParseException e) {
            resp = NetworkResponse.newProtocolErrorResponse(source, e);            
        } catch (MalformedURLException e) {
            resp = NetworkResponse.newInternalErrorResponse(source, e);
        } catch (IOException e) {
            resp = NetworkResponse.newConnErrorResponse(source, e);
        } catch (SecurityException e) {
            resp = NetworkResponse.newSecurityErrorResponse(source, e);            
        } finally {
            close(conn);
        }
        return resp;
    }

    /**
     * Handle the create charge OK state
     *
     * @param  data the response data received from the API
     * @return      the network response containing the ListResult
     */
    private NetworkResponse handleCreateChargeOk(String data) throws JsonParseException {

        Charge charge = gson.fromJson(data, Charge.class);
        NetworkResponse resp = new NetworkResponse();
        resp.putCharge(charge);
        return resp;
    }
}
