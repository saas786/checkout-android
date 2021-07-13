/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;


import static com.payoneer.checkout.core.PaymentInputType.ALLOW_RECURRENCE;
import static com.payoneer.checkout.core.PaymentInputType.AUTO_REGISTRATION;

import com.payoneer.checkout.R;
import com.payoneer.checkout.ui.model.NetworkCard;
import com.payoneer.checkout.ui.model.PaymentNetwork;
import com.payoneer.checkout.ui.model.RegistrationOption;
import com.payoneer.checkout.ui.model.SmartSwitch;
import com.payoneer.checkout.ui.widget.CheckboxWidget;
import com.payoneer.checkout.util.PaymentUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * The NetworkCardViewHolder
 */
final class NetworkCardViewHolder extends PaymentCardViewHolder {

    private final TextView titleView;
    private NetworkLogosView networkLogosView;


    public NetworkCardViewHolder(ListAdapter adapter, View parent, NetworkCard networkCard) {
        super(adapter, parent, networkCard);
        this.titleView = parent.findViewById(R.id.text_title);

        addElementWidgets(networkCard);
        addRegistrationWidgets();
        addButtonWidget();
        layoutWidgets();

        if (networkCard.getPaymentNetworkCount() > 1) {
            networkLogosView = new NetworkLogosView(parent, networkCard.getPaymentNetworks());
        }
        setLastImeOptions();
    }

    static ViewHolder createInstance(ListAdapter adapter, NetworkCard networkCard, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_networkcard, parent, false);
        return new NetworkCardViewHolder(adapter, view, networkCard);
    }

    void onBind() {
        super.onBind();

        NetworkCard networkCard = (NetworkCard) paymentCard;
        bindLabel(titleView, networkCard.getTitle(), false);

        if (networkCard.getPaymentNetworkCount() == 1) {
            bindCardLogo(networkCard.getNetworkCode(), networkCard.getLogoLink());
            setTestId("network");
        } else {
            bindCardLogo(R.drawable.ic_card);
            bindNetworkLogos(networkCard);
            setTestId("group");
        }
        PaymentNetwork network = networkCard.getVisibleNetwork();
        bindRegistrationWidget(AUTO_REGISTRATION, network.getAutoRegistration());
        bindRegistrationWidget(ALLOW_RECURRENCE, network.getAllowRecurrence());
    }

    void bindRegistrationWidget(String name, RegistrationOption option) {
        CheckboxWidget widget = (CheckboxWidget) getFormWidget(name);
        widget.onBind(option.getCheckboxMode(), option.getLabel());
    }

    private void bindNetworkLogos(NetworkCard card) {
        if (networkLogosView == null) {
            return;
        }
        SmartSwitch smartSwitch = card.getSmartSwitch();
        if (smartSwitch.getSelectedCount() == 1) {
            PaymentNetwork network = smartSwitch.getFirstSelected();
            networkLogosView.setSelected(network.getNetworkCode());
            return;
        }
        networkLogosView.setSelected(null);
    }

    private void setTestId(String testId) {
        PaymentUtils.setTestId(itemView, "card", testId);
    }

    private void addRegistrationWidgets() {
        addWidget(new CheckboxWidget(AUTO_REGISTRATION));
        addWidget(new CheckboxWidget(ALLOW_RECURRENCE));
    }
}
