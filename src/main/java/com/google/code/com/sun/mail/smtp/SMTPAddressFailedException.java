/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.smtp;

import com.google.code.javax.mail.SendFailedException;
import com.google.code.javax.mail.internet.InternetAddress;

/**
 * This exception is thrown when the message cannot be sent. <p>
 * 
 * The exception includes the address to which the message could not be
 * sent.  This will usually appear in a chained list of exceptions,
 * one per address, attached to a top level SendFailedException that
 * aggregates all the addresses.
 *
 * @since JavaMail 1.3.2
 */

public class SMTPAddressFailedException extends SendFailedException {
    protected InternetAddress addr;	// address that failed
    protected String cmd;		// command issued to server
    protected int rc;			// return code from SMTP server

    private static final long serialVersionUID = 804831199768630097L;

    /**
     * Constructs an SMTPAddressFailedException with the specified 
     * address, return code, and error string.
     *
     * @param addr	the address that failed
     * @param cmd	the command that was sent to the SMTP server
     * @param rc	the SMTP return code indicating the failure
     * @param err	the error string from the SMTP server
     */
    public SMTPAddressFailedException(InternetAddress addr, String cmd, int rc,
				String err) {
	super(err);
	this.addr = addr;
	this.cmd = cmd;
	this.rc = rc;
    }

    /**
     * Return the address that failed.
     */
    public InternetAddress getAddress() {
	return addr;
    }

    /**
     * Return the command that failed.
     */
    public String getCommand() {
	return cmd;
    }


    /**
     * Return the return code from the SMTP server that indicates the
     * reason for the failure.  See
     * <A HREF="http://www.ietf.org/rfc/rfc821.txt">RFC 821</A>
     * for interpretation of the return code.
     */
    public int getReturnCode() {
	return rc;
    }
}
