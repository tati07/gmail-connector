/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)SecuritySupport.java	1.4 07/05/14
 */

package com.google.code.javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Security related methods that only work on J2SE 1.2 and newer.
 */
class SecuritySupport {

    private SecuritySupport() {
	// private constructor, can't create an instance
    }

    public static ClassLoader getContextClassLoader() {
	return (ClassLoader)
		AccessController.doPrivileged(new PrivilegedAction() {
	    public Object run() {
		ClassLoader cl = null;
		try {
		    cl = Thread.currentThread().getContextClassLoader();
		} catch (SecurityException ex) { }
		return cl;
	    }
	});
    }

    public static InputStream getResourceAsStream(final Class c,
				final String name) throws IOException {
	try {
	    return (InputStream)
		AccessController.doPrivileged(new PrivilegedExceptionAction() {
		    public Object run() throws IOException {
			return c.getResourceAsStream(name);
		    }
		});
	} catch (PrivilegedActionException e) {
	    throw (IOException)e.getException();
	}
    }

    public static URL[] getResources(final ClassLoader cl, final String name) {
	return (URL[])
		AccessController.doPrivileged(new PrivilegedAction() {
	    public Object run() {
		URL[] ret = null;
		try {
		    List v = new ArrayList();
		    Enumeration e = cl.getResources(name);
		    while (e != null && e.hasMoreElements()) {
			URL url = (URL)e.nextElement();
			if (url != null)
			    v.add(url);
		    }
		    if (v.size() > 0) {
			ret = new URL[v.size()];
			ret = (URL[])v.toArray(ret);
		    }
		} catch (IOException ioex) {
		} catch (SecurityException ex) { }
		return ret;
	    }
	});
    }

    public static URL[] getSystemResources(final String name) {
	return (URL[])
		AccessController.doPrivileged(new PrivilegedAction() {
	    public Object run() {
		URL[] ret = null;
		try {
		    List v = new ArrayList();
		    Enumeration e = ClassLoader.getSystemResources(name);
		    while (e != null && e.hasMoreElements()) {
			URL url = (URL)e.nextElement();
			if (url != null)
			    v.add(url);
		    }
		    if (v.size() > 0) {
			ret = new URL[v.size()];
			ret = (URL[])v.toArray(ret);
		    }
		} catch (IOException ioex) {
		} catch (SecurityException ex) { }
		return ret;
	    }
	});
    }

    public static InputStream openStream(final URL url) throws IOException {
	try {
	    return (InputStream)
		AccessController.doPrivileged(new PrivilegedExceptionAction() {
		    public Object run() throws IOException {
			return url.openStream();
		    }
		});
	} catch (PrivilegedActionException e) {
	    throw (IOException)e.getException();
	}
    }
}
