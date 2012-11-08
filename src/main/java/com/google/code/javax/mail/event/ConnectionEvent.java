/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.event;


/**
 * This class models Connection events.
 *
 * @author John Mani
 */

public class ConnectionEvent extends MailEvent  {

    /** A connection was opened. */
    public static final int OPENED 		= 1;
    /** A connection was disconnected (not currently used). */
    public static final int DISCONNECTED 	= 2;
    /** A connection was closed. */
    public static final int CLOSED 		= 3;

    /**
     * The event type.
     *
     * @serial
     */
    protected int type;

    private static final long serialVersionUID = -1855480171284792957L;

    /**
     * Constructor
     * @param source  The source object
     */
    public ConnectionEvent(Object source, int type) {
	super(source);
	this.type = type;
    }

    /**
     * Return the type of this event
     * @return  type
     */
    public int getType() {
	return type;
    }

    /**
     * Invokes the appropriate ConnectionListener method
     */
    public void dispatch(Object listener) {
	if (type == OPENED)
	    ((ConnectionListener)listener).opened(this);
	else if (type == DISCONNECTED)
	    ((ConnectionListener)listener).disconnected(this);
	else if (type == CLOSED)
	    ((ConnectionListener)listener).closed(this);
    }
}
