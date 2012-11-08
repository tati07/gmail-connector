/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.util;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import com.google.code.javax.mail.internet.MimePart;

/**
 * General MIME-related utility methods.
 *
 * @author	Bill Shannon
 * @since	JavaMail 1.4.4
 */
public class MimeUtil {

    private static final Method cleanContentType;

    static {
	Method meth = null;
	try {
	    String cth = System.getProperty("mail.mime.contenttypehandler");
	    if (cth != null) {
		ClassLoader cl = getContextClassLoader();
		Class clsHandler = null;
		if (cl != null) {
		    try {
			clsHandler = Class.forName(cth, false, cl);
		    } catch (ClassNotFoundException cex) { }
		}
		if (clsHandler == null)
		    clsHandler = Class.forName(cth);
		meth = clsHandler.getMethod("cleanContentType",
				new Class[] { MimePart.class, String.class });
	    }
	} catch (Exception ex) {
	    // ignore it
	} finally {
	    cleanContentType = meth;
	}
    }

    // No one should instantiate this class.
    private MimeUtil() {
    }

    /**
     * If a Content-Type handler has been specified,
     * call it to clean up the Content-Type value.
     */
    public static String cleanContentType(MimePart mp, String contentType) {
	if (cleanContentType != null) {
	    try {
		return (String)cleanContentType.invoke(null,
					    new Object[] { mp, contentType });
	    } catch (Exception ex) {
		return contentType;
	    }
	} else
	    return contentType;
    }

    /**
     * Convenience method to get our context class loader.
     * Assert any privileges we might have and then call the
     * Thread.getContextClassLoader method.
     */
    private static ClassLoader getContextClassLoader() {
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
}
