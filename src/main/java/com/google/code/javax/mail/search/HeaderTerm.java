/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.search;

import java.util.Locale;

import com.google.code.javax.mail.Message;

/**
 * This class implements comparisons for Message headers.
 * The comparison is case-insensitive.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class HeaderTerm extends StringTerm {
    /**
     * The name of the header.
     *
     * @serial
     */
    protected String headerName;

    private static final long serialVersionUID = 8342514650333389122L;

    /**
     * Constructor.
     *
     * @param headerName The name of the header
     * @param pattern    The pattern to search for
     */
    public HeaderTerm(String headerName, String pattern) {
	super(pattern);
	this.headerName = headerName;
    }

    /**
     * Return the name of the header to compare with.
     */
    public String getHeaderName() {
	return headerName;
    }

    /**
     * The header match method.
     *
     * @param msg	The match is applied to this Message's header
     * @return		true if the match succeeds, otherwise false
     */
    public boolean match(Message msg) {
	String[] headers;

	try {
	    headers = msg.getHeader(headerName);
	} catch (Exception e) {
	    return false;
	}

	if (headers == null)
	    return false;

	for (int i=0; i < headers.length; i++)
	    if (super.match(headers[i]))
		return true;
	return false;
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof HeaderTerm))
	    return false;
	HeaderTerm ht = (HeaderTerm)obj;
	// XXX - depends on header comparisons being case independent
	return ht.headerName.equalsIgnoreCase(headerName) && super.equals(ht);
    }

    /**
     * Compute a hashCode for this object.
     */
    public int hashCode() {
	// XXX - depends on header comparisons being case independent
	return headerName.toLowerCase(Locale.ENGLISH).hashCode() +
					super.hashCode();
    }
}
