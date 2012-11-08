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

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.google.code.javax.activation.ActivationDataFlavor;
import com.google.code.javax.activation.DataSource;
import com.google.code.javax.mail.internet.ContentType;

/**
 * DataContentHandler for text/xml.
 *
 * @author Anil Vijendran
 * @author Bill Shannon
 */
public class text_xml extends text_plain {

    private final DataFlavor[] flavors;

    public text_xml() {
	flavors = new DataFlavor[] {
	    new ActivationDataFlavor(String.class, "text/xml", "XML String"),
	    new ActivationDataFlavor(String.class, "application/xml",
					"XML String"),
	    new ActivationDataFlavor(StreamSource.class, "text/xml", "XML"),
	    new ActivationDataFlavor(StreamSource.class, "application/xml",
					"XML")
	};
    }

    /**
     * Return the DataFlavors for this <code>DataContentHandler</code>.
     *
     * @return the DataFlavors
     */
    public DataFlavor[] getTransferDataFlavors() { // throws Exception;
	return flavors;
    }

    /**
     * Return the Transfer Data of type DataFlavor from InputStream.
     *
     * @param df the DataFlavor
     * @param ds the InputStream corresponding to the data
     * @return the constructed Object
     */
    public Object getTransferData(DataFlavor df, DataSource ds)
				throws IOException {

	for (int i = 0; i < flavors.length; i++) {
	    DataFlavor aFlavor = flavors[i];
	    if (aFlavor.equals(df)) {
		if (aFlavor.getRepresentationClass() == String.class)
		    return super.getContent(ds);
		else if (aFlavor.getRepresentationClass() == StreamSource.class)
		    return new StreamSource(ds.getInputStream());
		else
		    return null;        // XXX - should never happen
	    }
	}
	return null;
    }

    /**
     */
    public void writeTo(Object obj, String mimeType, OutputStream os)
				    throws IOException {
	if (!isXmlType(mimeType))
	    throw new IOException(
		"Invalid content type \"" + mimeType + "\" for text/xml DCH");
	if (obj instanceof String) {
	    super.writeTo(obj, mimeType, os);
	    return;
	}
	if (!(obj instanceof DataSource || obj instanceof Source)) {
	     throw new IOException("Invalid Object type = "+obj.getClass()+
		". XmlDCH can only convert DataSource or Source to XML.");
	}

	try {
	    Transformer transformer =
		TransformerFactory.newInstance().newTransformer();
	    StreamResult result = new StreamResult(os);
	    if (obj instanceof DataSource) {
		// Streaming transform applies only to
		// javax.xml.transform.StreamSource
		transformer.transform(
		    new StreamSource(((DataSource)obj).getInputStream()),
		    result);
	    } else {
		transformer.transform((Source)obj, result);
	    }
	} catch (Exception ex) {
	    throw new IOException(
		"Unable to run the JAXP transformer on a stream "
		    + ex.getMessage());
	}
    }

    private boolean isXmlType(String type) {
	try {
	    ContentType ct = new ContentType(type);
	    return ct.getSubType().equals("xml") &&
		    (ct.getPrimaryType().equals("text") ||
		    ct.getPrimaryType().equals("application"));
	} catch (Exception ex) {
	    return false;
	}
    }
}
