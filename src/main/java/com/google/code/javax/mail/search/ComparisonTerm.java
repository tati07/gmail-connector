/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.search;

/**
 * This class models the comparison operator. This is an abstract
 * class; subclasses implement comparisons for different datatypes.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public abstract class ComparisonTerm extends SearchTerm {
    public static final int LE = 1;
    public static final int LT = 2;
    public static final int EQ = 3;
    public static final int NE = 4;
    public static final int GT = 5;
    public static final int GE = 6;

    /**
     * The comparison.
     *
     * @serial
     */
    protected int comparison;

    private static final long serialVersionUID = 1456646953666474308L;

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof ComparisonTerm))
	    return false;
	ComparisonTerm ct = (ComparisonTerm)obj;
	return ct.comparison == this.comparison;
    }

    /**
     * Compute a hashCode for this object.
     */
    public int hashCode() {
	return comparison;
    }
}
