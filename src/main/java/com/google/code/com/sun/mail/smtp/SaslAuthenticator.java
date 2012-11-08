/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.smtp;

import com.google.code.javax.mail.MessagingException;

/**
 * Interface to make it easier to call SMTPSaslAuthenticator.
 */

public interface SaslAuthenticator {
    public boolean authenticate(String[] mechs, String realm, String authzid,
				String u, String p) throws MessagingException;

}
