/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.search;

import com.google.code.javax.mail.MessagingException;


/**
 * The exception thrown when a Search expression could not be handled.
 *
 * @author John Mani
 */

public class SearchException extends MessagingException {

    private static final long serialVersionUID = -7092886778226268686L;

    /**
     * Constructs a SearchException with no detail message.
     */
    public SearchException() {
	super();
    }

    /**
     * Constructs a SearchException with the specified detail message.
     * @param s		the detail message
     */
    public SearchException(String s) {
	super(s);
    }
}
