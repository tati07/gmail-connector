/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.util;

import java.util.Properties;

import com.google.code.javax.mail.Session;

/**
 * Utilities to make it easier to get property values.
 * Properties can be strings or type-specific value objects.
 *
 * @author Bill Shannon
 */
public class PropUtil {

    // No one should instantiate this class.
    private PropUtil() {
    }

    /**
     * Get an integer valued property.
     */
    public static int getIntProperty(Properties props, String name, int def) {
	return getInt(getProp(props, name), def);
    }

    /**
     * Get a boolean valued property.
     */
    public static boolean getBooleanProperty(Properties props,
				String name, boolean def) {
	return getBoolean(getProp(props, name), def);
    }

    /**
     * Get an integer valued property.
     */
    public static int getIntSessionProperty(Session session,
				String name, int def) {
	return getInt(getProp(session.getProperties(), name), def);
    }

    /**
     * Get a boolean valued property.
     */
    public static boolean getBooleanSessionProperty(Session session,
				String name, boolean def) {
	return getBoolean(getProp(session.getProperties(), name), def);
    }

    /**
     * Get a boolean valued System property.
     */
    public static boolean getBooleanSystemProperty(String name, boolean def) {
	try {
	    return getBoolean(getProp(System.getProperties(), name), def);
	} catch (SecurityException sex) {
	    // fall through...
	}

	/*
	 * If we can't get the entire System Properties object because
	 * of a SecurityException, just ask for the specific property.
	 */
	try {
	    String value = System.getProperty(name);
	    if (value == null)
		return def;
	    if (def)
		return !value.equalsIgnoreCase("false");
	    else
		return value.equalsIgnoreCase("true");
	} catch (SecurityException sex) {
	    return def;
	}
    }

    /**
     * Get the value of the specified property.
     * If the "get" method returns null, use the getProperty method,
     * which might cascade to a default Properties object.
     */
    private static Object getProp(Properties props, String name) {
	Object val = props.get(name);
	if (val != null)
	    return val;
	else
	    return props.getProperty(name);
    }

    /**
     * Interpret the value object as an integer,
     * returning def if unable.
     */
    private static int getInt(Object value, int def) {
	if (value == null)
	    return def;
	if (value instanceof String) {
	    try {
		return Integer.parseInt((String)value);
	    } catch (NumberFormatException nfex) { }
	}
	if (value instanceof Integer)
	    return ((Integer)value).intValue();
	return def;
    }

    /**
     * Interpret the value object as a boolean,
     * returning def if unable.
     */
    private static boolean getBoolean(Object value, boolean def) {
	if (value == null)
	    return def;
	if (value instanceof String) {
	    /*
	     * If the default is true, only "false" turns it off.
	     * If the default is false, only "true" turns it on.
	     */
	    if (def)
		return !((String)value).equalsIgnoreCase("false");
	    else
		return ((String)value).equalsIgnoreCase("true");
	}
	if (value instanceof Boolean)
	    return ((Boolean)value).booleanValue();
	return def;
    }
}
