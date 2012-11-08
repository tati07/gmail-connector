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
 * This class represents the X-GM-LABELS data item
 *
 */

public class X_GM_LABELS implements Item {

    static final char[] name = {'X','-','G','M','-','L','A','B','E','L','S'};
    public int seqnum;

    public String[] x_gm_labels;

    /**
     * Constructor
     */
    public X_GM_LABELS(FetchResponse r) throws ParsingException {
	seqnum = r.getNumber();
	r.skipSpaces();
	//x_gm_labels = r.readSimpleList()
        x_gm_labels = r.readAtomStringList();
    }
    
}
