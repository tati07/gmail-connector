/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.internet;

import com.google.code.javax.mail.MessagingException;

/**
 * The exception thrown due to an error in parsing RFC822 
 * or MIME headers
 *
 * @author John Mani
 */

public class ParseException extends MessagingException {

    private static final long serialVersionUID = 7649991205183658089L;

    /**
     * Constructs a ParseException with no detail message.
     */
    public ParseException() {
	super();
    }

    /**
     * Constructs a ParseException with the specified detail message.
     * @param s		the detail message
     */
    public ParseException(String s) {
	super(s);
    }
}
