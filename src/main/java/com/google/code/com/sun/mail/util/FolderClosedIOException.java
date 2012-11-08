/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.util;

import java.io.IOException;

import com.google.code.javax.mail.Folder;

/**
 * A variant of FolderClosedException that can be thrown from methods
 * that only throw IOException.  The getContent method will catch this
 * exception and translate it back to FolderClosedException.
 *
 * @author Bill Shannon
 */

public class FolderClosedIOException extends IOException {
    transient private Folder folder;

    private static final long serialVersionUID = 4281122580365555735L;
    
    /**
     * Constructor
     * @param folder	the GmailFolder
     */
    public FolderClosedIOException(Folder folder) {
	this(folder, null);
    }

    /**
     * Constructor
     * @param folder 	the GmailFolder
     * @param message	the detailed error message
     */
    public FolderClosedIOException(Folder folder, String message) {
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
