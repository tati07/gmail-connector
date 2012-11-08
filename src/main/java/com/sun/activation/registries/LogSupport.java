/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)LogSupport.java	1.5 07/05/14
 */

package com.sun.activation.registries;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logging related methods.
 */
public class LogSupport {
    private static boolean debug = false;
    private static Logger logger;
    private static final Level level = Level.FINE;

    static {
	try {
	    debug = Boolean.getBoolean("javax.activation.debug");
	} catch (Throwable t) {
	    // ignore any errors
	}
	logger = Logger.getLogger("javax.activation");
    }

    /**
     * Constructor.
     */
    private LogSupport() {
	// private constructor, can't create instances
    }

    public static void log(String msg) {
	if (debug)
	    System.out.println(msg);
	logger.log(level, msg);
    }

    public static void log(String msg, Throwable t) {
	if (debug)
	    System.out.println(msg + "; Exception: " + t);
	logger.log(level, msg, t);
    }

    public static boolean isLoggable() {
	return debug || logger.isLoggable(level);
    }
}
