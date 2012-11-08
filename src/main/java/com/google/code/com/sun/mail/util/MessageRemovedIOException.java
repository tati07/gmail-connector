/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.util;

import java.io.IOException;

/**
 * A variant of MessageRemovedException that can be thrown from methods
 * that only throw IOException.  The getContent method will catch this
 * exception and translate it back to MessageRemovedException.
 *
 * @see	   javax.mail.Message#isExpunged()
 * @see	   javax.mail.Message#getMessageNumber()
 * @author Bill Shannon
 */

public class MessageRemovedIOException extends IOException {

    private static final long serialVersionUID = 4280468026581616424L;

    /**
     * Constructs a MessageRemovedIOException with no detail message.
     */
    public MessageRemovedIOException() {
	super();
    }

    /**
     * Constructs a MessageRemovedIOException with the specified detail message.
     * @param s		the detail message
     */
    public MessageRemovedIOException(String s) {
	super(s);
    }
}
