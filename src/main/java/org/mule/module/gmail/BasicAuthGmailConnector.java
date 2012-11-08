/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.gmail;

import com.google.code.javax.mail.Store;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class BasicAuthGmailConnector extends BaseGmailConnector {
	
	@Override
	protected Store getStore(String username) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     * Connect
     *
     * @param username A username
     * @param password A password
     * @throws ConnectionException
     */
//    @Connect
//    public void connect(@ConnectionKey String username, String password)
//        throws ConnectionException {
        /*
         * CODE FOR ESTABLISHING A CONNECTION GOES IN HERE
         */
//    }

    /**
     * Disconnect
     */
//    @Disconnect
    public void disconnect() {
        /*
         * CODE FOR CLOSING A CONNECTION GOES IN HERE
         */
    }

    /**
     * Are we connected
     */
//    @ValidateConnection
    public boolean isConnected() {
        return true;
    }

    /**
     * Are we connected
     */
//    @ConnectionIdentifier
    public String connectionId() {
        return "001";
    }


}
