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
 * The BODY fetch response item.
 *
 * @author  John Mani
 */

public class BODY implements Item {
    
    static final char[] name = {'B','O','D','Y'};

    public int msgno;
    public ByteArray data;
    public String section;
    public int origin = 0;

    /**
     * Constructor
     */
    public BODY(FetchResponse r) throws ParsingException {
	msgno = r.getNumber();

	r.skipSpaces();

	int b;
	while ((b = r.readByte()) != ']') { // skip section
	    if (b == 0)
		throw new ParsingException(
			"BODY parse error: missing ``]'' at section end");
	}

	
	if (r.readByte() == '<') { // origin
	    origin = r.readNumber();
	    r.skip(1); // skip '>';
	}

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
