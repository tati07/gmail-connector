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
 * This class implements the logical NEGATION operator.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class NotTerm extends SearchTerm {
    /**
     * The search term to negate.
     *
     * @serial
     */
    protected SearchTerm term;

    private static final long serialVersionUID = 7152293214217310216L;

    public NotTerm(SearchTerm t) {
	term = t;
    }

    /**
     * Return the term to negate.
     */
    public SearchTerm getTerm() {
	return term;
    }

    /* The NOT operation */
    public boolean match(Message msg) {
	return !term.match(msg);
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof NotTerm))
	    return false;
	NotTerm nt = (NotTerm)obj;
	return nt.term.equals(this.term);
    }

    /**
     * Compute a hashCode for this object.
     */
    public int hashCode() {
	return term.hashCode() << 1;
    }
}
