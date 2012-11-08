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
import com.google.code.com.sun.mail.imap.protocol.IMAPProtocol;
import com.google.code.com.sun.mail.imap.protocol.ListInfo;
import com.google.code.javax.mail.Folder;
import com.google.code.javax.mail.Message;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.MethodNotSupportedException;

/**
 * The default IMAP folder (root of the naming hierarchy).
 *
 * @author  John Mani
 */

public class DefaultFolder extends IMAPFolder {
    
    protected DefaultFolder(IMAPStore store) {
	super("", UNKNOWN_SEPARATOR, store, null);
	exists = true; // of course
	type = HOLDS_FOLDERS; // obviously
    }

    public synchronized String getName() {
	return fullName;
    }

    public Folder getParent() {
	return null;
    }

    public synchronized Folder[] list(final String pattern)
				throws MessagingException {
	ListInfo[] li = null;

	li = (ListInfo[])doCommand(new ProtocolCommand() {
	    public Object doCommand(IMAPProtocol p) throws ProtocolException {
		return p.list("", pattern);
	    }
	});

	if (li == null)
	    return new Folder[0];

	IMAPFolder[] folders = new IMAPFolder[li.length];
	for (int i = 0; i < folders.length; i++)
	    folders[i] = ((IMAPStore)store).newIMAPFolder(li[i]);
	return folders;
    }

    public synchronized Folder[] listSubscribed(final String pattern)
				throws MessagingException {
	ListInfo[] li = null;

	li = (ListInfo[])doCommand(new ProtocolCommand() {
	    public Object doCommand(IMAPProtocol p) throws ProtocolException {
		return p.lsub("", pattern);
	    }
	});

	if (li == null)
	    return new Folder[0];

	IMAPFolder[] folders = new IMAPFolder[li.length];
	for (int i = 0; i < folders.length; i++)
	    folders[i] = ((IMAPStore)store).newIMAPFolder(li[i]);
	return folders;
    }

    public boolean hasNewMessages() throws MessagingException {
	// Not applicable on DefaultFolder
	return false;
    }

    public Folder getFolder(String name) throws MessagingException {
	return ((IMAPStore)store).newIMAPFolder(name, UNKNOWN_SEPARATOR);
    }

    public boolean delete(boolean recurse) throws MessagingException {  
	// Not applicable on DefaultFolder
	throw new MethodNotSupportedException("Cannot delete Default GmailFolder");
    }

    public boolean renameTo(Folder f) throws MessagingException {
	// Not applicable on DefaultFolder
	throw new MethodNotSupportedException("Cannot rename Default GmailFolder");
    }

    public void appendMessages(Message[] msgs) throws MessagingException {
	// Not applicable on DefaultFolder
	throw new MethodNotSupportedException("Cannot append to Default GmailFolder");
    }

    public Message[] expunge() throws MessagingException {
	// Not applicable on DefaultFolder
	throw new MethodNotSupportedException("Cannot expunge Default GmailFolder");
    }
}
