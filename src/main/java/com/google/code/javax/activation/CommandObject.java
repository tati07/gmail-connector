/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)CommandObject.java	1.10 07/05/14
 */

package com.google.code.javax.activation;

import java.io.IOException;

/**
 * JavaBeans components that are Activation Framework aware implement
 * this interface to find out which command verb they're being asked
 * to perform, and to obtain the DataHandler representing the
 * data they should operate on.  JavaBeans that don't implement
 * this interface may be used as well.  Such commands may obtain
 * the data using the Externalizable interface, or using an
 * application-specific method.<p>
 */
public interface CommandObject {

    /**
     * Initialize the Command with the verb it is requested to handle
     * and the DataHandler that describes the data it will
     * operate on. <b>NOTE:</b> it is acceptable for the caller
     * to pass <i>null</i> as the value for <code>DataHandler</code>.
     *
     * @param verb The Command Verb this object refers to.
     * @param dh The DataHandler.
     */
    public void setCommandContext(String verb, DataHandler dh)
						throws IOException;
}
