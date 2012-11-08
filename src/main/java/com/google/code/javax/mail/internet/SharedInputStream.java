/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.internet;

import java.io.InputStream;

/**
 * An InputStream that is backed by data that can be shared by multiple
 * readers may implement this interface.  This allows users of such an
 * InputStream to determine the current position in the InputStream, and
 * to create new InputStreams representing a subset of the data in the
 * original InputStream.  The new InputStream will access the same
 * underlying data as the original, without copying the data. <p>
 *
 * Note that implementations of this interface must ensure that the
 * <code>close</code> method does not close any underlying stream
 * that might be shared by multiple instances of <code>SharedInputStream</code>
 * until all shared instances have been closed.
 *
 * @author  Bill Shannon
 * @since JavaMail 1.2
 */

public interface SharedInputStream {
    /**
     * Return the current position in the InputStream, as an
     * offset from the beginning of the InputStream.
     *
     * @return	the current position
     */
    public long getPosition();

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
    public InputStream newStream(long start, long end);
}
