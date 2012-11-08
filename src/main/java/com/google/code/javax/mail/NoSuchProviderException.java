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
 * This exception is thrown when Session attempts to instantiate a  
 * Provider that doesn't exist.
 * 
 * @author Max Spivak
 */

public class NoSuchProviderException extends MessagingException {

    private static final long serialVersionUID = 8058319293154708827L;
    
    /**
     * Constructor.
     */
    public NoSuchProviderException() {
	super();
    }

    /**
     * Constructor.
     * @param message	The detailed error message
     */
    public NoSuchProviderException(String message) {
	super(message);
    }
}
