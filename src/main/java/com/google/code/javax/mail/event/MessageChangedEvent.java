/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.event;

import com.google.code.javax.mail.Message;

/**
 * This class models Message change events.
 *
 * @author John Mani
 */

public class MessageChangedEvent extends MailEvent {

    /** The message's flags changed. */
    public static final int FLAGS_CHANGED 	= 1;
    /** The message's envelope (headers, but not body) changed. */
    public static final int ENVELOPE_CHANGED 	= 2;
	/** The message's labels changed. */
    public static final int LABELS_CHANGED 	= 3;

    /**
     * The event type.
     *
     * @serial
     */
    protected int type;

    /**
     * The message that changed.
     */
    transient protected Message msg;

    private static final long serialVersionUID = -4974972972105535108L;

    /**
     * Constructor.
     * @param source  	The folder that owns the message
     * @param type	The change type
     * @param msg	The changed message 
     */
    public MessageChangedEvent(Object source, int type, Message msg) {
	super(source);
	this.msg = msg;
	this.type = type;
    }

    /**
     * Return the type of this event.
     * @return  type
     */
    public int getMessageChangeType() {
	return type;
    }

    /**
     * Return the changed Message.
     * @return  the message
     */
    public Message getMessage() {
	return msg;
    }

    /**
     * Invokes the appropriate MessageChangedListener method.
     */
    public void dispatch(Object listener) {
	((MessageChangedListener)listener).messageChanged(this);
    }
}
