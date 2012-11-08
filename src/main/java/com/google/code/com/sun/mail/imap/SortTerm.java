/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap;

/**
 * A particular sort criteria, as defined by
 * <A HREF="http://www.ietf.org/rfc/rfc5256.txt">RFC 5256</A>.
 * Sort criteria are used with the
 * {@link IMAPFolder#getSortedMessages getSortedMessages} method.
 * Multiple sort criteria are specified in an array with the order in
 * the array specifying the order in which the sort criteria are applied.
 *
 * @since JavaMail 1.4.4
 */
public final class SortTerm {
    /**
     * Sort by message arrival date and time.
     */
    public static final SortTerm ARRIVAL = new SortTerm("ARRIVAL");

    /**
     * Sort by email address of first Cc recipient.
     */
    public static final SortTerm CC = new SortTerm("CC");

    /**
     * Sort by sent date and time.
     */
    public static final SortTerm DATE = new SortTerm("DATE");

    /**
     * Sort by first From email address.
     */
    public static final SortTerm FROM = new SortTerm("FROM");

    /**
     * Reverse the sort order of the following item.
     */
    public static final SortTerm REVERSE = new SortTerm("REVERSE");

    /**
     * Sort by the message size.
     */
    public static final SortTerm SIZE = new SortTerm("SIZE");

    /**
     * Sort by the base subject text.  Note that the "base subject"
     * is defined by RFC 5256 and doesn't include items such as "Re:"
     * in the subject header.
     */
    public static final SortTerm SUBJECT = new SortTerm("SUBJECT");

    /**
     * Sort by email address of first To recipient.
     */
    public static final SortTerm TO = new SortTerm("TO");

    private String term;
    private SortTerm(String term) {
	this.term = term;
    }

    public String toString() {
	return term;
    }
}
