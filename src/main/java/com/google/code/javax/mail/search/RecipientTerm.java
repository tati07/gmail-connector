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
import com.google.code.javax.mail.Message;

/**
 * This class implements comparisons for the Recipient Address headers.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class RecipientTerm extends AddressTerm {

    /**
     * The recipient type.
     *
     * @serial
     */
    protected Message.RecipientType type;

    private static final long serialVersionUID = 6548700653122680468L;

    /**
     * Constructor.
     *
     * @param type	the recipient type
     * @param address	the address to match for
     */
    public RecipientTerm(Message.RecipientType type, Address address) {
	super(address);
	this.type = type;
    }

    /**
     * Return the type of recipient to match with.
     */
    public Message.RecipientType getRecipientType() {
	return type;
    }

    /**
     * The match method.
     *
     * @param msg	The address match is applied to this Message's recepient
     *			address
     * @return		true if the match succeeds, otherwise false
     */
    public boolean match(Message msg) {
	Address[] recipients;

	try {
 	    recipients = msg.getRecipients(type);
	} catch (Exception e) {
	    return false;
	}

	if (recipients == null)
	    return false;

	for (int i=0; i < recipients.length; i++)
	    if (super.match(recipients[i]))
		return true;
	return false;
    }

    /**
     * Equality comparison.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof RecipientTerm))
	    return false;
	RecipientTerm rt = (RecipientTerm)obj;
	return rt.type.equals(this.type) && super.equals(obj);
    }

    /**
     * Compute a hashCode for this object.
     */
    public int hashCode() {
	return type.hashCode() + super.hashCode();
    }
}
