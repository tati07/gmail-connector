/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.iap;

import java.io.ByteArrayInputStream;

/**
 * A simple wrapper around a byte array, with a start position and
 * count of bytes.
 *
 * @author  John Mani
 */

public class ByteArray {
    private byte[] bytes; // the byte array
    private int start;	  // start position
    private int count;	  // count of bytes

    /**
     * Constructor
     */
    public ByteArray(byte[] b, int start, int count) {
	bytes = b;
	this.start = start;
	this.count = count;
    }

    /**
     * Constructor that creates a byte array of the specified size.
     *
     * @since	JavaMail 1.4.1
     */
    public ByteArray(int size) {
	this(new byte[size], 0, size);
    }

    /**
     * Returns the internal byte array. Note that this is a live
     * reference to the actual data, not a copy.
     */
    public byte[] getBytes() {
	return bytes;
    }

    /**
     * Returns a new byte array that is a copy of the data.
     */
    public byte[] getNewBytes() {
	byte[] b = new byte[count];
	System.arraycopy(bytes, start, b, 0, count);
	return b;
    }

    /**
     * Returns the start position
     */
    public int getStart() {
	return start;
    }

    /**
     * Returns the count of bytes
     */
    public int getCount() {
	return count;
    }

    /**
     * Set the count of bytes.
     *
     * @since	JavaMail 1.4.1
     */
    public void setCount(int count) {
	this.count = count;
    }

    /**
     * Returns a ByteArrayInputStream.
     */
    public ByteArrayInputStream toByteArrayInputStream() {
	return new ByteArrayInputStream(bytes, start, count);
    }

    /**
     * Grow the byte array by incr bytes.
     *
     * @since	JavaMail 1.4.1
     */
    public void grow(int incr) {
	byte[] nbuf = new byte[bytes.length + incr];
	System.arraycopy(bytes, 0, nbuf, 0, bytes.length);
	bytes = nbuf;
    }
}
