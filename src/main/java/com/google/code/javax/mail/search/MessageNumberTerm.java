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

/**
 * This class implements comparisons for Message numbers.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class MessageNumberTerm extends IntegerComparisonTerm {

    private static final long serialVersionUID = -5379625829658623812L;

    /**
     * Constructor.
     *
     * @param number  the Message number
     */
    public MessageNumberTerm(int number) {
	super(EQ, number);
    }

    /**
     * The match method.
     *
     * @param msg	the Message number is matched with this Message
     * @return		true if the match succeeds, otherwise false
     */
    public boolean match(Message msg) {
	int msgno;

	try {
	    msgno = msg.getMessageNumber();
	} catch (Exception e) {
	    return false;
	}
	
	return super.match(msgno);
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof MessageNumberTerm))
	    return false;
	return super.equals(obj);
    }
}
