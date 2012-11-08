/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap.protocol;

import com.google.code.com.sun.mail.iap.ParsingException;

/**
 * This class represents the UID data item
 *
 * @author  John Mani
 */

public class UID implements Item {
    
    static final char[] name = {'U','I','D'};
    public int seqnum;

    public long uid;

    /**
     * Constructor
     */
    public UID(FetchResponse r) throws ParsingException {
	seqnum = r.getNumber();
	r.skipSpaces();
	uid = r.readLong();
    }
}
