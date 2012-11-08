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
 * The Provider is a class that describes a protocol 
 * implementation.  The values typically come from the
 * javamail.providers and javamail.default.providers
 * resource files.  An application may also create and
 * register a Provider object to dynamically add support
 * for a new provider.
 *
 * @author Max Spivak
 * @author Bill Shannon
 */
public class Provider {

    /**
     * This inner class defines the Provider type.
     * Currently, STORE and TRANSPORT are the only two provider types 
     * supported.
     */

    public static class Type {
	public static final Type STORE     = new Type("STORE");
	public static final Type TRANSPORT = new Type("TRANSPORT");

	private String type;

	private Type(String type) {
	    this.type = type;
	}

	public String toString() {
	    return type;
	}
    }

    private Type type;
    private String protocol, className, vendor, version;

    /**
     * Create a new provider of the specified type for the specified
     * protocol.  The specified class implements the provider.
     *
     * @param type      Type.STORE or Type.TRANSPORT
     * @param protocol  valid protocol for the type
     * @param classname class name that implements this protocol
     * @param vendor    optional string identifying the vendor (may be null)
     * @param version   optional implementation version string (may be null)
     * @since JavaMail 1.4
     */
    public Provider(Type type, String protocol, String classname, 
	     String vendor, String version) {
	this.type = type;
	this.protocol = protocol;
	this.className = classname;
	this.vendor = vendor;
	this.version = version;
    }

    /** Returns the type of this Provider */
    public Type getType() {
	return type;
    }

    /** Returns the protocol supported by this Provider */
    public String getProtocol() {
	return protocol;
    }

    /** Returns name of the class that implements the protocol */
    public String getClassName() {
	return className;
    }

    /** Returns name of vendor associated with this implementation or null */
    public String getVendor() {
	return vendor;
    }

    /** Returns version of this implementation or null if no version */
    public String getVersion() {
	return version;
    }

    /** Overrides Object.toString() */
    public String toString() {
	String s = "com.google.code.javax.mail.Provider[" + type + "," +
		    protocol + "," + className;

	if (vendor != null)
	    s += "," + vendor;

	if (version != null)
	    s += "," + version;

	s += "]";
	return s;
    }
}
