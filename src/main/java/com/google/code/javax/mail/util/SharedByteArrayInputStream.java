/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.google.code.javax.mail.internet.SharedInputStream;

/**
 * A ByteArrayInputStream that implements the SharedInputStream interface,
 * allowing the underlying byte array to be shared between multiple readers.
 *
 * @author  Bill Shannon
 * @since JavaMail 1.4
 */

public class SharedByteArrayInputStream extends ByteArrayInputStream
				implements SharedInputStream {
    /**
     * Position within shared buffer that this stream starts at.
     */
    protected int start = 0;

    /**
     * Create a SharedByteArrayInputStream representing the entire
     * byte array.
     *
     * @param	buf	the byte array
     */
    public SharedByteArrayInputStream(byte[] buf) {
	super(buf);
    }

    /**
     * Create a SharedByteArrayInputStream representing the part
     * of the byte array from <code>offset</code> for <code>length</code>
     * bytes.
     *
     * @param	buf	the byte array
     * @param	offset	offset in byte array to first byte to include
     * @param	length	number of bytes to include
     */
    public SharedByteArrayInputStream(byte[] buf, int offset, int length) {
	super(buf, offset, length);
	start = offset;
    }

    /**
     * Return the current position in the InputStream, as an
     * offset from the beginning of the InputStream.
     *
     * @return  the current position
     */
    public long getPosition() {
	return pos - start;
    }

    /**
     * Return a new InputStream representing a subset of the data
     * from this InputStream, starting at <code>start</code> (inclusive)
     * up to <code>end</code> (exclusive).  <code>start</code> must be
     * non-negative.  If <code>end</code> is -1, the new stream ends
     * at the same place as this stream.  The returned InputStream
     * will also implement the SharedInputStream interface.
     *
     * @param	start	the starting position
     * @param	end	the ending position + 1
     * @return		the new stream
     */
    public InputStream newStream(long start, long end) {
	if (start < 0)
	    throw new IllegalArgumentException("start < 0");
	if (end == -1)
	    end = count - this.start;
	return new SharedByteArrayInputStream(buf,
				this.start + (int)start, (int)(end - start));
    }
}
