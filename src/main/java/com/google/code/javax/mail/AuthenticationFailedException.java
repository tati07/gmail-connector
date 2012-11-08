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
 * This exception is thrown when the connect method on a Store or
 * Transport object fails due to an authentication failure (e.g.,
 * bad user name or password).
 *
 * @author Bill Shannon
 */

public class AuthenticationFailedException extends MessagingException {

    private static final long serialVersionUID = 492080754054436511L;

    /**
     * Constructor
     */
    public AuthenticationFailedException() {
	super();
    }

    /**
     * Constructor
     * @param message	The detailed error message
     */
    public AuthenticationFailedException(String message) {
	super(message);
    }
}
