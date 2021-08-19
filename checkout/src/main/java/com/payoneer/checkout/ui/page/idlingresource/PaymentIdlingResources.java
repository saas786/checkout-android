/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.page.idlingresource;

import androidx.test.espresso.IdlingResource;

/**
 * Class providing IdlingResources and interacting with these IdlingResources used in the Payment screens.
 */
public final class PaymentIdlingResources {

    private SimpleIdlingResource closeIdlingResource;
    private SimpleIdlingResource dialogIdlingResource;
    private SimpleIdlingResource loadIdlingResource;

    private boolean closeIdlingState;
    private boolean dialogIdlingState;
    private boolean loadIdlingState;

    private final String className;

    /**
     * Construct a new PaymentIdlingResources class with the provided className.
     * The className is used to uniquely name the IdlingResources.
     *
     * @param className name of the class that is using this PaymentIdlingResources
     */
    public PaymentIdlingResources(final String className) {
        this.className = className;
    }

    /**
     * Gets the DialogIdlingResource, this IdlingResource can be used to wait for the condition
     * that a PaymentDialog is shown to the user.
     *
     * @return IdlingResource for the show dialog condition
     */
    public IdlingResource getDialogIdlingResource() {
        if (dialogIdlingResource == null) {
            dialogIdlingResource = new SimpleIdlingResource(className + "-dialogIdlingResource");
        }
        setIdlingResourceState(dialogIdlingResource, dialogIdlingState);
        return dialogIdlingResource;
    }

    public void setDialogIdlingState(final boolean dialogIdlingState) {
        this.dialogIdlingState = dialogIdlingState;
        setIdlingResourceState(dialogIdlingResource, dialogIdlingState);
    }

    public void resetDialogIdlingResource() {
        dialogIdlingState = false;
        setIdlingResourceState(dialogIdlingResource, false);
    }

    /**
     * Gets the CloseIdlingResource, this IdlingResource can be used to wait for the condition that the page
     * is closed.
     *
     * @return IdlingResource for waiting that the page is closed
     */
    public IdlingResource getCloseIdlingResource() {
        if (closeIdlingResource == null) {
            closeIdlingResource = new SimpleIdlingResource(className + "-closeIdlingResource");
        }
        setIdlingResourceState(closeIdlingResource, closeIdlingState);
        return closeIdlingResource;
    }

    public void setCloseIdlingState(final boolean closeIdlingState) {
        this.closeIdlingState = closeIdlingState;
        setIdlingResourceState(closeIdlingResource, closeIdlingState);
    }

    public void resetCloseIdlingResource() {
        closeIdlingState = false;
        setIdlingResourceState(closeIdlingResource, false);
    }

    /**
     * Gets the LoadIdlingResource, this IdlingResource can be used to wait for the condition that the page
     * has loaded the PaymentSession and is showing it in the page.
     *
     * @return IdlingResource for waiting until the PaymentSession is loaded and shown
     */
    public IdlingResource getLoadIdlingResource() {
        if (loadIdlingResource == null) {
            loadIdlingResource = new SimpleIdlingResource(className + "-loadIdlingResource");
        }
        setIdlingResourceState(loadIdlingResource, loadIdlingState);
        return loadIdlingResource;
    }

    public void setLoadIdlingState(final boolean loadIdlingState) {
        this.loadIdlingState = loadIdlingState;
        setIdlingResourceState(loadIdlingResource, loadIdlingState);
    }

    public void resetLoadIdlingResource() {
        loadIdlingState = false;
        setIdlingResourceState(loadIdlingResource, false);
    }

    private void setIdlingResourceState(final SimpleIdlingResource idlingResource, final boolean state) {
        if (idlingResource != null) {
            if (state) {
                idlingResource.setIdleState(state);
            } else {
                idlingResource.reset();
            }
        }
    }
}
