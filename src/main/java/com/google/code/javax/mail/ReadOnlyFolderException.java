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
 * This exception is thrown when an attempt is made to open a folder
 * read-write access when the folder is marked read-only. <p>
 *
 * The getMessage() method returns more detailed information about the
 * error that caused this exception. <p>
 *
 * @author Jim Glennon
 */

public class ReadOnlyFolderException extends MessagingException {
    transient private Folder folder;

    private static final long serialVersionUID = 5711829372799039325L;
    
    /**
     * Constructs a MessagingException with the specified folder.
     * @param folder	the GmailFolder
     * @since 		JavaMail 1.2
     */
    public ReadOnlyFolderException(Folder folder) {
	this(folder, null);
    }

    /**
     * Constructs a MessagingException with the specified folder and
     * the specified detail message.
     * @param folder 	the GmailFolder
     * @param message	the detailed error message
     * @since 		JavaMail 1.2
     */
    public ReadOnlyFolderException(Folder folder, String message) {
	super(message);
	this.folder = folder;
    }

    /**
     * Returns the dead GmailFolder object.
     * @since 		JavaMail 1.2
     */
    public Folder getFolder() {
	return folder;
    }
}
