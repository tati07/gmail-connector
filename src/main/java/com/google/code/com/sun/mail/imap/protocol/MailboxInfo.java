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
import com.google.code.com.sun.mail.iap.Response;
import com.google.code.javax.mail.Flags;

/**
 * This class 
 *
 * @author  John Mani
 */

public class MailboxInfo { 
    public Flags availableFlags = null;
    public Flags permanentFlags = null;
    public int total = -1;
    public int recent = -1;
    public int first = -1;
    public long uidvalidity = -1;
    public long uidnext = -1;
    public int mode;

    public MailboxInfo(Response[] r) throws ParsingException {
	for (int i = 0; i < r.length; i++) {
	    if (r[i] == null || !(r[i] instanceof IMAPResponse))
		continue;

	    IMAPResponse ir = (IMAPResponse)r[i];

	    if (ir.keyEquals("EXISTS")) {
		total = ir.getNumber();
		r[i] = null; // remove this response
	    }
	    else if (ir.keyEquals("RECENT")) {
		recent = ir.getNumber();
		r[i] = null; // remove this response
	    }
	    else if (ir.keyEquals("FLAGS")) {
		availableFlags = new FLAGS(ir);
		r[i] = null; // remove this response
	    }
	    else if (ir.isUnTagged() && ir.isOK()) {
		/* should be one of:
		   	* OK [UNSEEN 12]
		   	* OK [UIDVALIDITY 3857529045]
		   	* OK [PERMANENTFLAGS (\Deleted)]
		   	* OK [UIDNEXT 44]
		*/
		ir.skipSpaces();

		if (ir.readByte() != '[') {	// huh ???
		    ir.reset();
		    continue;
		}

		boolean handled = true;
		String s = ir.readAtom();
		if (s.equalsIgnoreCase("UNSEEN"))
		    first = ir.readNumber();
		else if (s.equalsIgnoreCase("UIDVALIDITY"))
		    uidvalidity = ir.readLong();
		else if (s.equalsIgnoreCase("PERMANENTFLAGS"))
		    permanentFlags = new FLAGS(ir);
		else if (s.equalsIgnoreCase("UIDNEXT"))
		    uidnext = ir.readLong();
		else
		    handled = false;	// possibly an ALERT

		if (handled)
		    r[i] = null; // remove this response
		else
		    ir.reset();	// so ALERT can be read
	    }
	}

	/*
	 * The PERMANENTFLAGS response code is optional, and if
	 * not present implies that all flags in the required FLAGS
	 * response can be changed permanently.
	 */
	if (permanentFlags == null) {
	    if (availableFlags != null)
		permanentFlags = new Flags(availableFlags);
	    else
		permanentFlags = new Flags();
	}
    }
}
