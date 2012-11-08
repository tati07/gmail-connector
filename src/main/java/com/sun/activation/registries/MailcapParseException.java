/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)MailcapParseException.java	1.5 07/05/14
 */

package	com.sun.activation.registries;

/**
 *	A class to encapsulate Mailcap parsing related exceptions
 */
public class MailcapParseException extends Exception {

    public MailcapParseException() {
	super();
    }

    public MailcapParseException(String inInfo) {
	super(inInfo);
    }
}
