/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.pop3;

import com.google.code.javax.mail.Flags;
import com.google.code.javax.mail.Folder;
import com.google.code.javax.mail.Message;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.MethodNotSupportedException;

/**
 * The POP3 DefaultFolder.  Only contains the "INBOX" folder.
 *
 * @author Christopher Cotton
 */
public class DefaultFolder extends Folder {

    DefaultFolder(POP3Store store) {
	super(store);
    }

    public String getName() {
	return "";
    }

    public String getFullName() {
	return "";
    }

    public Folder getParent() {
	return null;
    }

    public boolean exists() {
	return true;
    }

    public Folder[] list(String pattern) throws MessagingException {
	Folder[] f = { getInbox() };
	return f;
    }

    public char getSeparator() {
	return '/';
    }

    public int getType() {
	return HOLDS_FOLDERS;
    }

    public boolean create(int type) throws MessagingException {
	return false;
    }

    public boolean hasNewMessages() throws MessagingException {
	return false;
    }

    public Folder getFolder(String name) throws MessagingException {
	if (!name.equalsIgnoreCase("INBOX")) {
	    throw new MessagingException("only INBOX supported");
	} else {
	    return getInbox();
	}
    }

    protected Folder getInbox() throws MessagingException {
	return getStore().getFolder("INBOX");
    }
    

    public boolean delete(boolean recurse) throws MessagingException {
	throw new MethodNotSupportedException("delete");
    }

    public boolean renameTo(Folder f) throws MessagingException {
	throw new MethodNotSupportedException("renameTo");
    }

    public void open(int mode) throws MessagingException {
	throw new MethodNotSupportedException("open");
    }

    public void close(boolean expunge) throws MessagingException {
	throw new MethodNotSupportedException("close");
    }

    public boolean isOpen() {
	return false;
    }

    public Flags getPermanentFlags() {
	return new Flags(); // empty flags object
    }

    public int getMessageCount() throws MessagingException {
	return 0;
    }

    public Message getMessage(int msgno) throws MessagingException {
	throw new MethodNotSupportedException("getMessage");
    }

    public void appendMessages(Message[] msgs) throws MessagingException {
	throw new MethodNotSupportedException("Append not supported");	
    }

    public Message[] expunge() throws MessagingException {
	throw new MethodNotSupportedException("expunge");	
    }

    public Folder[] xlist(String pattern) throws MessagingException {
        throw new MethodNotSupportedException("XLIST supported yet.");
    }
}
