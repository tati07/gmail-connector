/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)CommandInfo.java	1.12 07/05/14
 */

package com.google.code.javax.activation;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * The CommandInfo class is used by CommandMap implementations to
 * describe the results of command requests. It provides the requestor
 * with both the verb requested, as well as an instance of the
 * bean. There is also a method that will return the name of the
 * class that implements the command but <i>it is not guaranteed to
 * return a valid value</i>. The reason for this is to allow CommandMap
 * implmentations that subclass CommandInfo to provide special
 * behavior. For example a CommandMap could dynamically generate
 * JavaBeans. In this case, it might not be possible to create an
 * object with all the correct state information solely from the class
 * name.
 */

public class CommandInfo {
    private String verb;
    private String className;

    /**
     * The Constructor for CommandInfo.
     * @param verb The command verb this CommandInfo decribes.
     * @param className The command's fully qualified class name.
     */
    public CommandInfo(String verb, String className) {
	this.verb = verb;
	this.className = className;
    }

    /**
     * Return the command verb.
     *
     * @return the command verb.
     */
    public String getCommandName() {
	return verb;
    }

    /**
     * Return the command's class name. <i>This method MAY return null in
     * cases where a CommandMap subclassed CommandInfo for its
     * own purposes.</i> In other words, it might not be possible to
     * create the correct state in the command by merely knowing
     * its class name. <b>DO NOT DEPEND ON THIS METHOD RETURNING
     * A VALID VALUE!</b>
     *
     * @return The class name of the command, or <i>null</i>
     */
    public String getCommandClass() {
	return className;
    }

    /**
     * Return the instantiated JavaBean component.
     * <p>
     * Begin by instantiating the component with
     * <code>Beans.instantiate()</code>.
     * <p>
     * If the bean implements the <code>javax.activation.CommandObject</code>
     * interface, call its <code>setCommandContext</code> method.
     * <p>
     * If the DataHandler parameter is null, then the bean is
     * instantiated with no data. NOTE: this may be useful
     * if for some reason the DataHandler that is passed in
     * throws IOExceptions when this method attempts to
     * access its InputStream. It will allow the caller to
     * retrieve a reference to the bean if it can be
     * instantiated.
     * <p>
     * If the bean does NOT implement the CommandObject interface,
     * this method will check if it implements the
     * java.io.Externalizable interface. If it does, the bean's
     * readExternal method will be called if an InputStream
     * can be acquired from the DataHandler.<p>
     *
     * @param dh	The DataHandler that describes the data to be
     *			passed to the command.
     * @param loader	The ClassLoader to be used to instantiate the bean.
     * @return The bean
     * @see java.beans.Beans#instantiate
     * @see javax.activation.CommandObject
     */
    public Object getCommandObject(DataHandler dh, ClassLoader loader)
			throws IOException, ClassNotFoundException {
	Object new_bean = null;

	// try to instantiate the bean
	new_bean = java.beans.Beans.instantiate(loader, className);

	// if we got one and it is a CommandObject
	if (new_bean != null) {
	    if (new_bean instanceof CommandObject) {
		((CommandObject)new_bean).setCommandContext(verb, dh);
	    } else if (new_bean instanceof Externalizable) {
		if (dh != null) {
		    InputStream is = dh.getInputStream();
		    if (is != null) {
			((Externalizable)new_bean).readExternal(
					       new ObjectInputStream(is));
		    }
		}
	    }
	}

	return new_bean;
    }
}
