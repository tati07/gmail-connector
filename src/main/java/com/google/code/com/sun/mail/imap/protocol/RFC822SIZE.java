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
 * This class 
 *
 * @author  John Mani
 */

public class RFC822SIZE implements Item {
    
    static final char[] name = {'R','F','C','8','2','2','.','S','I','Z','E'};
    public int msgno;

    public int size;

    /**
     * Constructor
     * @param port	portnumber to connect to
     */
    public RFC822SIZE(FetchResponse r) throws ParsingException {
	msgno = r.getNumber();
	r.skipSpaces();
	size = r.readNumber();		
    }
}
