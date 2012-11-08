/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap.protocol;

import java.io.IOException;
import java.util.Vector;

import com.google.code.com.sun.mail.iap.Protocol;
import com.google.code.com.sun.mail.iap.ProtocolException;
import com.google.code.com.sun.mail.iap.Response;
import com.google.code.com.sun.mail.util.ASCIIUtility;

/**
 * This class represents a response obtained from the input stream
 * of an IMAP server.
 *
 * @author  John Mani
 */

public class IMAPResponse extends Response {
    private String key;
    private int number;

    public IMAPResponse(Protocol c) throws IOException, ProtocolException {
	super(c);

	// continue parsing if this is an untagged response
	if (isUnTagged() && !isOK() && !isNO() && !isBAD() && !isBYE()) {
	    key = readAtom();

	    // Is this response of the form "* <number> <command>"
	    try {
		number = Integer.parseInt(key);
		key = readAtom();
	    } catch (NumberFormatException ne) { }
	}
    }

    /**
     * Copy constructor.
     */
    public IMAPResponse(IMAPResponse r) {
	super((Response)r);
	key = r.key;
	number = r.number;
    }

    /**
     * Read a list of space-separated "flag_extension" sequences and 
     * return the list as a array of Strings. An empty list is returned
     * as null.  This is an IMAP-ism, and perhaps this method should 
     * moved into the IMAP layer.
     */
    public String[] readSimpleList() {
	skipSpaces();

	if (buffer[index] != '(') // not what we expected
	    return null;
	index++; // skip '('

	Vector v = new Vector();
	int start;
	for (start = index; buffer[index] != ')'; index++) {
	    if (buffer[index] == ' ') { // got one item
		v.addElement(ASCIIUtility.toString(buffer, start, index));
		start = index+1; // index gets incremented at the top
	    }
	}
	if (index > start) // get the last item
	    v.addElement(ASCIIUtility.toString(buffer, start, index));
	index++; // skip ')'
	
	int size = v.size();
	if (size > 0) {
	    String[] s = new String[size];
	    v.copyInto(s);
	    return s;
	} else  // empty list
	    return null;
    }
    
    public String[] readAtomStringList() {
	skipSpaces();

	if (buffer[index] != '(') // not what we expected
	    return null;
	index++; // skip '('

	Vector v = new Vector();
        String as;
	do {
            as = readAtomString();
            if(as.length() > 0){
                v.addElement(as);                
            }
	} while (buffer[index++] != ')');

	int size = v.size();
	if (size > 0) {
	    String[] s = new String[size];
	    v.copyInto(s);
	    return s;
	} else  // empty list
	    return null;    
    }

    public String getKey() {
	return key;
    }

    public boolean keyEquals(String k) {
	if (key != null && key.equalsIgnoreCase(k))
	    return true;
	else
	    return false;
    }

    public int getNumber() {
	return number;
    }

    public static IMAPResponse readResponse(Protocol p) 
			throws IOException, ProtocolException {
	IMAPResponse r = new IMAPResponse(p);
	if (r.keyEquals("FETCH"))
	    r = new FetchResponse(r);
	return r;
    }
}
