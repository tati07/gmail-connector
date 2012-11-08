/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.search;

public abstract class GmailSearchTerm extends SearchTerm {

    /**
     * The pattern.
     *
     * @serial
     */
    protected String pattern;

    /**
     * Ignore case when comparing?
     *
     * @serial
     */
    protected boolean ignoreCase;

    protected GmailSearchTerm(String pattern) {
	this.pattern = pattern;
	ignoreCase = true;
    }

    protected GmailSearchTerm(String pattern, boolean ignoreCase) {
	this.pattern = pattern;
	this.ignoreCase = ignoreCase;
    }

    /**
     * Return the string to match with.
     */
    public String getPattern() {
	return pattern;
    }

    /**
     * Return true if we should ignore case when matching.
     */
    public boolean getIgnoreCase() {
	return ignoreCase;
    }

    protected boolean match(String s) {
	int len = s.length() - pattern.length();
	for (int i=0; i <= len; i++) {
	    if (s.regionMatches(ignoreCase, i, 
				pattern, 0, pattern.length()))
		return true;
	}
	return false;
    }

    /**
     * Equality comparison.
     */
    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof StringTerm))
	    return false;
	StringTerm st = (StringTerm)obj;
	if (ignoreCase)
	    return st.pattern.equalsIgnoreCase(this.pattern) &&
		    st.ignoreCase == this.ignoreCase;
	else
	    return st.pattern.equals(this.pattern) &&
		    st.ignoreCase == this.ignoreCase;
    }

    /**
     * Compute a hashCode for this object.
     */
    @Override
    public int hashCode() {
	return ignoreCase ? pattern.hashCode() : ~pattern.hashCode();
    }
        
    public abstract String getSearchAttribute();
    
}
