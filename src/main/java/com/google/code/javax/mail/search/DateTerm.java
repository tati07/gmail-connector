/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.search;

import java.util.Date;

/**
 * This class implements comparisons for Dates
 *
 * @author Bill Shannon
 * @author John Mani
 */
public abstract class DateTerm extends ComparisonTerm {
    /**
     * The date.
     *
     * @serial
     */
    protected Date date;

    private static final long serialVersionUID = 4818873430063720043L;

    /**
     * Constructor.
     * @param comparison the comparison type
     * @param date  The Date to be compared against
     */
    protected DateTerm(int comparison, Date date) {
	this.comparison = comparison;
	this.date = date;
    }

    /**
     * Return the Date to compare with.
     */
    public Date getDate() {
	return new Date(date.getTime());
    }

    /**
     * Return the type of comparison.
     */
    public int getComparison() {
	return comparison;
    }

    /**
     * The date comparison method.
     *
     * @param d	the date in the constructor is compared with this date
     * @return  true if the dates match, otherwise false
     */
    protected boolean match(Date d) {
	switch (comparison) {
	    case LE: 
		return d.before(date) || d.equals(date);
	    case LT:
		return d.before(date);
	    case EQ:
		return d.equals(date);
	    case NE:
		return !d.equals(date);
	    case GT:
		return d.after(date);
	    case GE:
		return d.after(date) || d.equals(date);
	    default:
		return false;
	}
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof DateTerm))
	    return false;
	DateTerm dt = (DateTerm)obj;
	return dt.date.equals(this.date) && super.equals(obj);
    }

    /**
     * Compute a hashCode for this object.
     */
    public int hashCode() {
	return date.hashCode() + super.hashCode();
    }
}
