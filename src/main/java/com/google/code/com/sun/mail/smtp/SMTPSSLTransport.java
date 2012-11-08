/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.smtp;

import com.google.code.javax.mail.Session;
import com.google.code.javax.mail.URLName;

/**
 * This class implements the Transport abstract class using SMTP
 * over SSL for message submission and transport.
 *
 * @author Bill Shannon
 */

public class SMTPSSLTransport extends SMTPTransport {

    /** Constructor */
    public SMTPSSLTransport(Session session, URLName urlname) {
	super(session, urlname, "smtps", true);
    }
}
