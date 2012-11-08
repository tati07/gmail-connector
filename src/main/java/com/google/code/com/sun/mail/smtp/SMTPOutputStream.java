/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.smtp;

import java.io.IOException;
import java.io.OutputStream;

import com.google.code.com.sun.mail.util.CRLFOutputStream;

/**
 * In addition to converting lines into the canonical format,
 * i.e., terminating lines with the CRLF sequence, escapes the "."
 * by adding another "." to any "." that appears in the beginning
 * of a line.  See RFC821 section 4.5.2.
 * 
 * @author Max Spivak
 * @see CRLFOutputStream
 */
public class SMTPOutputStream extends CRLFOutputStream {
    public SMTPOutputStream(OutputStream os) {
	super(os);
    }

    public void write(int b) throws IOException {
	// if that last character was a newline, and the current
	// character is ".", we always write out an extra ".".
	if ((lastb == '\n' || lastb == '\r' || lastb == -1) && b == '.') {
	    out.write('.');
	}
	
	super.write(b);
    }

    /* 
     * This method has been added to improve performance.
     */
    public void write(byte b[], int off, int len) throws IOException {
	int lastc = (lastb == -1) ? '\n' : lastb;
	int start = off;
	
	len += off;
	for (int i = off; i < len; i++) {
	    if ((lastc == '\n' || lastc == '\r') && b[i] == '.') {
		super.write(b, start, i - start);
		out.write('.');
		start = i;
	    }
	    lastc = b[i];
	}
	if ((len - start) > 0)
	    super.write(b, start, len - start);
    }

    /**
     * Override flush method in FilterOutputStream.
     *
     * The MimeMessage writeTo method flushes its buffer at the end,
     * but we don't want to flush data out to the socket until we've
     * also written the terminating "\r\n.\r\n".
     *
     * We buffer nothing so there's nothing to flush.  We depend
     * on the fact that CRLFOutputStream also buffers nothing.
     * SMTPTransport will manually flush the socket before reading
     * the response.
     */
    public void flush() {
	// do nothing
    }

    /**
     * Ensure we're at the beginning of a line.
     * Write CRLF if not.
     */
    public void ensureAtBOL() throws IOException {
	if (!atBOL)
	    super.writeln();
    }
}
