/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail;

/**
 * This exception is thrown when a method is invoked on a Messaging object
 * and the Store that owns that object has died due to some reason.
 * This exception should be treated as a fatal error; in particular any
 * messaging object belonging to that Store must be considered invalid. <p>
 *
 * The connect method may be invoked on the dead Store object to 
 * revive it. <p>
 *
 * The getMessage() method returns more detailed information about the
 * error that caused this exception. <p>
 *
 * @author John Mani
 */

public class StoreClosedException extends MessagingException {
    transient private Store store;

    private static final long serialVersionUID = -3145392336120082655L;

    /**
     * Constructor
     * @param store	The dead Store object
     */
    public StoreClosedException(Store store) {
	this(store, null);
    }

    /**
     * Constructor
     * @param store	The dead Store object
     * @param message	The detailed error message
     */
    public StoreClosedException(Store store, String message) {
	super(message);
	this.store = store;
    }

    /**
     * Returns the dead Store object 
     */
    public Store getStore() {
	return store;
    }
}
