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

public class BadCommandException extends ProtocolException {

    private static final long serialVersionUID = 5769722539397237515L;

    /**
     * Constructs an BadCommandException with no detail message.
     */
    public BadCommandException() {
	super();
    }

    /**
     * Constructs an BadCommandException with the specified detail message.
     * @param s		the detail message
     */
    public BadCommandException(String s) {
	super(s);
    }

    /**
     * Constructs an BadCommandException with the specified Response.
     * @param r		the Response
     */
    public BadCommandException(Response r) {
	super(r);
    }
}
