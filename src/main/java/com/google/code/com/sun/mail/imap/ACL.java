/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap;


/**
 * An access control list entry for a particular authentication identifier
 * (user or group).  Associates a set of Rights with the identifier.
 * See RFC 2086.
 * <p>
 *
 * @author Bill Shannon
 */

public class ACL implements Cloneable {

    private String name;
    private Rights rights;

    /**
     * Construct an ACL entry for the given identifier and with no rights.
     *
     * @param	name	the identifier name
     */
    public ACL(String name) {
	this.name = name;
	this.rights = new Rights();
    }

    /**
     * Construct an ACL entry for the given identifier with the given rights.
     *
     * @param	name	the identifier name
     * @param	rights	the rights
     */
    public ACL(String name, Rights rights) {
	this.name = name;
	this.rights = rights;
    }

    /**
     * Get the identifier name for this ACL entry.
     *
     * @return	the identifier name
     */
    public String getName() {
	return name;
    }

    /**
     * Set the rights associated with this ACL entry.
     *
     * @param	rights	the rights
     */
    public void setRights(Rights rights) {
	this.rights = rights;
    }

    /**
     * Get the rights associated with this ACL entry.
     * Returns the actual Rights object referenced by this ACL;
     * modifications to the Rights object will effect this ACL.
     *
     * @return	the rights
     */
    public Rights getRights() {
	return rights;
    }

    /**
     * Clone this ACL entry.
     */
    public Object clone() throws CloneNotSupportedException {
	ACL acl = (ACL)super.clone();
	acl.rights = (Rights)this.rights.clone();
	return acl;
    }
}
