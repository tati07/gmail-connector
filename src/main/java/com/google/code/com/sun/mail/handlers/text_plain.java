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
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.google.code.javax.activation.ActivationDataFlavor;
import com.google.code.javax.activation.DataContentHandler;
import com.google.code.javax.activation.DataSource;
import com.google.code.javax.mail.internet.ContentType;
import com.google.code.javax.mail.internet.MimeUtility;

/**
 * DataContentHandler for text/plain.
 *
 */
public class text_plain implements DataContentHandler {
    private static ActivationDataFlavor myDF = new ActivationDataFlavor(
	java.lang.String.class,
	"text/plain",
	"Text String");

    /**
     * An OuputStream wrapper that doesn't close the underlying stream.
     */
    private static class NoCloseOutputStream extends FilterOutputStream {
	public NoCloseOutputStream(OutputStream os) {
	    super(os);
	}

	public void close() {
	    // do nothing
	}
    }

    protected ActivationDataFlavor getDF() {
	return myDF;
    }

    /**
     * Return the DataFlavors for this <code>DataContentHandler</code>.
     *
     * @return The DataFlavors
     */
    public DataFlavor[] getTransferDataFlavors() {
	return new DataFlavor[] { getDF() };
    }

    /**
     * Return the Transfer Data of type DataFlavor from InputStream.
     *
     * @param df The DataFlavor
     * @param ds The DataSource corresponding to the data
     * @return String object
     */
    public Object getTransferData(DataFlavor df, DataSource ds) 
			throws IOException {
	// use myDF.equals to be sure to get ActivationDataFlavor.equals,
	// which properly ignores Content-Type parameters in comparison
	if (getDF().equals(df))
	    return getContent(ds);
	else
	    return null;
    }

    public Object getContent(DataSource ds) throws IOException {
	String enc = null;
	InputStreamReader is = null;
	
	try {
	    enc = getCharset(ds.getContentType());
	    is = new InputStreamReader(ds.getInputStream(), enc);
	} catch (IllegalArgumentException iex) {
	    /*
	     * An unknown charset of the form ISO-XXX-XXX will cause
	     * the JDK to throw an IllegalArgumentException.  The
	     * JDK will attempt to create a classname using this string,
	     * but valid classnames must not contain the character '-',
	     * and this results in an IllegalArgumentException, rather than
	     * the expected UnsupportedEncodingException.  Yikes.
	     */
	    throw new UnsupportedEncodingException(enc);
	}

	try {
	    int pos = 0;
	    int count;
	    char buf[] = new char[1024];

	    while ((count = is.read(buf, pos, buf.length - pos)) != -1) {
		pos += count;
		if (pos >= buf.length) {
		    int size = buf.length;
		    if (size < 256*1024)
			size += size;
		    else
			size += 256*1024;
		    char tbuf[] = new char[size];
		    System.arraycopy(buf, 0, tbuf, 0, pos);
		    buf = tbuf;
		}
	    }
	    return new String(buf, 0, pos);
	} finally {
	    try {
		is.close();
	    } catch (IOException ex) { }
	}
    }
    
    /**
     * Write the object to the output stream, using the specified MIME type.
     */
    public void writeTo(Object obj, String type, OutputStream os) 
			throws IOException {
	if (!(obj instanceof String))
	    throw new IOException("\"" + getDF().getMimeType() +
		"\" DataContentHandler requires String object, " +
		"was given object of type " + obj.getClass().toString());

	String enc = null;
	OutputStreamWriter osw = null;

	try {
	    enc = getCharset(type);
	    osw = new OutputStreamWriter(new NoCloseOutputStream(os), enc);
	} catch (IllegalArgumentException iex) {
	    /*
	     * An unknown charset of the form ISO-XXX-XXX will cause
	     * the JDK to throw an IllegalArgumentException.  The
	     * JDK will attempt to create a classname using this string,
	     * but valid classnames must not contain the character '-',
	     * and this results in an IllegalArgumentException, rather than
	     * the expected UnsupportedEncodingException.  Yikes.
	     */
	    throw new UnsupportedEncodingException(enc);
	}

	String s = (String)obj;
	osw.write(s, 0, s.length());
	/*
	 * Have to call osw.close() instead of osw.flush() because
	 * some charset converts, such as the iso-2022-jp converter,
	 * don't output the "shift out" sequence unless they're closed.
	 * The NoCloseOutputStream wrapper prevents the underlying
	 * stream from being closed.
	 */
	osw.close();
    }

    private String getCharset(String type) {
	try {
	    ContentType ct = new ContentType(type);
	    String charset = ct.getParameter("charset");
	    if (charset == null)
		// If the charset parameter is absent, use US-ASCII.
		charset = "us-ascii";
	    return MimeUtility.javaCharset(charset);
	} catch (Exception ex) {
	    return null;
	}
    }
}
