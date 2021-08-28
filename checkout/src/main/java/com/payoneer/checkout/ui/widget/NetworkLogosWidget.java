/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import com.payoneer.checkout.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.payoneer.checkout.R;
import com.payoneer.checkout.ui.model.PaymentNetwork;
import com.payoneer.checkout.util.NetworkLogoLoader;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ViewSwitcher;

/**
 * Widget for showing the network logos for grouped cards
 */
public final class NetworkLogosWidget extends FormWidget {

    private final Map<String, NetworkLogo> logos;
    private ViewSwitcher switcher;
    private ImageView selImage;
    private int margin;
    private LinearLayout layout;
    
    /**
     * Construct a new NetworkLogosWidget
     *
     * @param name the name of this widget
     */
    public NetworkLogosWidget(String name) {
        super(name);
        logos = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View inflate(ViewGroup parent) {
        inflateWidgetView(parent, R.layout.widget_networklogos);

        switcher = widgetView.findViewById(R.id.viewswitcher_logos);
        switcher.setVisibility(View.VISIBLE);
        selImage = widgetView.findViewById(R.id.image_selected);
        layout = widgetView.findViewById(R.id.layout_logos);

        Resources resources = parent.getContext().getResources();
        margin = (int) resources.getDimension(R.dimen.pmborder_small);
        return widgetView;
    }

    public void onBind(List<PaymentNetwork> networks) {
        logos.clear();
        layout.removeAllViews();

        for (PaymentNetwork network : networks) {
            addNetworkLogo(network, layout);
        }
    }

    public void setSelected(String networkCode) {
        if (networkCode != null) {
            showSelectedLogo(networkCode);
        } else {
            showAllLogos();
        }
    }

    private void addNetworkLogo(PaymentNetwork network, LinearLayout layout) {
        String code = network.getNetworkCode();
        ImageView imageView = inflateLogoImage(layout);
        logos.put(code, new NetworkLogo(code, network.getLink("logo"), imageView));
        layout.addView(imageView);
    }

    private ImageView inflateLogoImage(LinearLayout layout) {
        LayoutInflater inflater = LayoutInflater.from(layout.getContext());
        ImageView view = (ImageView) inflater.inflate(R.layout.view_logosmall, layout, false);
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        params.setMargins(0, 0, margin, 0);
        view.setLayoutParams(params);
        return view;
    }

    private void showSelectedLogo(String networkCode) {
        NetworkLogo logo = logos.get(networkCode);
        NetworkLogoLoader.loadNetworkLogo(selImage, networkCode, logo.url);
        switcher.setDisplayedChild(1);
    }

    private void showAllLogos() {
        for (Map.Entry<String, NetworkLogo> entry : logos.entrySet()) {
            NetworkLogo logo = entry.getValue();
            NetworkLogoLoader.loadNetworkLogo(logo.image, logo.networkCode, logo.url);
        }
        switcher.setDisplayedChild(0);
    }

    static class NetworkLogo {

        final String networkCode;
        final URL url;
        final ImageView image;

        NetworkLogo(String networkCode, URL url, ImageView image) {
            this.networkCode = networkCode;
            this.url = url;
            this.image = image;
        }
    }
}
