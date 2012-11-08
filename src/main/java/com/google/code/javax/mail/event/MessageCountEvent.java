/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.event;

import com.google.code.javax.mail.Folder;
import com.google.code.javax.mail.Message;

/**
 * This class notifies changes in the number of messages in a folder. <p>
 *
 * Note that some folder types may only deliver MessageCountEvents at
 * certain times or after certain operations.  IMAP in particular will
 * only notify the client of MessageCountEvents when a client issues a
 * new command.
 * Refer to RFC 2060 <A HREF="http://www.ietf.org/rfc/rfc2060.txt">
 * http://www.ietf.org/rfc/rfc2060.txt</A> for details.
 * A client may want "poll" the folder by occasionally calling the
 * <code>getMessageCount</code> or <code>isConnected</code> methods
 * to solicit any such notifications.
 *
 * @author John Mani
 */

public class MessageCountEvent extends MailEvent {

    /** The messages were added to their folder */
    public static final int ADDED 		= 1;
    /** The messages were removed from their folder */
    public static final int REMOVED 		= 2;

    /**
     * The event type.
     *
     * @serial
     */
    protected int type;

    /**
     * If true, this event is the result of an explicit
     * expunge by this client, and the messages in this 
     * folder have been renumbered to account for this.
     * If false, this event is the result of an expunge
     * by external sources.
     *
     * @serial
     */
    protected boolean removed;

    /**
     * The messages.
     */
    transient protected Message[] msgs;

    private static final long serialVersionUID = -7447022340837897369L;

    /**
     * Constructor.
     * @param folder  	The containing folder
     * @param type	The event type
     * @param removed	If true, this event is the result of an explicit
     *			expunge by this client, and the messages in this 
     *			folder have been renumbered to account for this.
     *			If false, this event is the result of an expunge
     *			by external sources.
     *
     * @param msgs	The messages added/removed
     */
    public MessageCountEvent(Folder folder, int type, 
			     boolean removed, Message[] msgs) {
	super(folder);
	this.type = type;
	this.removed = removed;
	this.msgs = msgs;
    }

    /**
     * Return the type of this event.
     * @return  type
     */
    public int getType() {
	return type;
    }

    /**
     * Indicates whether this event is the result of an explicit
     * expunge by this client, or due to an expunge from external
     * sources. If <code>true</code>, this event is due to an
     * explicit expunge and hence all remaining messages in this
     * folder have been renumbered. If <code>false</code>, this event
     * is due to an external expunge. <p>
     *
     * Note that this method is valid only if the type of this event
     * is <code>REMOVED</code>
     */
    public boolean isRemoved() {
	return removed;
    }

    /**
     * Return the array of messages added or removed.
     * @return array of messages
     */
    public Message[] getMessages() {
	return msgs;
    }

    /**
     * Invokes the appropriate MessageCountListener method.
     */
    public void dispatch(Object listener) {
	if (type == ADDED)
	    ((MessageCountListener)listener).messagesAdded(this);
	else // REMOVED
	    ((MessageCountListener)listener).messagesRemoved(this);
    }
}
