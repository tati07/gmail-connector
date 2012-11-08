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
 * The exception thrown when a write is attempted on a read-only attribute
 * of any Messaging object. 
 *
 * @author John Mani
 */

public
class IllegalWriteException extends MessagingException {

    private static final long serialVersionUID = 3974370223328268013L;

    /**
     * Constructs a IllegalWriteException with no detail message.
     */
    public IllegalWriteException() {
	super();
    }

    /**
     * Constructs a IllegalWriteException with the specified detail message.
     * @param s		the detail message
     */
    public IllegalWriteException(String s) {
	super(s);
    }
}
