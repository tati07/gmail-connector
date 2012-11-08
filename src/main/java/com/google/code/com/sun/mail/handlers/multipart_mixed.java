/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.handlers;

import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.io.OutputStream;

import com.google.code.javax.activation.ActivationDataFlavor;
import com.google.code.javax.activation.DataContentHandler;
import com.google.code.javax.activation.DataSource;
import com.google.code.javax.mail.MessagingException;


public class multipart_mixed implements DataContentHandler {
    private ActivationDataFlavor myDF = new ActivationDataFlavor(
	    com.google.code.javax.mail.internet.MimeMultipart.class,
	    "multipart/mixed",
	    "Multipart");

    /**
     * Return the DataFlavors for this <code>DataContentHandler</code>.
     *
     * @return The DataFlavors
     */
    public DataFlavor[] getTransferDataFlavors() { // throws Exception;
	return new DataFlavor[] { myDF };
    }

    /**
     * Return the Transfer Data of type DataFlavor from InputStream.
     *
     * @param df The DataFlavor
     * @param ins The InputStream corresponding to the data
     * @return String object
     */
    public Object getTransferData(DataFlavor df, DataSource ds)
				throws IOException {
	// use myDF.equals to be sure to get ActivationDataFlavor.equals,
	// which properly ignores Content-Type parameters in comparison
	if (myDF.equals(df))
	    return getContent(ds);
	else
	    return null;
    }

    /**
     * Return the content.
     */
    public Object getContent(DataSource ds) throws IOException {
	try {
	    return new com.google.code.javax.mail.internet.MimeMultipart(ds);
	} catch (MessagingException e) {
	    IOException ioex =
		new IOException("Exception while constructing MimeMultipart");
	    ioex.initCause(e);
	    throw ioex;
	}
    }

    /**
     * Write the object to the output stream, using the specific MIME type.
     */
    public void writeTo(Object obj, String mimeType, OutputStream os)
			throws IOException {
	if (obj instanceof com.google.code.javax.mail.internet.MimeMultipart) {
	    try {
		((com.google.code.javax.mail.internet.MimeMultipart)obj).writeTo(os);
	    } catch (MessagingException e) {
		throw new IOException(e.toString());
	    }
	}
    }
}
