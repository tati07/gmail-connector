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
 * This exception is thrown by GmailFolder methods, when those
 * methods are invoked on a non existent folder.
 *
 * @author John Mani
 */

public class FolderNotFoundException extends MessagingException {
    transient private Folder folder;

    private static final long serialVersionUID = 472612108891249403L;

    /**
     * Constructs a MessagingException with no detail message.
     */
    public FolderNotFoundException() {
	super();
    }

    /**
     * Constructs a MessagingException with the specified folder.
     * @param folder	the GmailFolder
     * @since		JavaMail 1.2 
     */
    public FolderNotFoundException(Folder folder) {
	super();
        this.folder = folder;
    }

    /**
     * Constructs a MessagingException with the specified folder and 
     * the specified detail message.
     * @param folder	the GmailFolder
     * @param s		the detail message
     * @since		JavaMail 1.2
     */
    public FolderNotFoundException(Folder folder, String s) {
	super(s);
	this.folder = folder;
    }

    /**
     * Constructs a MessagingException with the specified detail message
     * and the specified folder.
     * @param s		the detail message
     * @param folder	the GmailFolder
     */
    public FolderNotFoundException(String s, Folder folder) {
	super(s);
	this.folder = folder;
    }

    /**
     * Returns the offending GmailFolder object.
     * @return	the GmailFolder object. Note that the returned value can be
     * 		<code>null</code>.
     */
    public Folder getFolder() {
	return folder;
    }
}
