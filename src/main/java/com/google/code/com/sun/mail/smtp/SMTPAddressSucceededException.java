/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.smtp;

import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.internet.InternetAddress;

/**
 * This exception is chained off a SendFailedException when the
 * <code>mail.smtp.reportsuccess</code> property is true.  It
 * indicates an address to which the message was sent.  The command
 * will be an SMTP RCPT command and the return code will be the
 * return code from that command.
 *
 * @since JavaMail 1.3.2
 */

public class SMTPAddressSucceededException extends MessagingException {
    protected InternetAddress addr;	// address that succeeded
    protected String cmd;		// command issued to server
    protected int rc;			// return code from SMTP server

    private static final long serialVersionUID = -1168335848623096749L;

    /**
     * Constructs an SMTPAddressSucceededException with the specified 
     * address, return code, and error string.
     *
     * @param addr	the address that succeeded
     * @param cmd	the command that was sent to the SMTP server
     * @param rc	the SMTP return code indicating the success
     * @param err	the error string from the SMTP server
     */
    public SMTPAddressSucceededException(InternetAddress addr,
				String cmd, int rc, String err) {
	super(err);
	this.addr = addr;
	this.cmd = cmd;
	this.rc = rc;
    }

    /**
     * Return the address that succeeded.
     */
    public InternetAddress getAddress() {
	return addr;
    }

    /**
     * Return the command that succeeded.
     */
    public String getCommand() {
	return cmd;
    }


    /**
     * Return the return code from the SMTP server that indicates the
     * reason for the success.  See
     * <A HREF="http://www.ietf.org/rfc/rfc821.txt">RFC 821</A>
     * for interpretation of the return code.
     */
    public int getReturnCode() {
	return rc;
    }
}
