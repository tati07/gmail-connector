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

/**
 * Clients use a FetchProfile to list the Message attributes that 
 * it wishes to prefetch from the server for a range of messages.<p>
 *
 * Messages obtained from a GmailFolder are light-weight objects that 
 * typically start off as empty references to the actual messages.
 * Such a Message object is filled in "on-demand" when the appropriate 
 * get*() methods are invoked on that particular Message. Certain
 * server-based message access protocols (Ex: IMAP) allow batch
 * fetching of message attributes for a range of messages in a single
 * request. Clients that want to use message attributes for a range of
 * Messages (Example: to display the top-level headers in a headerlist)
 * might want to use the optimization provided by such servers. The
 * <code>FetchProfile</code> allows the client to indicate this desire
 * to the server. <p>
 *
 * Note that implementations are not obligated to support
 * FetchProfiles, since there might be cases where the backend service 
 * does not allow easy, efficient fetching of such profiles. <p>
 *
 * Sample code that illustrates the use of a FetchProfile is given
 * below:  <p>
 * <blockquote>
 * <pre>
 *
 *  Message[] msgs = folder.getMessages();
 *
 *  FetchProfile fp = new FetchProfile();
 *  fp.add(FetchProfile.Item.ENVELOPE);
 *  fp.add("X-mailer");
 *  folder.fetch(msgs, fp);
 *
 * </pre></blockquote><p>
 *
 * @see org.mule.module.gmail.search.GmailFolder.Folder#fetch
 * @author John Mani
 * @author Bill Shannon
 */

public class FetchProfile {

    private Vector specials; // specials
    private Vector headers; // vector of header names

    /**
     * This inner class is the base class of all items that
     * can be requested in a FetchProfile. The items currently
     * defined here are <code>ENVELOPE</code>, <code>CONTENT_INFO</code>
     * and <code>FLAGS</code>. The <code>UIDFolder</code> interface 
     * defines the <code>UID</code> Item as well. <p>
     *
     * Note that this class only has a protected constructor, therby
     * restricting new Item types to either this class or subclasses.
     * This effectively implements a enumeration of allowed Item types.
     *
     * @see UIDFolder
     */

    public static class Item {
	/**
	 * This is the Envelope item. <p>
	 *
	 * The Envelope is an aggregration of the common attributes
	 * of a Message. Implementations should include the following
	 * attributes: From, To, Cc, Bcc, ReplyTo, Subject and Date.
	 * More items may be included as well. <p>
	 *
	 * For implementations of the IMAP4 protocol (RFC 2060), the 
	 * Envelope should include the ENVELOPE data item. More items
	 * may be included too.
	 */
	public static final Item ENVELOPE = new Item("ENVELOPE");

	/**
	 * This item is for fetching information about the 
	 * content of the message. <p>
	 *
	 * This includes all the attributes that describe the content
	 * of the message. Implementations should include the following
	 * attributes: ContentType, ContentDisposition, 
	 * ContentDescription, Size and LineCount. Other items may be
	 * included as well.
	 */
	public static final Item CONTENT_INFO = new Item("CONTENT_INFO");

	/**
	 * This is the Flags item.
	 */
	public static final Item FLAGS = new Item("FLAGS");

	private String name;

	/**
	 * Constructor for an item.  The name is used only for debugging.
	 */
	protected Item(String name) {
	    this.name = name;
	}

	/**
	 * Include the name in the toString return value for debugging.
	 */
	public String toString() {
	    return getClass().getName() + "[" + name + "]";
	}
    }

    /**
     * Create an empty FetchProfile.
     */
    public FetchProfile() { 
	specials = null;
	headers = null;
    }
    
    /**
     * Add the given special item as one of the attributes to
     * be prefetched.
     *
     * @param	item		the special item to be fetched
     * @see	FetchProfile.Item#ENVELOPE
     * @see	FetchProfile.Item#CONTENT_INFO
     * @see	FetchProfile.Item#FLAGS
     */
    public void add(Item item) { 
	if (specials == null)
	    specials = new Vector();
	specials.addElement(item);
    }

    /**
     * Add the specified header-field to the list of attributes
     * to be prefetched.
     *
     * @param	headerName	header to be prefetched
     */
    public void add(String headerName) { 
   	if (headers == null)
	    headers = new Vector();
	headers.addElement(headerName);
    }

    /**
     * Returns true if the fetch profile contains given special item.
     */
    public boolean contains(Item item) { 
   	return specials != null && specials.contains(item);
    }

    /**
     * Returns true if the fetch profile contains given header name.
     */
    public boolean contains(String headerName) { 
   	return headers != null && headers.contains(headerName);
    }

    /**
     * Get the items set in this profile. 
     *
     * @return		items set in this profile
     */
    public Item[] getItems() { 
	if (specials == null)
	    return new Item[0];

   	Item[] s = new Item[specials.size()];
	specials.copyInto(s);
	return s;
    }

    /**
     * Get the names of the header-fields set in this profile. 
     *
     * @return		headers set in this profile
     */
    public String[] getHeaderNames() { 
	if (headers == null)
	    return new String[0];

   	String[] s = new String[headers.size()];
	headers.copyInto(s);
	return s;
    }
}
