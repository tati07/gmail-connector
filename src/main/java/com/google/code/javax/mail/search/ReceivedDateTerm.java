/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.search;

import java.util.Date;

import com.google.code.javax.mail.Message;

/**
 * This class implements comparisons for the Message Received date
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class ReceivedDateTerm extends DateTerm {

    private static final long serialVersionUID = -2756695246195503170L;

    /**
     * Constructor.
     *
     * @param comparison	the Comparison type
     * @param date		the date to be compared
     */
    public ReceivedDateTerm(int comparison, Date date) {
	super(comparison, date);
    }

    /**
     * The match method.
     *
     * @param msg	the date comparator is applied to this Message's
     *			sent date
     * @return		true if the comparison succeeds, otherwise false
     */
    public boolean match(Message msg) {
	Date d;

	try {
	    d = msg.getReceivedDate();
	} catch (Exception e) {
	    return false;
	}

	if (d == null)
	    return false;

	return super.match(d);
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof ReceivedDateTerm))
	    return false;
	return super.equals(obj);
    }
}
