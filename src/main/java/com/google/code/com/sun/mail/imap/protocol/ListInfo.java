/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap.protocol;

import java.util.Vector;

import com.google.code.com.sun.mail.iap.ParsingException;

/**
 * A LIST response.
 *
 * @author  John Mani
 * @author  Bill Shannon
 */

public class ListInfo { 
    public String name = null;
    public char separator = '/';
    public boolean hasInferiors = true;
    public boolean canOpen = true;
    public int changeState = INDETERMINATE;
    public String[] attrs;

    public static final int CHANGED		= 1;
    public static final int UNCHANGED		= 2;
    public static final int INDETERMINATE	= 3;

    public ListInfo(IMAPResponse r) throws ParsingException {
	String[] s = r.readSimpleList();

	Vector v = new Vector();	// accumulate attributes
	if (s != null) {
	    // non-empty attribute list
	    for (int i = 0; i < s.length; i++) {
		if (s[i].equalsIgnoreCase("\\Marked"))
		    changeState = CHANGED;
		else if (s[i].equalsIgnoreCase("\\Unmarked"))
		    changeState = UNCHANGED;
		else if (s[i].equalsIgnoreCase("\\Noselect"))
		    canOpen = false;
		else if (s[i].equalsIgnoreCase("\\Noinferiors"))
		    hasInferiors = false;
		v.addElement(s[i]);
	    }
	}
	attrs = new String[v.size()];
	v.copyInto(attrs);

	r.skipSpaces();
	if (r.readByte() == '"') {
	    if ((separator = (char)r.readByte()) == '\\')
		// escaped separator character
		separator = (char)r.readByte();	
	    r.skip(1); // skip <">
	} else // NIL
	    r.skip(2);
	
	r.skipSpaces();
	name = r.readAtomString();

	// decode the name (using RFC2060's modified UTF7)
	name = BASE64MailboxDecoder.decode(name);
    }
}
