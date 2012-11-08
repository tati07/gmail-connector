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
 * @author Bill Shannon
 */

public class LiteralException extends ProtocolException {

    private static final long serialVersionUID = -6919179828339609913L;

    /**
     * Constructs a LiteralException with the specified Response object.
     */
    public LiteralException(Response r) {
	super(r.toString());
	response = r;
    }
}
