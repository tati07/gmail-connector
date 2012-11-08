/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail;

/**
 * The exception thrown when an invalid method is invoked on an expunged
 * Message. The only valid methods on an expunged Message are
 * <code>isExpunged()</code> and <code>getMessageNumber()</code>.
 *
 * @see	   javax.mail.Message#isExpunged()
 * @see	   javax.mail.Message#getMessageNumber()
 * @author John Mani
 */

public class MessageRemovedException extends MessagingException {

    private static final long serialVersionUID = 1951292550679528690L;

    /**
     * Constructs a MessageRemovedException with no detail message.
     */
    public MessageRemovedException() {
	super();
    }

    /**
     * Constructs a MessageRemovedException with the specified detail message.
     * @param s		the detail message
     */
    public MessageRemovedException(String s) {
	super(s);
    }
}
