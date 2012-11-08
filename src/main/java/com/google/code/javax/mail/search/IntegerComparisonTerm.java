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
 * This class implements comparisons for integers.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public abstract class IntegerComparisonTerm extends ComparisonTerm {
    /**
     * The number.
     *
     * @serial
     */
    protected int number;

    private static final long serialVersionUID = -6963571240154302484L;

    protected IntegerComparisonTerm(int comparison, int number) {
	this.comparison = comparison;
	this.number = number;
    }

    /**
     * Return the number to compare with.
     */
    public int getNumber() {
	return number;
    }

    /**
     * Return the type of comparison.
     */
    public int getComparison() {
	return comparison;
    }

    protected boolean match(int i) {
	switch (comparison) {
	    case LE: 
		return i <= number;
	    case LT:
		return i < number;
	    case EQ:
		return i == number;
	    case NE:
		return i != number;
	    case GT:
		return i > number;
	    case GE:
		return i >= number;
	    default:
		return false;
	}
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof IntegerComparisonTerm))
	    return false;
	IntegerComparisonTerm ict = (IntegerComparisonTerm)obj;
	return ict.number == this.number && super.equals(obj);
    }

    /**
     * Compute a hashCode for this object.
     */
    public int hashCode() {
	return number + super.hashCode();
    }
}
