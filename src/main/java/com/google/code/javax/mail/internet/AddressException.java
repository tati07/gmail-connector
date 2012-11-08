/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.internet;

/**
 * The exception thrown when a wrongly formatted address is encountered.
 *
 * @author Bill Shannon
 * @author Max Spivak
 */

public class AddressException extends ParseException {
    /**
     * The string being parsed.
     *
     * @serial
     */
    protected String ref = null;

    /**
     * The index in the string where the error occurred, or -1 if not known.
     *
     * @serial
     */
    protected int pos = -1;

    private static final long serialVersionUID = 9134583443539323120L;

    /**
     * Constructs an AddressException with no detail message.
     */
    public AddressException() {
	super();
    }

    /**
     * Constructs an AddressException with the specified detail message.
     * @param s		the detail message
     */
    public AddressException(String s) {
	super(s);
    }

    /**
     * Constructs an AddressException with the specified detail message
     * and reference info.
     *
     * @param s		the detail message
     */

    public AddressException(String s, String ref) {
	super(s);
	this.ref = ref;
    }
    /**
     * Constructs an AddressException with the specified detail message
     * and reference info.
     *
     * @param s		the detail message
     */
    public AddressException(String s, String ref, int pos) {
	super(s);
	this.ref = ref;
	this.pos = pos;
    }

    /**
     * Get the string that was being parsed when the error was detected
     * (null if not relevant).
     */
    public String getRef() {
	return ref;
    }

    /**
     * Get the position with the reference string where the error was
     * detected (-1 if not relevant).
     */
    public int getPos() {
	return pos;
    }

    public String toString() {
	String s = super.toString();
	if (ref == null)
	    return s;
	s += " in string ``" + ref + "''";
	if (pos < 0)
	    return s;
	return s + " at position " + pos;
    }
}
