/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.internet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import com.google.code.com.sun.mail.util.LineOutputStream;
import com.google.code.javax.mail.MessagingException;

/**
 * A MimeBodyPart that handles data that has already been encoded.
 * This class is useful when constructing a message and attaching
 * data that has already been encoded (for example, using base64
 * encoding).  The data may have been encoded by the application,
 * or may have been stored in a file or database in encoded form.
 * The encoding is supplied when this object is created.  The data
 * is attached to this object in the usual fashion, by using the
 * <code>setText</code>, <code>setContent</code>, or
 * <code>setDataHandler</code> methods.
 *
 * @since	JavaMail 1.4
 */

public class PreencodedMimeBodyPart extends MimeBodyPart {
    private String encoding;

    /**
     * Create a PreencodedMimeBodyPart that assumes the data is
     * encoded using the specified encoding.  The encoding must
     * be a MIME supported Content-Transfer-Encoding.
     */
    public PreencodedMimeBodyPart(String encoding) {
	this.encoding = encoding;
    }

    /**
     * Returns the content transfer encoding specified when
     * this object was created.
     */
    public String getEncoding() throws MessagingException {
	return encoding;
    }

    /**
     * Output the body part as an RFC 822 format stream.
     *
     * @exception MessagingException
     * @exception IOException	if an error occurs writing to the
     *				stream or if an error is generated
     *				by the javax.activation layer.
     * @see javax.activation.DataHandler#writeTo
     */
    public void writeTo(OutputStream os)
			throws IOException, MessagingException {

	// see if we already have a LOS
	LineOutputStream los = null;
	if (os instanceof LineOutputStream) {
	    los = (LineOutputStream) os;
	} else {
	    los = new LineOutputStream(os);
	}

	// First, write out the header
	Enumeration hdrLines = getAllHeaderLines();
	while (hdrLines.hasMoreElements())
	    los.writeln((String)hdrLines.nextElement());

	// The CRLF separator between header and content
	los.writeln();

	// Finally, the content, already encoded.
	getDataHandler().writeTo(os);
	os.flush();
    }

    /**
     * Force the <code>Content-Transfer-Encoding</code> header to use
     * the encoding that was specified when this object was created.
     */
    protected void updateHeaders() throws MessagingException {
	super.updateHeaders();
	MimeBodyPart.setEncoding(this, encoding);
    }
}
