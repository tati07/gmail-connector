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
 * The class PasswordAuthentication is a data holder that is used by
 * Authenticator.  It is simply a repository for a user name and a password.
 *
 * @see java.net.PasswordAuthentication
 * @see javax.mail.Authenticator
 * @see javax.mail.Authenticator#getPasswordAuthentication()
 *
 * @author  Bill Foote
 */

public final class PasswordAuthentication {

    private String userName;
    private String password;

    /** 
     * Initialize a new PasswordAuthentication
     * @param userName the user name
     * @param password The user's password
     */
    public PasswordAuthentication(String userName, String password) {
	this.userName = userName;
	this.password = password;
    }

    /**
     * @return the user name
     */
    public String getUserName() {
	return userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }
}
