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
 * This class implements comparisons for the message Subject header.
 * The comparison is case-insensitive.  The pattern is a simple string
 * that must appear as a substring in the Subject.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class SubjectTerm extends StringTerm {

    private static final long serialVersionUID = 7481568618055573432L;

    /**
     * Constructor.
     *
     * @param pattern  the pattern to search for
     */
    public SubjectTerm(String pattern) {
	// Note: comparison is case-insensitive
	super(pattern);
    }

    /**
     * The match method.
     *
     * @param msg	the pattern match is applied to this Message's 
     *			subject header
     * @return		true if the pattern match succeeds, otherwise false
     */
    public boolean match(Message msg) {
	String subj;

	try {
	    subj = msg.getSubject();
	} catch (Exception e) {
	    return false;
	}

	if (subj == null)
	    return false;

	return super.match(subj);
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof SubjectTerm))
	    return false;
	return super.equals(obj);
    }
}
