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

public class ParsingException extends ProtocolException {

    private static final long serialVersionUID = 7756119840142724839L;

    /**
     * Constructs an ParsingException with no detail message.
     */
    public ParsingException() {
	super();
    }

    /**
     * Constructs an ParsingException with the specified detail message.
     * @param s		the detail message
     */
    public ParsingException(String s) {
	super(s);
    }

    /**
     * Constructs an ParsingException with the specified Response.
     * @param r		the Response
     */
    public ParsingException(Response r) {
	super(r);
    }
}
