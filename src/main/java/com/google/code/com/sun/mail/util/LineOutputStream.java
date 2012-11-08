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
 * This class is to support writing out Strings as a sequence of bytes
 * terminated by a CRLF sequence. The String must contain only US-ASCII
 * characters.<p>
 *
 * The expected use is to write out RFC822 style headers to an output
 * stream. <p>
 *
 * @author John Mani
 */

public class LineOutputStream extends FilterOutputStream {
    private static byte[] newline;

    static {
	newline = new byte[2];
	newline[0] = (byte)'\r';
	newline[1] = (byte)'\n';
    }

    public LineOutputStream(OutputStream out) {
	super(out);
    }

    public void writeln(String s) throws IOException {
	byte[] bytes = ASCIIUtility.getBytes(s);
	out.write(bytes);
	out.write(newline);
    }

    public void writeln() throws IOException {
	out.write(newline);
    }
}
