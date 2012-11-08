/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail;

import java.util.NoSuchElementException;

/**
 * The <code>UIDFolder</code> interface is implemented by Folders 
 * that can support the "disconnected" mode of operation, by providing 
 * unique-ids for messages in the folder. This interface is based on
 * the IMAP model for supporting disconnected operation. <p>
 *
 * A Unique identifier (UID) is a positive long value, assigned to
 * each message in a specific folder. Unique identifiers are assigned
 * in a strictly <strong>ascending</strong> fashion in the mailbox. 
 * That is, as each message is added to the mailbox it is assigned a 
 * higher UID than the message(s) which were added previously. Unique
 * identifiers persist across sessions. This permits a client to 
 * resynchronize its state from a previous session with the server. <p>
 *
 * Associated with every mailbox is a unique identifier validity value.
 * If unique identifiers from an earlier session fail to persist to 
 * this session, the unique identifier validity value 
 * <strong>must</strong> be greater than the one used in the earlier
 * session. <p>
 *
 * Refer to RFC 2060 <A HREF="http://www.ietf.org/rfc/rfc2060.txt">
 * http://www.ietf.org/rfc/rfc2060.txt</A> for more information.
 *
 * @author John Mani
 */

public interface UIDFolder {

    /**
     * A fetch profile item for fetching UIDs.
     * This inner class extends the <code>FetchProfile.Item</code>
     * class to add new FetchProfile item types, specific to UIDFolders.
     * The only item currently defined here is the <code>UID</code> item.
     *
     * @see FetchProfile
     */
    public static class FetchProfileItem extends FetchProfile.Item {
	protected FetchProfileItem(String name) {
	    super(name);
	}

	/**
	 * UID is a fetch profile item that can be included in a
	 * <code>FetchProfile</code> during a fetch request to a GmailFolder.
	 * This item indicates that the UIDs for messages in the specified 
	 * range are desired to be prefetched. <p>
	 * 
	 * An example of how a client uses this is below: <p>
	 * <blockquote><pre>
	 *
	 * 	FetchProfile fp = new FetchProfile();
	 *	fp.add(UIDFolder.FetchProfileItem.UID);
	 *	folder.fetch(msgs, fp);
	 *
	 * </pre></blockquote><p>
	 */ 
	public static final FetchProfileItem UID = 
		new FetchProfileItem("UID");
    }

    /**
     * This is a special value that can be used as the <code>end</code>
     * parameter in <code>getMessagesByUID(start, end)</code>, to denote the
     * UID of the last message in the folder.
     *
     * @see #getMessagesByUID
     */ 
    public final static long LASTUID = -1;

    /**
     * Returns the UIDValidity value associated with this folder. <p>
     * 
     * Clients typically compare this value against a UIDValidity
     * value saved from a previous session to insure that any cached 
     * UIDs are not stale.
     *
     * @return UIDValidity
     */
    public long getUIDValidity() throws MessagingException;

    /**
     * Get the Message corresponding to the given UID. If no such 
     * message exists, <code>null</code> is returned.
     *
     * @param uid	UID for the desired message
     * @return		the Message object. <code>null</code> is returned
     *			if no message corresponding to this UID is obtained.
     * @exception	MessagingException
     */
    public Message getMessageByUID(long uid) throws MessagingException;

    /**
     * Get the Messages specified by the given range. The special
     * value LASTUID can be used for the <code>end</code> parameter
     * to indicate the UID of the last message in the folder. <p>
     *
     * Note that <code>end</code> need not be greater than <code>start</code>;
     * the order of the range doesn't matter.
     * Note also that, unless the folder is empty, use of LASTUID ensures
     * that at least one message will be returned - the last message in the
     * folder.
     *
     * @param start	start UID
     * @param end	end UID
     * @return		array of Message objects
     * @exception	MessagingException
     * @see 		#LASTUID
     */
    public Message[] getMessagesByUID(long start, long end)
				throws MessagingException;

    /**
     * Get the Messages specified by the given array of UIDs. If any UID is 
     * invalid, <code>null</code> is returned for that entry. <p>
     *
     * Note that the returned array will be of the same size as the specified
     * array of UIDs, and <code>null</code> entries may be present in the
     * array to indicate invalid UIDs.
     *
     * @param uids	array of UIDs
     * @return		array of Message objects
     * @exception	MessagingException
     */
    public Message[] getMessagesByUID(long[] uids) 
				throws MessagingException;

    /**
     * Get the UID for the specified message. Note that the message
     * <strong>must</strong> belong to this folder. Otherwise
     * java.util.NoSuchElementException is thrown.
     *
     * @param message	Message from this folder
     * @return		UID for this message
     * @exception	NoSuchElementException if the given Message
     *			is not in this GmailFolder.
     */
    public long getUID(Message message) throws MessagingException;
}
