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
 * This class implements the logical AND operator on individual
 * SearchTerms.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class AndTerm extends SearchTerm {

    /**
     * The array of terms on which the AND operator should be
     * applied.
     *
     * @serial
     */
    protected SearchTerm[] terms;

    private static final long serialVersionUID = -3583274505380989582L;

    /**
     * Constructor that takes two terms.
     * 
     * @param t1 first term
     * @param t2 second term
     */
    public AndTerm(SearchTerm t1, SearchTerm t2) {
	terms = new SearchTerm[2];
	terms[0] = t1;
	terms[1] = t2;
    }

    /**
     * Constructor that takes an array of SearchTerms.
     * 
     * @param t  array of terms
     */
    public AndTerm(SearchTerm[] t) {
	terms = new SearchTerm[t.length]; // clone the array
	for (int i = 0; i < t.length; i++)
	    terms[i] = t[i];
    }

    /**
     * Return the search terms.
     */
    public SearchTerm[] getTerms() {
	return (SearchTerm[])terms.clone();
    }

    /**
     * The AND operation. <p>
     *
     * The terms specified in the constructor are applied to
     * the given object and the AND operator is applied to their results.
     *
     * @param msg	The specified SearchTerms are applied to this Message
     *			and the AND operator is applied to their results.
     * @return		true if the AND succeds, otherwise false
     */
    public boolean match(Message msg) {
	for (int i=0; i < terms.length; i++)
	    if (!terms[i].match(msg))
		return false;
	return true;
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof AndTerm))
	    return false;
	AndTerm at = (AndTerm)obj;
	if (at.terms.length != terms.length)
	    return false;
	for (int i=0; i < terms.length; i++)
	    if (!terms[i].equals(at.terms[i]))
		return false;
	return true;
    }

    /**
     * Compute a hashCode for this object.
     */
    public int hashCode() {
	int hash = 0;
	for (int i=0; i < terms.length; i++)
	    hash += terms[i].hashCode();
	return hash;
    }
}
