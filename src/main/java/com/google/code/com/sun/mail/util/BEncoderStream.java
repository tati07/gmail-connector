/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.util;

import java.io.OutputStream;

/**
 * This class implements a 'B' Encoder as defined by RFC2047 for
 * encoding MIME headers. It subclasses the BASE64EncoderStream
 * class.
 * 
 * @author John Mani
 */

public class BEncoderStream extends BASE64EncoderStream {

    /**
     * Create a 'B' encoder that encodes the specified input stream.
     * @param out        the output stream
     */
    public BEncoderStream(OutputStream out) {
	super(out, Integer.MAX_VALUE); // MAX_VALUE is 2^31, should
				       // suffice (!) to indicate that
				       // CRLFs should not be inserted
    }

    /**
     * Returns the length of the encoded version of this byte array.
     */
    public static int encodedLength(byte[] b) {
        return ((b.length + 2)/3) * 4;
    }
}
