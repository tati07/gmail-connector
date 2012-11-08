/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap.protocol;

import java.io.ByteArrayInputStream;

import com.google.code.com.sun.mail.iap.ByteArray;
import com.google.code.com.sun.mail.iap.ParsingException;

/**
 * This class 
 *
 * @author  John Mani
 */

public class RFC822DATA implements Item {
   
    static final char[] name = {'R','F','C','8','2','2'};
    public int msgno;
    public ByteArray data;

    /**
     * Constructor
     * @param port	portnumber to connect to
     */
    public RFC822DATA(FetchResponse r) throws ParsingException {
	msgno = r.getNumber();
	r.skipSpaces();
	data = r.readByteArray();
    }

    public ByteArray getByteArray() {
	return data;
    }

    public ByteArrayInputStream getByteArrayInputStream() {
	if (data != null)
	    return data.toByteArrayInputStream();
	else
	    return null;
    }
}
