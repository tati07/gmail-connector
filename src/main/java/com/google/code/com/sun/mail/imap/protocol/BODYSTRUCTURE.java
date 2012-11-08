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
import com.google.code.com.sun.mail.iap.Response;
import com.google.code.com.sun.mail.util.PropUtil;
import com.google.code.javax.mail.internet.ParameterList;

/**
 * A BODYSTRUCTURE response.
 *
 * @author  John Mani
 * @author  Bill Shannon
 */

public class BODYSTRUCTURE implements Item {
    
    static final char[] name =
	{'B','O','D','Y','S','T','R','U','C','T','U','R','E'};
    public int msgno;

    public String type;		// Type
    public String subtype;	// Subtype
    public String encoding;	// Encoding
    public int lines = -1;	// Size in lines
    public int size = -1;	// Size in bytes
    public String disposition;	// Disposition
    public String id;		// Content-ID
    public String description;	// Content-Description
    public String md5;		// MD-5 checksum
    public String attachment;	// Attachment name
    public ParameterList cParams; // Body parameters
    public ParameterList dParams; // Disposition parameters
    public String[] language;	// Language
    public BODYSTRUCTURE[] bodies; // array of BODYSTRUCTURE objects
				   //  for multipart & message/rfc822
    public ENVELOPE envelope;	// for message/rfc822

    private static int SINGLE	= 1;
    private static int MULTI	= 2;
    private static int NESTED	= 3;
    private int processedType;	// MULTI | SINGLE | NESTED

    // special debugging output to debug parsing errors
    private static boolean parseDebug =
	PropUtil.getBooleanSystemProperty("mail.imap.parse.debug", false);


    public BODYSTRUCTURE(FetchResponse r) throws ParsingException {
	if (parseDebug)
	    System.out.println("DEBUG IMAP: parsing BODYSTRUCTURE");
	msgno = r.getNumber();
	if (parseDebug)
	    System.out.println("DEBUG IMAP: msgno " + msgno);

	r.skipSpaces();

	if (r.readByte() != '(')
	    throw new ParsingException(
		"BODYSTRUCTURE parse error: missing ``('' at start");

	if (r.peekByte() == '(') { // multipart
	    if (parseDebug)
		System.out.println("DEBUG IMAP: parsing multipart");
	    type = "multipart";
	    processedType = MULTI;
	    Vector v = new Vector(1);
	    int i = 1;
	    do {
		v.addElement(new BODYSTRUCTURE(r));
		/*
		 * Even though the IMAP spec says there can't be any spaces
		 * between parts, some servers erroneously put a space in
		 * here.  In the spirit of "be liberal in what you accept",
		 * we skip it.
		 */
		r.skipSpaces();
	    } while (r.peekByte() == '(');

	    // setup bodies.
	    bodies = new BODYSTRUCTURE[v.size()];
	    v.copyInto(bodies);

	    subtype = r.readString(); // subtype
	    if (parseDebug)
		System.out.println("DEBUG IMAP: subtype " + subtype);

	    if (r.readByte() == ')') { // done
		if (parseDebug)
		    System.out.println("DEBUG IMAP: parse DONE");
		return;
	    }

	    // Else, we have extension data

	    if (parseDebug)
		System.out.println("DEBUG IMAP: parsing extension data");
	    // Body parameters
	    cParams = parseParameters(r);
	    if (r.readByte() == ')') { // done
		if (parseDebug)
		    System.out.println("DEBUG IMAP: body parameters DONE");
		return;
	    }
	    
	    // Disposition
	    byte b = r.readByte();
	    if (b == '(') {
		if (parseDebug)
		    System.out.println("DEBUG IMAP: parse disposition");
		disposition = r.readString();
		if (parseDebug)
		    System.out.println("DEBUG IMAP: disposition " +
							disposition);
		dParams = parseParameters(r);
		if (r.readByte() != ')') // eat the end ')'
		    throw new ParsingException(
			"BODYSTRUCTURE parse error: " +
			"missing ``)'' at end of disposition in multipart");
		if (parseDebug)
		    System.out.println("DEBUG IMAP: disposition DONE");
	    } else if (b == 'N' || b == 'n') {
		if (parseDebug)
		    System.out.println("DEBUG IMAP: disposition NIL");
		r.skip(2); // skip 'NIL'
	    } else {
		throw new ParsingException(
		    "BODYSTRUCTURE parse error: " +
		    type + "/" + subtype + ": " +
		    "bad multipart disposition, b " + b);
	    }

	    // RFC3501 allows no body-fld-lang after body-fld-disp,
	    // even though RFC2060 required it
	    if ((b = r.readByte()) == ')') {
		if (parseDebug)
		    System.out.println("DEBUG IMAP: no body-fld-lang");
		return; // done
	    }

	    if (b != ' ')
		throw new ParsingException(
		    "BODYSTRUCTURE parse error: " +
		    "missing space after disposition");

	    // Language
	    if (r.peekByte() == '(') { // a list follows
		language = r.readStringList();
		if (parseDebug)
		    System.out.println(
			"DEBUG IMAP: language len " + language.length);
	    } else {
		String l = r.readString();
		if (l != null) {
		    String[] la = { l };
		    language = la;
		    if (parseDebug)
			System.out.println("DEBUG IMAP: language " + l);
		}
	    }

	    // RFC3501 defines an optional "body location" next,
	    // but for now we ignore it along with other extensions.

	    // Throw away any further extension data
	    while (r.readByte() == ' ')
		parseBodyExtension(r);
	}
	else { // Single part
	    if (parseDebug)
		System.out.println("DEBUG IMAP: single part");
	    type = r.readString();
	    if (parseDebug)
		System.out.println("DEBUG IMAP: type " + type);
	    processedType = SINGLE;
	    subtype = r.readString();
	    if (parseDebug)
		System.out.println("DEBUG IMAP: subtype " + subtype);

	    // SIMS 4.0 returns NIL for a Content-Type of "binary", fix it here
	    if (type == null) {
		type = "application";
		subtype = "octet-stream";
	    }
	    cParams = parseParameters(r);
	    if (parseDebug)
		System.out.println("DEBUG IMAP: cParams " + cParams);
	    id = r.readString();
	    if (parseDebug)
		System.out.println("DEBUG IMAP: id " + id);
	    description = r.readString();
	    if (parseDebug)
		System.out.println("DEBUG IMAP: description " + description);
	    /*
	     * XXX - Work around bug in Exchange 2010 that
	     *       returns unquoted string.
	     */
	    encoding = r.readAtomString();
	    if (encoding != null && encoding.equalsIgnoreCase("NIL"))
		encoding = null;
	    if (parseDebug)
		System.out.println("DEBUG IMAP: encoding " + encoding);
	    size = r.readNumber();
	    if (parseDebug)
		System.out.println("DEBUG IMAP: size " + size);
	    if (size < 0)
		throw new ParsingException(
			    "BODYSTRUCTURE parse error: bad ``size'' element");

	    // "text/*" & "message/rfc822" types have additional data ..
	    if (type.equalsIgnoreCase("text")) {
		lines = r.readNumber();
		if (parseDebug)
		    System.out.println("DEBUG IMAP: lines " + lines);
		if (lines < 0)
		    throw new ParsingException(
			    "BODYSTRUCTURE parse error: bad ``lines'' element");
	    } else if (type.equalsIgnoreCase("message") &&
		     subtype.equalsIgnoreCase("rfc822")) {
		// Nested message
		processedType = NESTED;
		envelope = new ENVELOPE(r);
		BODYSTRUCTURE[] bs = { new BODYSTRUCTURE(r) };
		bodies = bs;
		lines = r.readNumber();
		if (parseDebug)
		    System.out.println("DEBUG IMAP: lines " + lines);
		if (lines < 0)
		    throw new ParsingException(
			    "BODYSTRUCTURE parse error: bad ``lines'' element");
	    } else {
		// Detect common error of including lines element on other types
		r.skipSpaces();
		byte bn = r.peekByte();
		if (Character.isDigit((char)bn)) // number
		    throw new ParsingException(
			    "BODYSTRUCTURE parse error: server erroneously " +
				"included ``lines'' element with type " +
				type + "/" + subtype);
	    }

	    if (r.peekByte() == ')') {
		r.readByte();
		if (parseDebug)
		    System.out.println("DEBUG IMAP: parse DONE");
		return; // done
	    }

	    // Optional extension data

	    // MD5
	    md5 = r.readString();
	    if (r.readByte() == ')') {
		if (parseDebug)
		    System.out.println("DEBUG IMAP: no MD5 DONE");
		return; // done
	    }
	    
	    // Disposition
	    byte b = r.readByte();
	    if (b == '(') {
		disposition = r.readString();
		if (parseDebug)
		    System.out.println("DEBUG IMAP: disposition " +
							disposition);
		dParams = parseParameters(r);
		if (parseDebug)
		    System.out.println("DEBUG IMAP: dParams " + dParams);
		if (r.readByte() != ')') // eat the end ')'
		    throw new ParsingException(
			"BODYSTRUCTURE parse error: " +
			"missing ``)'' at end of disposition");
	    } else if (b == 'N' || b == 'n') {
		if (parseDebug)
		    System.out.println("DEBUG IMAP: disposition NIL");
		r.skip(2); // skip 'NIL'
	    } else {
		throw new ParsingException(
		    "BODYSTRUCTURE parse error: " +
		    type + "/" + subtype + ": " +
		    "bad single part disposition, b " + b);
	    }

	    if (r.readByte() == ')') {
		if (parseDebug)
		    System.out.println("DEBUG IMAP: disposition DONE");
		return; // done
	    }
	    
	    // Language
	    if (r.peekByte() == '(') { // a list follows
		language = r.readStringList();
		if (parseDebug)
		    System.out.println("DEBUG IMAP: language len " +
							language.length);
	    } else { // protocol is unnessarily complex here
		String l = r.readString();
		if (l != null) {
		    String[] la = { l };
		    language = la;
		    if (parseDebug)
			System.out.println("DEBUG IMAP: language " + l);
		}
	    }

	    // RFC3501 defines an optional "body location" next,
	    // but for now we ignore it along with other extensions.

	    // Throw away any further extension data
	    while (r.readByte() == ' ')
		parseBodyExtension(r);
	    if (parseDebug)
		System.out.println("DEBUG IMAP: all DONE");
	}
    }

    public boolean isMulti() {
	return processedType == MULTI;
    }

    public boolean isSingle() {
	return processedType == SINGLE;
    }

    public boolean isNested() {
	return processedType == NESTED;
    }

    private ParameterList parseParameters(Response r)
			throws ParsingException {
	r.skipSpaces();

	ParameterList list = null;
	byte b = r.readByte();
	if (b == '(') {
	    list = new ParameterList();
	    do {
		String name = r.readString();
		if (parseDebug)
		    System.out.println("DEBUG IMAP: parameter name " + name);
		if (name == null)
		    throw new ParsingException(
			"BODYSTRUCTURE parse error: " +
			type + "/" + subtype + ": " +
			"null name in parameter list");
		String value = r.readString();
		if (parseDebug)
		    System.out.println("DEBUG IMAP: parameter value " + value);
		list.set(name, value);
	    } while (r.readByte() != ')');
	    list.set(null, "DONE");	// XXX - hack
	} else if (b == 'N' || b == 'n') {
	    if (parseDebug)
		System.out.println("DEBUG IMAP: parameter list NIL");
	    r.skip(2);
	} else
	    throw new ParsingException("Parameter list parse error");

	return list;
    }

    private void parseBodyExtension(Response r) throws ParsingException {
	r.skipSpaces();

	byte b = r.peekByte();
	if (b == '(') {
	    r.skip(1); // skip '('
	    do {
		parseBodyExtension(r);
	    } while (r.readByte() != ')');
	} else if (Character.isDigit((char)b)) // number
	    r.readNumber();
	else // nstring
	    r.readString();
    }
}
