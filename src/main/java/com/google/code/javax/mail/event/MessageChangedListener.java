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
 * This is the Listener interface for MessageChanged events
 *
 * @author John Mani
 */

public interface MessageChangedListener extends java.util.EventListener {
    /**
     * Invoked when a message is changed. The change-type specifies
     * what changed.
     * @see MessageChangedEvent#FLAGS_CHANGED
     * @see MessageChangedEvent#ENVELOPE_CHANGED
     */
    public void messageChanged(MessageChangedEvent e);
}
