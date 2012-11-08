/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap;

import com.google.code.com.sun.mail.iap.ProtocolException;
import com.google.code.com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.google.code.com.sun.mail.imap.protocol.ENVELOPE;
import com.google.code.com.sun.mail.imap.protocol.IMAPProtocol;
import com.google.code.javax.mail.Flags;
import com.google.code.javax.mail.FolderClosedException;
import com.google.code.javax.mail.MessageRemovedException;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.MethodNotSupportedException;

/**
 * This class implements a nested IMAP message
 *
 * @author  John Mani
 */

public class IMAPNestedMessage extends IMAPMessage {
    private IMAPMessage msg; // the enclosure of this nested message

    /**
     * Package private constructor. <p>
     *
     * Note that nested messages have no containing folder, nor 
     * a message number.
     */
    IMAPNestedMessage(IMAPMessage m, BODYSTRUCTURE b, ENVELOPE e, String sid) {
	super(m._getSession());
	msg = m;
	bs = b;
	envelope = e;
	sectionId = sid;
	setPeek(m.getPeek());
    }

    /*
     * Get the enclosing message's Protocol object. Overrides
     * IMAPMessage.getProtocol().
     */
    protected IMAPProtocol getProtocol()
			throws ProtocolException, FolderClosedException {
	return msg.getProtocol();
    }

    /*
     * Is this an IMAP4 REV1 server?
     */
    protected boolean isREV1() throws FolderClosedException {
	return msg.isREV1();
    }

    /*
     * Get the enclosing message's messageCacheLock. Overrides
     * IMAPMessage.getMessageCacheLock().
     */
    protected Object getMessageCacheLock() {
	return msg.getMessageCacheLock();
    }

    /*
     * Get the enclosing message's sequence number. Overrides
     * IMAPMessage.getSequenceNumber().
     */
    protected int getSequenceNumber() {
	return msg.getSequenceNumber();
    }

    /*
     * Check whether the enclosing message is expunged. Overrides 
     * IMAPMessage.checkExpunged().
     */
    protected void checkExpunged() throws MessageRemovedException {
	msg.checkExpunged();
    }

    /*
     * Check whether the enclosing message is expunged. Overrides
     * Message.isExpunged().
     */
    public boolean isExpunged() {
	return msg.isExpunged();
    }

    /*
     * Get the enclosing message's fetchBlockSize. 
     */
    protected int getFetchBlockSize() {
	return msg.getFetchBlockSize();
    }

    /*
     * IMAPMessage uses RFC822.SIZE. We use the "size" field from
     * our BODYSTRUCTURE.
     */
    public int getSize() throws MessagingException {
	return bs.size;
    }

    /*
     * Disallow setting flags on nested messages
     */
    public synchronized void setFlags(Flags flag, boolean set) 
			throws MessagingException {
	// Cannot set FLAGS on a nested IMAP message	
	throw new MethodNotSupportedException(
		"Cannot set flags on this nested message");
    }
}
