/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.list;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.DateWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.SelectWidget;
import net.optile.payment.ui.widget.TextInputWidget;
import net.optile.payment.ui.widget.WidgetInflater;
import net.optile.payment.ui.widget.WidgetPresenter;
import net.optile.payment.util.ImageHelper;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.validation.ValidationResult;

/**
 * The PaymentCardViewHolder holding the header and input widgets
 */
abstract class PaymentCardViewHolder extends RecyclerView.ViewHolder {

    final static float ALPHA_SELECTED = 1f;
    final static float ALPHA_DESELECTED = 0.4f;
    final static int ANIM_DURATION = 200;

    final ViewGroup formLayout;
    final ListAdapter adapter;
    final WidgetPresenter presenter;
    final Map<String, FormWidget> widgets;
    final TableLayout logoLayout;
    final Map<String, ImageView> logos;

    PaymentCardViewHolder(ListAdapter adapter, View parent) {
        super(parent);
        this.adapter = adapter;

        this.formLayout = parent.findViewById(R.id.layout_form);
        this.widgets = new LinkedHashMap<>();
        this.logoLayout = parent.findViewById(R.id.tablelayout_logo);
        this.logos = new HashMap<>();

        View view = parent.findViewById(R.id.layout_header);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onItemClicked(getAdapterPosition());
            }
        });

        this.presenter = new WidgetPresenter() {
            @Override
            public void onActionClicked() {
                adapter.onActionClicked(getAdapterPosition());
            }

            @Override
            public void onHintClicked(String type) {
                adapter.onHintClicked(getAdapterPosition(), type);
            }

            @Override
            public void hideKeyboard() {
                adapter.hideKeyboard(getAdapterPosition());
            }

            @Override
            public void showKeyboard() {
                adapter.showKeyboard(getAdapterPosition());
            }

            @Override
            public void showDialogFragment(DialogFragment dialog, String tag) {
                adapter.showDialogFragment(getAdapterPosition(), dialog, tag);
            }

            @Override
            public ValidationResult validate(String type, String value1, String value2) {
                return adapter.validate(getAdapterPosition(), type, value1, value2);
            }

            @Override
            public void onTextInputChanged(String type, String text) {
                adapter.onTextInputChanged(getAdapterPosition(), type, text);
            }
        };
    }

    void addButtonWidget(PaymentTheme theme) {
        FormWidget widget = WidgetInflater.inflateButtonWidget(PaymentInputType.ACTION_BUTTON, formLayout, theme);
        addWidget(widget);
    }

    void addElementWidgets(List<InputElement> elements, PaymentTheme theme) {
        DateWidget dateWidget = null;
        boolean containsExpiryDate = PaymentUtils.containsExpiryDate(elements);

        for (InputElement element : elements) {
            if (!containsExpiryDate) {
                addWidget(WidgetInflater.inflateElementWidget(element, formLayout, theme));
                continue;
            }
            switch (element.getName()) {
                case PaymentInputType.EXPIRY_MONTH:
                case PaymentInputType.EXPIRY_YEAR:
                    if (dateWidget == null) {
                        dateWidget = WidgetInflater.inflateDateWidget(PaymentInputType.EXPIRY_DATE, formLayout, theme);
                        addWidget(dateWidget);
                    }
                    break;
                default:
                    addWidget(WidgetInflater.inflateElementWidget(element, formLayout, theme));
            }
        }
    }

    void addWidget(FormWidget widget) {
        String name = widget.getName();

        if (widgets.containsKey(name)) {
            return;
        }
        widget.setPresenter(presenter);
        widgets.put(name, widget);
        formLayout.addView(widget.getRootView());
    }

    void addLogoViews(View parent, List<String> names, PaymentTheme theme) {
        int logoBackground = theme.getPageParameters().getPaymentLogoBackground();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Context context = parent.getContext();

        int count = 0;
        TableRow row = null;
        int border = context.getResources().getDimensionPixelSize(R.dimen.pmborder_xsmall);

        for (String name : names) {
            int marginTop = count > 1 ? border : 0;
            int marginRight = 0;

            if (count++ % 2 == 0) {
                row = new TableRow(context);
                logoLayout.addView(row);
                marginRight = border;
            }
            ImageView view = (ImageView) inflater.inflate(R.layout.list_item_logo, row, false);
            LayoutParams params = (LayoutParams) view.getLayoutParams();

            params.setMargins(0, marginTop, marginRight, 0);
            view.setLayoutParams(params);
            PaymentUtils.setImageBackground(view, logoBackground);
            logos.put(name, view);
            row.addView(view);
        }
    }

    boolean isExpanded() {
        return formLayout.getVisibility() == View.VISIBLE;
    }

    void expand(boolean expand) {
        formLayout.setVisibility(expand ? View.VISIBLE : View.GONE);
        clearInputErrors();
    }

    FormWidget getFormWidget(String name) {
        return widgets.get(name);
    }

    void onBind(PaymentCard paymentCard) {
        bindElementWidgets(paymentCard);
        bindDateWidget(paymentCard);
        bindButtonWidget(paymentCard);
    }

    void bindElementWidgets(PaymentCard card) {
        FormWidget widget;
        LanguageFile lang = card.getLang();

        for (InputElement element : card.getInputElements()) {
            widget = getFormWidget(element.getName());

            if (widget instanceof SelectWidget) {
                bindSelectWidget((SelectWidget) widget, element, lang);
            } else if (widget instanceof TextInputWidget) {
                bindTextInputWidget((TextInputWidget) widget, element, lang);
            }
        }
    }

    void bindSelectWidget(SelectWidget widget, InputElement element, LanguageFile lang) {
        bindIconResource(widget);
        widget.setLabel(element.getLabel());
        widget.setSelectOptions(element.getOptions());
    }

    void bindTextInputWidget(TextInputWidget widget, InputElement element, LanguageFile lang) {
        WidgetParameters params = adapter.getPaymentTheme().getWidgetParameters();
        bindIconResource(widget);
        widget.setLabel(element.getLabel());
        widget.setInputType(element.getType());

        int hintDrawable = params.getHintDrawable();
        boolean visible = hintDrawable != 0 && lang.containsAccountHint(widget.getName());
        widget.setHint(visible, hintDrawable);
    }

    void bindIconResource(FormWidget widget) {
        WidgetParameters params = adapter.getPaymentTheme().getWidgetParameters();
        widget.setIconResource(params.getInputTypeIcon(widget.getName()));
    }

    void bindDateWidget(PaymentCard card) {
        String name = PaymentInputType.EXPIRY_DATE;
        DateWidget widget = (DateWidget) getFormWidget(name);

        if (widget == null) {
            return;
        }
        LanguageFile pageLang = adapter.getPageLanguageFile();
        bindIconResource(widget);
        widget.setMonthInputElement(card.getInputElement(PaymentInputType.EXPIRY_MONTH));
        widget.setYearInputElement(card.getInputElement(PaymentInputType.EXPIRY_YEAR));

        widget.setLabel(card.getLang().translateAccountLabel(name));
        widget.setDialogButtonLabel(pageLang.translate(LanguageFile.KEY_BUTTON_UPDATE));
    }

    void bindButtonWidget(PaymentCard card) {
        String name = PaymentInputType.ACTION_BUTTON;
        ButtonWidget widget = (ButtonWidget) getFormWidget(name);

        if (widget == null) {
            return;
        }
        LanguageFile pageLang = adapter.getPageLanguageFile();
        widget.setButtonLabel(pageLang.translate(card.getButton()));
    }

    void bindLogoView(String name, URL url, boolean selected) {
        ImageView view = logos.get(name);

        if (view == null || url == null) {
            return;
        }
        ImageHelper.getInstance().loadImage(view, url);
        float alpha = selected ? ALPHA_SELECTED : ALPHA_DESELECTED;
        ObjectAnimator.ofFloat(view, "alpha", alpha).setDuration(ANIM_DURATION).start();
    }

    void setLastImeOptions() {
        List<String> keys = new ArrayList<>(widgets.keySet());
        Collections.reverse(keys);

        for (String key : keys) {
            FormWidget widget = widgets.get(key);
            if (widget.setLastImeOptionsWidget()) {
                break;
            }
        }
    }

    private void clearInputErrors() {

        for (FormWidget widget : widgets.values()) {
            widget.clearInputErrors();
        }
    }
}
