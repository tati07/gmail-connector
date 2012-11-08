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

public class ProtocolException extends Exception {
    protected transient Response response = null;

    private static final long serialVersionUID = -4360500807971797439L;

    /**
     * Constructs a ProtocolException with no detail message.
     */
    public ProtocolException() {
	super();
    }

    /**
     * Constructs a ProtocolException with the specified detail message.
     * @param message		the detail message
     */
    public ProtocolException(String message) {
	super(message);
    }

    /**
     * Constructs a ProtocolException with the specified detail message
     * and cause.
     * @param message		the detail message
     * @param cause		the cause
     */
    public ProtocolException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Constructs a ProtocolException with the specified Response object.
     */
    public ProtocolException(Response r) {
	super(r.toString());
	response = r;
    }

    /**
     * Return the offending Response object.
     */
    public Response getResponse() {
	return response;
    }
}
