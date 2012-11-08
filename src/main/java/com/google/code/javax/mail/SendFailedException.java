/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail;

/**
 * This exception is thrown when the message cannot be sent.<p>
 * 
 * The exception includes those addresses to which the message could not be
 * sent as well as the valid addresses to which the message was sent and
 * valid addresses to which the message was not sent.
 *
 * @see	javax.mail.Transport#send
 * @see	javax.mail.Transport#sendMessage
 * @see	javax.mail.event.TransportEvent
 *
 * @author John Mani
 * @author Max Spivak
 */

public class SendFailedException extends MessagingException {
    transient protected Address[] invalid;
    transient protected Address[] validSent;
    transient protected Address[] validUnsent;

    private static final long serialVersionUID = -6457531621682372913L;

    /**
     * Constructs a SendFailedException with no detail message.
     */
    public SendFailedException() {
	super();
    }

    /**
     * Constructs a SendFailedException with the specified detail message.
     * @param s		the detail message
     */
    public SendFailedException(String s) {
	super(s);
    }

    /**
     * Constructs a SendFailedException with the specified 
     * Exception and detail message. The specified exception is chained
     * to this exception.
     * @param s		the detail message
     * @param e		the embedded exception
     * @see	#getNextException
     * @see	#setNextException
     */
    public SendFailedException(String s, Exception e) {
	super(s, e);
    }


    /**
     * Constructs a SendFailedException with the specified string
     * and the specified address objects.
     *
     * @param msg	the detail message
     * @param ex        the embedded exception
     * @param validSent valid addresses to which message was sent
     * @param validUnsent valid addresses to which message was not sent
     * @param invalid 	the invalid addresses
     * @see	#getNextException
     * @see	#setNextException
     */
    public SendFailedException(String msg, Exception ex, Address[] validSent, 
			       Address[] validUnsent, Address[] invalid) {
	super(msg, ex);
	this.validSent = validSent;
	this.validUnsent = validUnsent;
	this.invalid = invalid;
    }

    /**
     * Return the addresses to which this message was sent succesfully.
     * @return Addresses to which the message was sent successfully or null
     */
    public Address[] getValidSentAddresses() {
	return validSent;
    }

    /**
     * Return the addresses that are valid but to which this message 
     * was not sent.
     * @return Addresses that are valid but to which the message was 
     *         not sent successfully or null
     */
    public Address[] getValidUnsentAddresses() {
	return validUnsent;
    }

    /**
     * Return the addresses to which this message could not be sent.
     *
     * @return Addresses to which the message sending failed or null;
     */
    public Address[] getInvalidAddresses() {
	return invalid;
    }
}
