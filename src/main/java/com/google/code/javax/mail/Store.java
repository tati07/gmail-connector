/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail;

import java.util.Vector;

import com.google.code.javax.mail.event.FolderEvent;
import com.google.code.javax.mail.event.FolderListener;
import com.google.code.javax.mail.event.StoreEvent;
import com.google.code.javax.mail.event.StoreListener;

/**
 * An abstract class that models a message store and its
 * access protocol, for storing and retrieving messages. 
 * Subclasses provide actual implementations. <p>
 *
 * Note that <code>Store</code> extends the <code>Service</code>
 * class, which provides many common methods for naming stores,
 * connecting to stores, and listening to connection events.
 *
 * @author John Mani
 * @author Bill Shannon
 *
 * @see javax.mail.Service
 * @see javax.mail.event.ConnectionEvent
 * @see javax.mail.event.StoreEvent
 */

public abstract class Store extends Service {

    /**
     * Constructor.
     *
     * @param	session Session object for this Store.
     * @param	urlname	URLName object to be used for this Store
     */
    protected Store(Session session, URLName urlname) {
	super(session, urlname);
    }

    /**
     * Returns a GmailFolder object that represents the 'root' of
     * the default namespace presented to the user by the Store.
     *
     * @return the root GmailFolder
     * @exception IllegalStateException if this Store is not connected.
     */
    public abstract Folder getDefaultFolder() throws MessagingException;

    /**
     * Return the GmailFolder object corresponding to the given name. Note
     * that a GmailFolder object is returned even if the named folder does
     * not physically exist on the Store. The <code>exists()</code> 
     * method on the folder object indicates whether this folder really
     * exists. <p>
     *
     * GmailFolder objects are not cached by the Store, so invoking this
     * method on the same name multiple times will return that many
     * distinct GmailFolder objects.
     *
     * @param name 	The name of the GmailFolder. In some Stores, name can
     *			be an absolute path if it starts with the
     *			hierarchy delimiter. Else it is interpreted
     *			relative to the 'root' of this namespace.
     * @return		GmailFolder object
     * @exception 	IllegalStateException if this Store is not connected.
     * @see 		GmailFolder#exists
     * @see		GmailFolder#create
     */
    public abstract Folder getFolder(String name)
			throws MessagingException;

    /**
     * Return a closed GmailFolder object, corresponding to the given 
     * URLName. The store specified in the given URLName should
     * refer to this Store object. <p>
     *
     * Implementations of this method may obtain the name of the
     * actual folder using the <code>getFile()</code> method on
     * URLName, and use that name to create the folder.
     * 
     * @param url	URLName that denotes a folder
     * @see 		URLName
     * @exception 	IllegalStateException if this Store is not connected.
     * @return		GmailFolder object
     */
    public abstract Folder getFolder(URLName url)
			throws MessagingException;

    /**
     * Return a set of folders representing the <i>personal</i> namespaces
     * for the current user.  A personal namespace is a set of names that
     * is considered within the personal scope of the authenticated user.
     * Typically, only the authenticated user has access to mail folders
     * in their personal namespace.  If an INBOX exists for a user, it
     * must appear within the user's personal namespace.  In the
     * typical case, there should be only one personal namespace for each
     * user in each Store. <p>
     *
     * This implementation returns an array with a single entry containing
     * the return value of the <code>getDefaultFolder</code> method.
     * Subclasses should override this method to return appropriate information.
     *
     * @exception 	IllegalStateException if this Store is not connected.
     * @return		array of GmailFolder objects
     * @since		JavaMail 1.2
     */
    public Folder[] getPersonalNamespaces() throws MessagingException {
	return new Folder[] { getDefaultFolder() };
    }

    /**
     * Return a set of folders representing the namespaces for
     * <code>user</code>.  The namespaces returned represent the
     * personal namespaces for the user.  To access mail folders in the
     * other user's namespace, the currently authenticated user must be
     * explicitly granted access rights.  For example, it is common for
     * a manager to grant to their secretary access rights to their
     * mail folders. <p>
     *
     * This implementation returns an empty array.  Subclasses should
     * override this method to return appropriate information.
     *
     * @exception 	IllegalStateException if this Store is not connected.
     * @return		array of GmailFolder objects
     * @since		JavaMail 1.2
     */
    public Folder[] getUserNamespaces(String user)
				throws MessagingException {
	return new Folder[0];
    }

    /**
     * Return a set of folders representing the <i>shared</i> namespaces.
     * A shared namespace is a namespace that consists of mail folders
     * that are intended to be shared amongst users and do not exist
     * within a user's personal namespace. <p>
     *
     * This implementation returns an empty array.  Subclasses should
     * override this method to return appropriate information.
     *
     * @exception 	IllegalStateException if this Store is not connected.
     * @return		array of GmailFolder objects
     * @since		JavaMail 1.2
     */
    public Folder[] getSharedNamespaces() throws MessagingException {
	return new Folder[0];
    }

    // Vector of Store listeners
    private volatile Vector storeListeners = null;

    /**
     * Add a listener for StoreEvents on this Store. <p>
     *
     * The default implementation provided here adds this listener
     * to an internal list of StoreListeners.
     *
     * @param l         the Listener for Store events
     * @see             javax.mail.event.StoreEvent
     */
    public synchronized void addStoreListener(StoreListener l) {
	if (storeListeners == null)
	    storeListeners = new Vector();
	storeListeners.addElement(l);
    }

    /**
     * Remove a listener for Store events. <p>
     *
     * The default implementation provided here removes this listener
     * from the internal list of StoreListeners.
     *
     * @param l         the listener
     * @see             #addStoreListener
     */
    public synchronized void removeStoreListener(StoreListener l) {
	if (storeListeners != null)
	    storeListeners.removeElement(l);
    }

    /**
     * Notify all StoreListeners. Store implementations are
     * expected to use this method to broadcast StoreEvents. <p>
     *
     * The provided default implementation queues the event into
     * an internal event queue. An event dispatcher thread dequeues
     * events from the queue and dispatches them to the registered
     * StoreListeners. Note that the event dispatching occurs
     * in a separate thread, thus avoiding potential deadlock problems.
     */
    protected void notifyStoreListeners(int type, String message) {
   	if (storeListeners == null)
	    return;
	
	StoreEvent e = new StoreEvent(this, type, message);
	queueEvent(e, storeListeners);
    }

    // Vector of folder listeners
    private volatile Vector folderListeners = null;

    /**
     * Add a listener for GmailFolder events on any GmailFolder object 
     * obtained from this Store. FolderEvents are delivered to
     * FolderListeners on the affected GmailFolder as well as to 
     * FolderListeners on the containing Store. <p>
     *
     * The default implementation provided here adds this listener
     * to an internal list of FolderListeners.
     *
     * @param l         the Listener for GmailFolder events
     * @see             javax.mail.event.FolderEvent
     */
    public synchronized void addFolderListener(FolderListener l) {
   	if (folderListeners == null)
	    folderListeners = new Vector();
	folderListeners.addElement(l);
    }

    /**
     * Remove a listener for GmailFolder events. <p>
     *
     * The default implementation provided here removes this listener
     * from the internal list of FolderListeners.
     *
     * @param l         the listener
     * @see             #addFolderListener
     */
    public synchronized void removeFolderListener(FolderListener l) {
   	if (folderListeners != null)
	    folderListeners.removeElement(l);
    }

    /**
     * Notify all FolderListeners. Store implementations are
     * expected to use this method to broadcast GmailFolder events. <p>
     *
     * The provided default implementation queues the event into
     * an internal event queue. An event dispatcher thread dequeues
     * events from the queue and dispatches them to the registered
     * FolderListeners. Note that the event dispatching occurs
     * in a separate thread, thus avoiding potential deadlock problems.
     *
     * @param	type	type of FolderEvent
     * @param	folder	affected GmailFolder
     * @see		#notifyFolderRenamedListeners
     */
    protected void notifyFolderListeners(int type, Folder folder) {
   	if (folderListeners == null) 
	    return;
	
	FolderEvent e = new FolderEvent(this, folder, type);
	queueEvent(e, folderListeners);
    }

    /**
     * Notify all FolderListeners about the renaming of a folder.
     * Store implementations are expected to use this method to broadcast 
     * GmailFolder events indicating the renaming of folders. <p>
     *
     * The provided default implementation queues the event into
     * an internal event queue. An event dispatcher thread dequeues
     * events from the queue and dispatches them to the registered
     * FolderListeners. Note that the event dispatching occurs
     * in a separate thread, thus avoiding potential deadlock problems.
     *
     * @param	oldF	the folder being renamed
     * @param	newF	the folder representing the new name.
     * @since	JavaMail 1.1
     */
    protected void notifyFolderRenamedListeners(Folder oldF, Folder newF) {
   	if (folderListeners == null) 
	    return;
	
	FolderEvent e = new FolderEvent(this, oldF, newF,FolderEvent.RENAMED);
	queueEvent(e, folderListeners);
    }
}
