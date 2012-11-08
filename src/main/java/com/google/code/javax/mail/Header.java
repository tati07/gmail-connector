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
 * The Header class stores a name/value pair to represent headers.
 *
 * @author John Mani
 */

public class Header {

    /**
     * The name of the header.
     *
     * @since	JavaMail 1.4
     */
    protected String name;

    /**
     * The value of the header.
     *
     * @since	JavaMail 1.4
     */
    protected String value;

    /**
     * Construct a Header object.
     *
     * @param name	name of the header
     * @param value	value of the header
     */
    public Header(String name, String value) {
	this.name = name;
	this.value = value;
    }

    /**
     * Returns the name of this header.
     *
     * @return 		name of the header
     */
    public String getName() {
	return name;
    }

    /**
     * Returns the value of this header.
     *
     * @return 		value of the header
     */
    public String getValue() {
	return value;
    }
}
