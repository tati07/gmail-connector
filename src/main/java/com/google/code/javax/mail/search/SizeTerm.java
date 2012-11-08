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
 * This class implements comparisons for Message sizes.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class SizeTerm extends IntegerComparisonTerm {

    private static final long serialVersionUID = -2556219451005103709L;

    /**
     * Constructor.
     *
     * @param comparison	the Comparison type
     * @param size		the size
     */
    public SizeTerm(int comparison, int size) {
	super(comparison, size);
    }

    /**
     * The match method.
     *
     * @param msg	the size comparator is applied to this Message's size
     * @return		true if the size is equal, otherwise false 
     */
    public boolean match(Message msg) {
	int size;

	try {
	    size = msg.getSize();
	} catch (Exception e) {
	    return false;
	}

	if (size == -1)
	    return false;

	return super.match(size);
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof SizeTerm))
	    return false;
	return super.equals(obj);
    }
}
