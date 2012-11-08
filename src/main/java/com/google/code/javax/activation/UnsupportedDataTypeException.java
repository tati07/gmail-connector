/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)UnsupportedDataTypeException.java	1.10 07/05/14
 */

package com.google.code.javax.activation;

import java.io.IOException;

/**
 * Signals that the requested operation does not support the
 * requested data type.
 *
 * @see javax.activation.DataHandler
 */

public class UnsupportedDataTypeException extends IOException {
    /**
     * Constructs an UnsupportedDataTypeException with no detail
     * message.
     */
    public UnsupportedDataTypeException() {
	super();
    }

    /**
     * Constructs an UnsupportedDataTypeException with the specified 
     * message.
     * 
     * @param s The detail message.
     */
    public UnsupportedDataTypeException(String s) {
	super(s);
    }
}
