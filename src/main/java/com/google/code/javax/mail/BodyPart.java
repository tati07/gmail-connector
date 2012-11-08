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
 * This class models a Part that is contained within a Multipart.
 * This is an abstract class. Subclasses provide actual implementations.<p>
 *
 * BodyPart implements the Part interface. Thus, it contains a set of
 * attributes and a "content".
 *
 * @author John Mani
 * @author Bill Shannon
 */

public abstract class BodyPart implements Part {

    /**
     * The <code>Multipart</code> object containing this <code>BodyPart</code>,
     * if known.
     * @since	JavaMail 1.1
     */
    protected Multipart parent;

    /**
     * Return the containing <code>Multipart</code> object,
     * or <code>null</code> if not known.
     */
    public Multipart getParent() {
	return parent;
    }

    /**
     * Set the parent of this <code>BodyPart</code> to be the specified
     * <code>Multipart</code>.  Normally called by <code>Multipart</code>'s
     * <code>addBodyPart</code> method.  <code>parent</code> may be
     * <code>null</code> if the <code>BodyPart</code> is being removed
     * from its containing <code>Multipart</code>.
     * @since	JavaMail 1.1
     */
    void setParent(Multipart parent) {
	this.parent = parent;
    }
}
