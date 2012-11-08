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
 * This is the Listener interface for Transport events
 *
 * @author John Mani
 * @author Max Spivak
 *
 * @see javax.mail.Transport
 * @see javax.mail.event.TransportEvent
 */

public interface TransportListener extends java.util.EventListener {

    /**
     * Invoked when a Message is succesfully delivered.
     * @param	e TransportEvent
     */
    public void messageDelivered(TransportEvent e);

    /**
     * Invoked when a Message is not delivered.
     * @param	e TransportEvent
     * @see TransportEvent
     */
    public void messageNotDelivered(TransportEvent e);

    /**
     * Invoked when a Message is partially delivered.
     * @param	e TransportEvent
     * @see TransportEvent
     */
    public void messagePartiallyDelivered(TransportEvent e);
}
