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
 * This is the Listener interface for MessageCount events.
 *
 * @author John Mani
 */

public interface MessageCountListener extends java.util.EventListener {
    /**
     * Invoked when messages are added into a folder.
     */
    public void messagesAdded(MessageCountEvent e);

    /**
     * Invoked when messages are removed (expunged) from a folder.
     */
    public void messagesRemoved(MessageCountEvent e);
}
