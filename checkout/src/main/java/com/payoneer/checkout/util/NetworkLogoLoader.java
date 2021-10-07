/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.util;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;
import com.payoneer.checkout.R;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for loading network logo images into an ImageView.
 * This loader will first check if a locally stored logo image is available, if not, the loader will
 * download the logo using the provided URL.
 */
public final class NetworkLogoLoader {

    private final static String NETWORKLOGO_FOLDER = "networklogos/";
    private final Map<String, String> localNetworkLogos;
    private final ImageHelper helper = ImageHelper.getInstance();

    private NetworkLogoLoader() {
        localNetworkLogos = new HashMap<>();
    }

    /**
     * Get the instance of this LogoLoader
     *
     * @return the instance of this LogoLoader
     */
    public static NetworkLogoLoader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Load the network logo given the networkCode and URL and store it into the ImageView.
     *
     * @param view           ImageView in which to place the Bitmap
     * @param networkCode    code of the payment network
     * @param networkLogoUrl pointing to the remote image
     */
    public static void loadNetworkLogo(ImageView view, String networkCode, URL networkLogoUrl) {
        getInstance().loadIntoView(view, networkCode, networkLogoUrl);
    }

    private void loadIntoView(ImageView view, String networkCode, URL networkLogoUrl) {
        if (localNetworkLogos.size() == 0) {
            loadLocalNetworkLogos(view.getContext());
        }
        if (localNetworkLogos.containsKey(networkCode)) {
            String url = localNetworkLogos.get(networkCode);
            helper.loadImageFromFile(view, url);
        } else {
            String url = networkLogoUrl.toString();
            helper.loadImageFromNetwork(view, createUrl(url));
        }
    }

    private URL createUrl(String url) {
        URL createdUrl = null;
        try {
            createdUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return createdUrl;
    }

    private void loadLocalNetworkLogos(Context context) {
        synchronized (localNetworkLogos) {
            if (localNetworkLogos.size() != 0) {
                return;
            }
            Resources res = context.getResources();
            String[] ts;
            String[] ar = res.getStringArray(R.array.networklogos);
            for (String icon : ar) {
                ts = icon.split(",");
                localNetworkLogos.put(ts[0], NETWORKLOGO_FOLDER + ts[1]);
            }
        }
    }

    private static class InstanceHolder {
        static final NetworkLogoLoader INSTANCE = new NetworkLogoLoader();
    }
}
