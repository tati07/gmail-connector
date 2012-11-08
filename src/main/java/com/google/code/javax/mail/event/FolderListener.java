/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.event;


/**
 * This is the Listener interface for GmailFolder events.
 *
 * @author John Mani
 */

public interface FolderListener extends java.util.EventListener {
    /**
     * Invoked when a GmailFolder is created.
     */
    public void folderCreated(FolderEvent e);

    /**
     * Invoked when a folder is deleted.
     */
    public void folderDeleted(FolderEvent e);

    /**
     * Invoked when a folder is renamed.
     */
    public void folderRenamed(FolderEvent e);
}
