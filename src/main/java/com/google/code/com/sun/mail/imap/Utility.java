/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap;

import java.util.Vector;

import com.google.code.com.sun.mail.imap.protocol.MessageSet;
import com.google.code.com.sun.mail.imap.protocol.UIDSet;
import com.google.code.javax.mail.Message;

/**
 * Holder for some static utility methods.
 *
 * @author  John Mani
 * @author  Bill Shannon
 */

public final class Utility {

    // Cannot be initialized
    private Utility() { }

    /**
     * Run thru the given array of messages, apply the given
     * Condition on each message and generate sets of contiguous 
     * sequence-numbers for the successful messages. If a message 
     * in the given array is found to be expunged, it is ignored.
     *
     * ASSERT: Since this method uses and returns message sequence
     * numbers, you should use this method only when holding the
     * messageCacheLock.
     */
    public static 
    MessageSet[] toMessageSet(Message[] msgs, Condition cond) {
	Vector v = new Vector(1);
	int current, next;

	IMAPMessage msg;
	for (int i = 0; i < msgs.length; i++) {
	    msg = (IMAPMessage)msgs[i];
	    if (msg.isExpunged()) // expunged message, skip it
		continue;

	    current = msg.getSequenceNumber();
	    // Apply the condition. If it fails, skip it.
	    if ((cond != null) && !cond.test(msg))
		continue;
	    
	    MessageSet set = new MessageSet();
	    set.start = current;

	    // Look for contiguous sequence numbers
	    for (++i; i < msgs.length; i++) {
		// get next message
		msg = (IMAPMessage)msgs[i];

		if (msg.isExpunged()) // expunged message, skip it
		    continue;
		next = msg.getSequenceNumber();

		// Does this message match our condition ?
		if ((cond != null) && !cond.test(msg))
		    continue;
		
		if (next == current+1)
		    current = next;
		else { // break in sequence
		    // We need to reexamine this message at the top of
		    // the outer loop, so decrement 'i' to cancel the
		    // outer loop's autoincrement 
		    i--;
		    break;
		}
	    }
	    set.end = current;
	    v.addElement(set);
	}
	
	if (v.isEmpty()) // No valid messages
	    return null;
	else {
	    MessageSet[] sets = new MessageSet[v.size()];
	    v.copyInto(sets);
	    return sets;
	}
    }

    /**
     * Return UIDSets for the messages.  Note that the UIDs
     * must have already been fetched for the messages.
     */
    public static UIDSet[] toUIDSet(Message[] msgs) {
	Vector v = new Vector(1);
	long current, next;

	IMAPMessage msg;
	for (int i = 0; i < msgs.length; i++) {
	    msg = (IMAPMessage)msgs[i];
	    if (msg.isExpunged()) // expunged message, skip it
		continue;

	    current = msg.getUID();
 
	    UIDSet set = new UIDSet();
	    set.start = current;

	    // Look for contiguous UIDs
	    for (++i; i < msgs.length; i++) {
		// get next message
		msg = (IMAPMessage)msgs[i];

		if (msg.isExpunged()) // expunged message, skip it
		    continue;
		next = msg.getUID();

		if (next == current+1)
		    current = next;
		else { // break in sequence
		    // We need to reexamine this message at the top of
		    // the outer loop, so decrement 'i' to cancel the
		    // outer loop's autoincrement 
		    i--;
		    break;
		}
	    }
	    set.end = current;
	    v.addElement(set);
	}

	if (v.isEmpty()) // No valid messages
	    return null;
	else {
	    UIDSet[] sets = new UIDSet[v.size()];
	    v.copyInto(sets);
	    return sets;
	}
    }

    /**
     * This interface defines the test to be executed in 
     * <code>toMessageSet()</code>. 
     */
    public static interface Condition {
	public boolean test(IMAPMessage message);
    }
}
