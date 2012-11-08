/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.event;

import com.google.code.javax.mail.Folder;

/**
 * This class models GmailFolder <em>existence</em> events. FolderEvents are
 * delivered to FolderListeners registered on the affected GmailFolder as
 * well as the containing Store. <p>
 *
 * Service providers vary widely in their ability to notify clients of
 * these events.  At a minimum, service providers must notify listeners
 * registered on the same Store or GmailFolder object on which the operation
 * occurs.  Service providers may also notify listeners when changes
 * are made through operations on other objects in the same virtual
 * machine, or by other clients in the same or other hosts.  Such
 * notifications are not required and are typically not supported
 * by mail protocols (including IMAP).
 *
 * @author John Mani
 * @author Bill Shannon
 */

public class FolderEvent extends MailEvent {

    /** The folder was created. */
    public static final int CREATED 		= 1;
    /** The folder was deleted. */
    public static final int DELETED 		= 2;
    /** The folder was renamed. */
    public static final int RENAMED 		= 3;

    /**
     * The event type.
     *
     * @serial
     */
    protected int type;

    /**
     * The folder the event occurred on.
     */
    transient protected Folder folder;

    /**
     * The folder that represents the new name, in case of a RENAMED event.
     *
     * @since	JavaMail 1.1
     */
    transient protected Folder newFolder;

    private static final long serialVersionUID = 5278131310563694307L;

    /**
     * Constructor. <p>
     *
     * @param source  	The source of the event
     * @param folder	The affected folder
     * @param type	The event type
     */
    public FolderEvent(Object source, Folder folder, int type) {
	this(source, folder, folder, type);
    }

    /**
     * Constructor. Use for RENAMED events.
     *
     * @param source  	The source of the event
     * @param oldFolder	The folder that is renamed
     * @param newFolder	The folder that represents the new name
     * @param type	The event type
     * @since		JavaMail 1.1
     */
    public FolderEvent(Object source, Folder oldFolder, 
		       Folder newFolder, int type) {
	super(source);
	this.folder = oldFolder;
	this.newFolder = newFolder;
	this.type = type;
    }

    /**
     * Return the type of this event.
     *
     * @return  type
     */
    public int getType() {
	return type;
    }

    /**
     * Return the affected folder.
     *
     * @return 		the affected folder
     * @see 		#getNewFolder
     */
    public Folder getFolder() {
	return folder;
    }

    /**
     * If this event indicates that a folder is renamed, (i.e, the event type
     * is RENAMED), then this method returns the GmailFolder object representing the
     * new name. <p>
     *
     * The <code>getFolder()</code> method returns the folder that is renamed.
     *
     * @return		GmailFolder representing the new name.
     * @see		#getFolder
     * @since		JavaMail 1.1
     */
    public Folder getNewFolder() {
	return newFolder;
    }

    /**
     * Invokes the appropriate FolderListener method
     */
    public void dispatch(Object listener) {
	if (type == CREATED)
	    ((FolderListener)listener).folderCreated(this);
	else if (type == DELETED)
	    ((FolderListener)listener).folderDeleted(this);
	else if (type == RENAMED)
	    ((FolderListener)listener).folderRenamed(this);
    }
}
