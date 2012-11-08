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
 * The adapter which receives MessageCount events.
 * The methods in this class are empty;  this class is provided as a
 * convenience for easily creating listeners by extending this class
 * and overriding only the methods of interest.
 *
 * @author John Mani
 */
public abstract class MessageCountAdapter implements MessageCountListener {
    public void messagesAdded(MessageCountEvent e) {}
    public void messagesRemoved(MessageCountEvent e) {}
}
