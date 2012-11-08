/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Convert lines into the canonical format, that is, terminate lines with the
 * CRLF sequence.
 * 
 * @author John Mani
 */
public class CRLFOutputStream extends FilterOutputStream {
    protected int lastb = -1;
    protected boolean atBOL = true;	// at beginning of line?
    private static final byte[] newline = { (byte)'\r', (byte)'\n' };

    public CRLFOutputStream(OutputStream os) {
	super(os);
    }

    public void write(int b) throws IOException {
	if (b == '\r') {
	    writeln();
	} else if (b == '\n') {
	    if (lastb != '\r')
		writeln();
	} else {
	    out.write(b);
	    atBOL = false;
	}
	lastb = b;
    }

    public void write(byte b[]) throws IOException {
	write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException {
	int start = off;
	
	len += off;
	for (int i = start; i < len ; i++) {
	    if (b[i] == '\r') {
		out.write(b, start, i - start);
		writeln();
		start = i + 1;
	    } else if (b[i] == '\n') {
		if (lastb != '\r') {
		    out.write(b, start, i - start);
		    writeln();
		}
		start = i + 1;
	    }
	    lastb = b[i];
	}
	if ((len - start) > 0) {
	    out.write(b, start, len - start);
	    atBOL = false;
	}
    }

    /*
     * Just write out a new line, something similar to out.println()
     */
    public void writeln() throws IOException {
        out.write(newline);
	atBOL = true;
    }
}
