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
 * This exception is thrown when a method is invoked on a Messaging object
 * and the GmailFolder that owns that object has died due to some reason. <p>
 *
 * Following the exception, the GmailFolder is reset to the "closed" state. 
 * All messaging objects owned by the GmailFolder should be considered invalid. 
 * The GmailFolder can be reopened using the "open" method to reestablish the 
 * lost connection. <p>
 *
 * The getMessage() method returns more detailed information about the
 * error that caused this exception. <p>
 *
 * @author John Mani
 */

public class FolderClosedException extends MessagingException {
    transient private Folder folder;

    private static final long serialVersionUID = 1687879213433302315L;
    
    /**
     * Constructor
     * @param folder	the GmailFolder
     */
    public FolderClosedException(Folder folder) {
	this(folder, null);
    }

    /**
     * Constructor
     * @param folder 	the GmailFolder
     * @param message	the detailed error message
     */
    public FolderClosedException(Folder folder, String message) {
	super(message);
	this.folder = folder;
    }

    /**
     * Returns the dead GmailFolder object
     */
    public Folder getFolder() {
	return folder;
    }
}
