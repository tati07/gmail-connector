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

/**
 * This class implements Message Address comparisons.
 *
 * @author Bill Shannon
 * @author John Mani
 */

public abstract class AddressTerm extends SearchTerm {
    /**
     * The address.
     *
     * @serial
     */
    protected Address address;

    private static final long serialVersionUID = 2005405551929769980L;

    protected AddressTerm(Address address) {
	this.address = address;
    }

    /**
     * Return the address to match with.
     */
    public Address getAddress() {
	return address;
    }

    /**
     * Match against the argument Address.
     */
    protected boolean match(Address a) {
	return (a.equals(address));
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof AddressTerm))
	    return false;
	AddressTerm at = (AddressTerm)obj;
	return at.address.equals(this.address);
    }

    /**
     * Compute a hashCode for this object.
     */
    public int hashCode() {
	return address.hashCode();
    }
}
