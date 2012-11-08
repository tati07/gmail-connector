/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap;

import com.google.code.javax.mail.Session;
import com.google.code.javax.mail.URLName;



/**
 * This class provides access to an IMAP message store over SSL. <p>
 */

public class IMAPSSLStore extends IMAPStore {
    
    /**
     * Constructor that takes a Session object and a URLName that
     * represents a specific IMAP server.
     */
    public IMAPSSLStore(Session session, URLName url) {
	super(session, url, "imaps", true); // call super constructor
    }
}
