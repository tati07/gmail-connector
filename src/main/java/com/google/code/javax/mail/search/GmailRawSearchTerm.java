/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.search;

import com.google.code.javax.mail.Message;

public final class GmailRawSearchTerm extends GmailSearchTerm {

    protected final static String searchAttribute = "X-GM-RAW";
    
    public GmailRawSearchTerm(String gm_raw) {
	// Note: comparison is case-insensitive
	super(gm_raw);
    }


    /**
     * Equality comparison.
     */
    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof GmailRawSearchTerm))
	    return false;
	return super.equals(obj);
    }

    @Override
    public String getSearchAttribute() {
        return searchAttribute;
    }

    @Override
    public boolean match(Message msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
