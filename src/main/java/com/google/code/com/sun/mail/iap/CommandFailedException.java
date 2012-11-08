/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.iap;

/**
 * @author John Mani
 */

public class CommandFailedException extends ProtocolException {

    private static final long serialVersionUID = 793932807880443631L;

    /**
     * Constructs an CommandFailedException with no detail message.
     */
    public CommandFailedException() {
	super();
    }

    /**
     * Constructs an CommandFailedException with the specified detail message.
     * @param s		the detail message
     */
    public CommandFailedException(String s) {
	super(s);
    }

    /**
     * Constructs an CommandFailedException with the specified Response.
     * @param r		the Response.
     */
    public CommandFailedException(Response r) {
	super(r);
    }
}
