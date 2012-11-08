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
 * The exception thrown when a method is not supported by the 
 * implementation
 *
 * @author John Mani
 */

public
class MethodNotSupportedException extends MessagingException {

    private static final long serialVersionUID = -3757386618726131322L;

    /**
     * Constructs a MethodNotSupportedException with no detail message.
     */
    public MethodNotSupportedException() {
	super();
    }

    /**
     * Constructs a MethodNotSupportedException with the specified detail message.
     * @param s		the detail message
     */
    public MethodNotSupportedException(String s) {
	super(s);
    }
}
