/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.event;

import com.google.code.javax.mail.Store;

/**
 * This class models notifications from the Store connection. These
 * notifications can be ALERTS or NOTICES. ALERTS must be presented
 * to the user in a fashion that calls the user's attention to the
 * message.
 *
 * @author John Mani
 */

public class StoreEvent extends MailEvent {

    /**
     * Indicates that this message is an ALERT.
     */
    public static final int ALERT 		= 1;

    /**
     * Indicates that this message is a NOTICE.
     */
    public static final int NOTICE 		= 2;

    /**
     * The event type.
     *
     * @serial
     */
    protected int type;

    /**
     * The message text to be presented to the user.
     *
     * @serial
     */
    protected String message;

    private static final long serialVersionUID = 1938704919992515330L;

    /**
     * Constructor.
     * @param store  The source Store
     */
    public StoreEvent(Store store, int type, String message) {
	super(store);
	this.type = type;
	this.message = message;
    }

    /**
     * Return the type of this event.
     *
     * @return  type
     * @see #ALERT
     * @see #NOTICE
     */
    public int getMessageType() {
	return type;
    }

    /**
     * Get the message from the Store.
     *
     * @return message from the Store
     */
    public String getMessage() {
	return message;
    }

    /**
     * Invokes the appropriate StoreListener method.
     */
    public void dispatch(Object listener) {
	((StoreListener)listener).notification(this);
    }
}
