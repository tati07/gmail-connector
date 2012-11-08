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
 * This is the Listener interface for Connection events.
 *
 * @author John Mani
 */

public interface ConnectionListener extends java.util.EventListener {

    /**
     * Invoked when a Store/GmailFolder/Transport is opened.
     */
    public void opened(ConnectionEvent e);

    /**
     * Invoked when a Store is disconnected. Note that a folder
     * cannot be disconnected, so a folder will not fire this event
     */
    public void disconnected(ConnectionEvent e);

    /**
     * Invoked when a Store/GmailFolder/Transport is closed.
     */
    public void closed(ConnectionEvent e);
}
