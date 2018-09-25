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

package net.optile.payment.ui.widget;

import net.optile.payment.R;
import android.view.View;
import android.widget.EditText;
import net.optile.payment.model.InputElement;
import android.support.design.widget.TextInputLayout;
    
/**
 * Class for handling the String input type
 */
public final class StringInputWidget extends FormWidget {

    private final InputElement element;
    
    private final EditText input;

    private final TextInputLayout layout;
    
    /** 
     * Construct a new StringInputWidget
     * 
     * @param name     name identifying this widget
     * @param rootView the root view of this input
     * @param element the InputElement this widget is displaying
     */
    public StringInputWidget(String name, View rootView, InputElement element) {
        super(name, rootView);
        this.element = element;
        layout = rootView.findViewById(R.id.layout_value);
        input = rootView.findViewById(R.id.input_value);

        layout.setHintAnimationEnabled(false);
        layout.setHint(element.getLabel());
        layout.setHintAnimationEnabled(true);
    }
}
