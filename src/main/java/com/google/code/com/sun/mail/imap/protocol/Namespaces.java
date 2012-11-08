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

import com.google.code.com.sun.mail.iap.ProtocolException;
import com.google.code.com.sun.mail.iap.Response;

/**
 * This class and its inner class represent the response to the
 * NAMESPACE command.
 *
 * @see RFC2342
 * @author Bill Shannon
 */

public class Namespaces {

    /**
     * A single namespace entry.
     */
    public static class Namespace {
	/**
	 * Prefix string for the namespace.
	 */
	public String prefix;

	/**
	 * Delimiter between names in this namespace.
	 */
	public char delimiter;

	/**
	 * Parse a namespace element out of the response.
	 */
	public Namespace(Response r) throws ProtocolException {
	    // Namespace_Element = "(" string SP (<"> QUOTED_CHAR <"> / nil)
	    //		*(Namespace_Response_Extension) ")"
	    if (r.readByte() != '(')
		throw new ProtocolException(
					"Missing '(' at start of Namespace");
	    // first, the prefix
	    prefix = BASE64MailboxDecoder.decode(r.readString());
	    r.skipSpaces();
	    // delimiter is a quoted character or NIL
	    if (r.peekByte() == '"') {
		r.readByte();
		delimiter = (char)r.readByte();
		if (delimiter == '\\')
		    delimiter = (char)r.readByte();
		if (r.readByte() != '"')
		    throw new ProtocolException(
				    "Missing '\"' at end of QUOTED_CHAR");
	    } else {
		String s = r.readAtom();
		if (s == null)
		    throw new ProtocolException("Expected NIL, got null");
		if (!s.equalsIgnoreCase("NIL"))
		    throw new ProtocolException("Expected NIL, got " + s);
		delimiter = 0;
	    }
	    // at end of Namespace data?
	    if (r.peekByte() != ')') {
		// otherwise, must be a Namespace_Response_Extension
		//    Namespace_Response_Extension = SP string SP
		//	    "(" string *(SP string) ")"
		r.skipSpaces();
		r.readString();
		r.skipSpaces();
		r.readStringList();
	    }
	    if (r.readByte() != ')')
		throw new ProtocolException("Missing ')' at end of Namespace");
	}
    };

    /**
     * The personal namespaces.
     * May be null.
     */
    public Namespace[] personal;

    /**
     * The namespaces for other users.
     * May be null.
     */
    public Namespace[] otherUsers;

    /**
     * The shared namespace.
     * May be null.
     */
    public Namespace[] shared;

    /**
     * Parse out all the namespaces.
     */
    public Namespaces(Response r) throws ProtocolException {
	personal = getNamespaces(r);
	otherUsers = getNamespaces(r);
	shared = getNamespaces(r);
    }

    /**
     * Parse out one of the three sets of namespaces.
     */
    private Namespace[] getNamespaces(Response r) throws ProtocolException {
	r.skipSpaces();
	//    Namespace = nil / "(" 1*( Namespace_Element) ")"
	if (r.peekByte() == '(') {
	    Vector v = new Vector();
	    r.readByte();
	    do {
		Namespace ns = new Namespace(r);
		v.addElement(ns);
	    } while (r.peekByte() != ')');
	    r.readByte();
	    Namespace[] nsa = new Namespace[v.size()];
	    v.copyInto(nsa);
	    return nsa;
	} else {
	    String s = r.readAtom();
	    if (s == null)
		throw new ProtocolException("Expected NIL, got null");
	    if (!s.equalsIgnoreCase("NIL"))
		throw new ProtocolException("Expected NIL, got " + s);
	    return null;
	}
    }
}
