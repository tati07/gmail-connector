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
 * This term models the RFC822 "MessageId" - a message-id for 
 * Internet messages that is supposed to be unique per message.
 * Clients can use this term to search a folder for a message given
 * its MessageId. <p>
 *
 * The MessageId is represented as a String.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class MessageIDTerm extends StringTerm {

    private static final long serialVersionUID = -2121096296454691963L;

    /**
     * Constructor.
     *
     * @param msgid  the msgid to search for
     */
    public MessageIDTerm(String msgid) {
	// Note: comparison is case-insensitive
	super(msgid);
    }

    /**
     * The match method.
     *
     * @param msg	the match is applied to this Message's 
     *			Message-ID header
     * @return		true if the match succeeds, otherwise false
     */
    public boolean match(Message msg) {
	String[] s;

	try {
	    s = msg.getHeader("Message-ID");
	} catch (Exception e) {
	    return false;
	}

	if (s == null)
	    return false;

	for (int i=0; i < s.length; i++)
	    if (super.match(s[i]))
		return true;
	return false;
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof MessageIDTerm))
	    return false;
	return super.equals(obj);
    }
}
