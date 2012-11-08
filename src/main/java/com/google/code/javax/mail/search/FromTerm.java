/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.search;

import com.google.code.javax.mail.Address;
import com.google.code.javax.mail.Message;

/**
 * This class implements comparisons for the From Address header.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class FromTerm extends AddressTerm {

    private static final long serialVersionUID = 5214730291502658665L;

    /**
     * Constructor
     * @param address	The Address to be compared
     */
    public FromTerm(Address address) {
	super(address);
    }

    /**
     * The address comparator.
     *
     * @param msg	The address comparison is applied to this Message
     * @return		true if the comparison succeeds, otherwise false
     */
    public boolean match(Message msg) {
	Address[] from;

	try {
	    from = msg.getFrom();
	} catch (Exception e) {
	    return false;
	}

	if (from == null)
	    return false;

	for (int i=0; i < from.length; i++)
	    if (super.match(from[i]))
		return true;
	return false;
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof FromTerm))
	    return false;
	return super.equals(obj);
    }
}
