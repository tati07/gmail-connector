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

public class ConnectionException extends ProtocolException {
    private transient Protocol p;

    private static final long serialVersionUID = 5749739604257464727L;

    /**
     * Constructs an ConnectionException with no detail message.
     */
    public ConnectionException() {
	super();
    }

    /**
     * Constructs an ConnectionException with the specified detail message.
     * @param s		the detail message
     */
    public ConnectionException(String s) {
	super(s);
    }

    /**
     * Constructs an ConnectionException with the specified Response.
     * @param r		the Response
     */
    public ConnectionException(Protocol p, Response r) {
	super(r);
	this.p = p;
    }

    public Protocol getProtocol() {
	return p;
    }
}
