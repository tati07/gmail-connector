/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)DataContentHandlerFactory.java	1.7 07/05/14
 */

package com.google.code.javax.activation;

/**
 * This interface defines a factory for <code>DataContentHandlers</code>. An
 * implementation of this interface should map a MIME type into an
 * instance of DataContentHandler. The design pattern for classes implementing
 * this interface is the same as for the ContentHandler mechanism used in
 * <code>java.net.URL</code>.
 */

public interface DataContentHandlerFactory {

    /**
     * Creates a new DataContentHandler object for the MIME type.
     *
     * @param mimeType the MIME type to create the DataContentHandler for.
     * @return The new <code>DataContentHandler</code>, or <i>null</i>
     * if none are found.
     */
    public DataContentHandler createDataContentHandler(String mimeType);
}
