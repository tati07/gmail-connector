/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.pop3;

import com.google.code.javax.mail.Session;
import com.google.code.javax.mail.URLName;

/**
 * A POP3 Message Store using SSL.  Contains only one folder, "INBOX".
 *
 * @author      Bill Shannon
 */
public class POP3SSLStore extends POP3Store {

    public POP3SSLStore(Session session, URLName url) {
	super(session, url, "pop3s", true);
    }
}
