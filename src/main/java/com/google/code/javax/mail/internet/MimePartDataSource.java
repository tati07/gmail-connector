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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownServiceException;

import com.google.code.javax.activation.DataSource;
import com.google.code.javax.mail.MessageAware;
import com.google.code.javax.mail.MessageContext;
import com.google.code.javax.mail.MessagingException;

/**
 * A utility class that implements a DataSource out of
 * a MimePart. This class is primarily meant for service providers.
 *
 * @see		javax.mail.internet.MimePart
 * @see		javax.activation.DataSource
 * @author 	John Mani
 */

public class MimePartDataSource implements DataSource, MessageAware {
    /**
     * The MimePart that provides the data for this DataSource.
     *
     * @since	JavaMail 1.4
     */
    protected MimePart part;

    private MessageContext context;

    /**
     * Constructor, that constructs a DataSource from a MimePart.
     */
    public MimePartDataSource(MimePart part) {
	this.part = part;
    }

    /**
     * Returns an input stream from this  MimePart. <p>
     *
     * This method applies the appropriate transfer-decoding, based 
     * on the Content-Transfer-Encoding attribute of this MimePart.
     * Thus the returned input stream is a decoded stream of bytes.<p>
     *
     * This implementation obtains the raw content from the Part
     * using the <code>getContentStream()</code> method and decodes
     * it using the <code>MimeUtility.decode()</code> method.
     *
     * @see	javax.mail.internet.MimeMessage#getContentStream
     * @see	javax.mail.internet.MimeBodyPart#getContentStream
     * @see	javax.mail.internet.MimeUtility#decode
     * @return 	decoded input stream
     */
    public InputStream getInputStream() throws IOException {
	InputStream is;

	try {
	    if (part instanceof MimeBodyPart)
		is = ((MimeBodyPart)part).getContentStream();
	    else if (part instanceof MimeMessage)
		is = ((MimeMessage)part).getContentStream();
	    else
		throw new MessagingException("Unknown part");
	    
	    String encoding =
		MimeBodyPart.restrictEncoding(part, part.getEncoding());
	    if (encoding != null)
		return MimeUtility.decode(is, encoding);
	    else
		return is;
	} catch (MessagingException mex) {
	    throw new IOException(mex.getMessage());
	}
    }

    /**
     * DataSource method to return an output stream. <p>
     *
     * This implementation throws the UnknownServiceException.
     */
    public OutputStream getOutputStream() throws IOException {
	throw new UnknownServiceException("Writing not supported");
    }

    /**
     * Returns the content-type of this DataSource. <p>
     *
     * This implementation just invokes the <code>getContentType</code>
     * method on the MimePart.
     */
    public String getContentType() {
	try {
	    return part.getContentType();
	} catch (MessagingException mex) {
	    // would like to be able to reflect the exception to the
	    // application, but since we can't do that we return a
	    // generic "unknown" value here and hope for another
	    // exception later.
	    return "application/octet-stream";
	}
    }

    /**
     * DataSource method to return a name.  <p>
     *
     * This implementation just returns an empty string.
     */
    public String getName() {
	try {
	    if (part instanceof MimeBodyPart)
		return ((MimeBodyPart)part).getFileName();
	} catch (MessagingException mex) {
	    // ignore it
	}
	return "";
    }

    /**
     * Return the <code>MessageContext</code> for the current part.
     * @since JavaMail 1.1
     */
    public synchronized MessageContext getMessageContext() {
	if (context == null)
	    context = new MessageContext(part);
	return context;
    }
}
