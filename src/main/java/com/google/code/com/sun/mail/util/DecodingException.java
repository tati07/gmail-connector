/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.util;

import java.io.IOException;

/**
 * A special IOException that indicates a failure to decode data due
 * to an error in the formatting of the data.  This allows applications
 * to distinguish decoding errors from other I/O errors.
 *
 * @author Bill Shannon
 */

public class DecodingException extends IOException {

    private static final long serialVersionUID = -6913647794421459390L;

    /**
     * Constructor
     */
    public DecodingException(String s) {
	super(s);
    }
}
