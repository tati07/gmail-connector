/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap.protocol;

import com.google.code.com.sun.mail.iap.ParsingException;
import com.google.code.javax.mail.Flags;

/**
 * This class 
 *
 * @author  John Mani
 */

public class FLAGS extends Flags implements Item {

    // IMAP item name
    static final char[] name = {'F','L','A','G','S'};
    public int msgno;

    private static final long serialVersionUID = 439049847053756670L;

    /**
     * Constructor
     */
    public FLAGS(IMAPResponse r) throws ParsingException {
	msgno = r.getNumber();

	r.skipSpaces();
	String[] flags = r.readSimpleList();
	if (flags != null) { // if not empty flaglist
	    for (int i = 0; i < flags.length; i++) {
		String s = flags[i];
		if (s.length() >= 2 && s.charAt(0) == '\\') {
		    switch (Character.toUpperCase(s.charAt(1))) {
		    case 'S': // \Seen
			add(Flags.Flag.SEEN);
			break;
		    case 'R': // \Recent
			add(Flags.Flag.RECENT);
			break;
		    case 'D':
			if (s.length() >= 3) {
			    char c = s.charAt(2);
			    if (c == 'e' || c == 'E') // \Deleted
				add(Flags.Flag.DELETED);
			    else if (c == 'r' || c == 'R') // \Draft
				add(Flags.Flag.DRAFT);
			} else
			    add(s);	// unknown, treat it as a user flag
			break;
		    case 'A': // \Answered
			add(Flags.Flag.ANSWERED);
			break;
		    case 'F': // \Flagged
			add(Flags.Flag.FLAGGED);
			break;
		    case '*': // \*
			add(Flags.Flag.USER);
			break;
		    default:
			add(s);		// unknown, treat it as a user flag
			break;
		    }
		} else
		    add(s);
	    }
	}
    }
}
