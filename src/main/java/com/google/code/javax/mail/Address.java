/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail;

import java.io.Serializable;

/**
 * This abstract class models the addresses in a message.
 * Subclasses provide specific implementations.  Subclasses
 * will typically be serializable so that (for example) the
 * use of Address objects in search terms can be serialized
 * along with the search terms.
 *
 * @author John Mani
 * @author Bill Shannon
 */

public abstract class Address implements Serializable {

    private static final long serialVersionUID = -5822459626751992278L;

    /**
     * Return a type string that identifies this address type.
     *
     * @return	address type
     * @see	javax.mail.internet.InternetAddress
     */
    public abstract String getType();

    /**
     * Return a String representation of this address object.
     *
     * @return	string representation of this address
     */
    public abstract String toString();

    /**
     * The equality operator.  Subclasses should provide an
     * implementation of this method that supports value equality
     * (do the two Address objects represent the same destination?),
     * not object reference equality.  A subclass must also provide
     * a corresponding implementation of the <code>hashCode</code>
     * method that preserves the general contract of
     * <code>equals</code> and <code>hashCode</code> - objects that
     * compare as equal must have the same hashCode.
     *
     * @param	address	Address object
     */
    public abstract boolean equals(Object address);
}
