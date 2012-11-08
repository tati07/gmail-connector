/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.util.logging;

import java.io.ObjectStreamException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.LogManager;

/**
 * An adapter class to allow the Mail API to access the LogManager properties.
 * The LogManager properties are treated as the root of all properties.
 * First, the parent properties are searched. If no value is found, then,
 * the LogManager is searched with prefix value.  If not found, then, just the
 * key itself is searched in the LogManager. If a value is found in the
 * LogManager it is then copied to this properties object with no key prefix.
 * If no value is found in the LogManager or the parent properties, then this
 * properties object is searched only by passing the key value.
 *
 * <p>
 * This class also emulates the LogManager functions for creating new objects
 * from string class names.  This is to support initial setup of objects such as
 * log filters, formatters, error managers, etc.
 *
 * <p>
 * This class should never be exposed outside of this package.  Keep this
 * class package private (default access).
 * 
 * @author Jason Mehrens
 * @since JavaMail 1.4.3
 */
final class LogManagerProperties extends Properties {

    private static final long serialVersionUID = -2239983349056806252L;
    /**
     * Caches the LogManager so we only read the config once.
     */
    final static LogManager manager = LogManager.getLogManager();

    /**
     * This code is modified from the LogManager, which explictly states
     * searching the system class loader first, then the context class loader.
     * There is resistance (compatibility) to change this behavior to simply
     * searching the context class loader.
     * @param name full class name
     * @return the class.
     * @throws ClassNotFoundException if not found.
     */
    static Class findClass(String name) throws ClassNotFoundException {
        ClassLoader[] loaders = getClassLoaders();
        Class clazz;
        if (loaders[0] != null) {
            try {
                clazz = Class.forName(name, false, loaders[0]);
            } catch (ClassNotFoundException tryContext) {
                clazz = tryLoad(name, loaders[1]);
            }
        } else {
            clazz = tryLoad(name, loaders[1]);
        }
        return clazz;
    }

    private static Class tryLoad(String name, ClassLoader l) throws ClassNotFoundException {
        if (l != null) {
            return Class.forName(name, false, l);
        } else {
            return Class.forName(name);
        }
    }

    private static ClassLoader[] getClassLoaders() {
        return (ClassLoader[]) AccessController.doPrivileged(new PrivilegedAction() {

            public Object run() {
                final ClassLoader[] loaders = new ClassLoader[2];
                try {
                    loaders[0] = ClassLoader.getSystemClassLoader();
                } catch (SecurityException ignore) {
                    loaders[0] = null;
                }

                try {
                    loaders[1] = Thread.currentThread().getContextClassLoader();
                } catch (SecurityException ignore) {
                    loaders[1] = null;
                }
                return loaders;
            }
        });
    }
    private final String prefix;

    /**
     * Creates a log manager properties object.
     * @param parent the parent properties.
     * @param prefix the namespace prefix.
     * @throws NullPointerException if <tt>prefix</tt> or <tt>parent</tt> is
     * <tt>null</tt>.
     */
    LogManagerProperties(final Properties parent, final String prefix) {
        super(parent);
        parent.isEmpty(); //null check, happens-before
        if (prefix == null) {
            throw new NullPointerException();
        }
        this.prefix = prefix;

        //'defaults' is not decalared final.
        super.isEmpty(); //happens-before.
    }

    /**
     * Clones the default properties.
     * @return the clone.
     */
    public Object clone() {
        Properties parent;
        synchronized (this) {
            parent = defaults;
        }
        return parent.clone();
    }

    /**
     * Performs the super action, then searches the log manager
     * by the prefix property, and then by the key itself.
     * @param key a non null key.
     * @return the value for that key.
     */
    public synchronized String getProperty(String key) {
        String value = defaults.getProperty(key);
        if (value == null && key.length() > 0) {
            value = manager.getProperty(prefix + '.' + key);
            if (value == null) {
                value = manager.getProperty(key);
            }

            /**
             * Copy the log manager properties as we read them.  If a value is
             * no longer present in the LogManager read it from here.
             * The reason this works is because LogManager.reset() closes
             * all attached handlers therefore, stale values only exist in
             * closed handlers.
             */
            if (value != null) {
                put(key, value);
            } else {
                Object v = get(key); //Call 'get' so defaults are not used.
                value = v instanceof String ? (String) v : null;
            }
        }
        return value;
    }

    /**
     * Calls getProperty directly.  If getProperty returns null the default
     * value is returned.
     * @param key a key to search for.
     * @param def the default value to use if not found.
     * @return the value for the key.
     * @since JavaMail 1.4.4
     */
    public String getProperty(String key, String def) {
        final String value = this.getProperty(key);
        return value == null ? def : value;
    }

    /**
     * It is assumed that this method will never be called.
     * No way to get the property names from LogManager.
     * @return the property names
     */
    public Enumeration propertyNames() {
        assert false;
        return super.propertyNames();
    }

    /**
     * It is assumed that this method will never be called.
     * The prefix value is not used for the equals method.
     * @param o any object or null.
     * @return true if equal, otherwise false.
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o instanceof Properties == false) {
            return false;
        }
        assert false : prefix;
        return super.equals(o);
    }

    /**
     * It is assumed that this method will never be called.  See equals.
     * @return the hash code.
     */
    public int hashCode() {
        assert false : prefix.hashCode();
        return super.hashCode();
    }

    /**
     * It is assumed that this method will never be called.
     * @return the parent properties.
     * @throws ObjectStreamException if there is a problem.
     */
    private synchronized Object writeReplace() throws ObjectStreamException {
        assert false;
        return new Properties(defaults);
    }
}
