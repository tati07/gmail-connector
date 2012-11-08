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
 * This class represents the X-GM-THRID data item
 *
 */

public class X_GM_THRID implements Item {

    static final char[] name = {'X','-','G','M','-','T','H','R','I','D'};
    public int seqnum;

    public long x_gm_thrid;

    /**
     * Constructor
     */
    public X_GM_THRID(FetchResponse r) throws ParsingException {
	seqnum = r.getNumber();
	r.skipSpaces();
	x_gm_thrid = r.readLong();
    }
}
