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
import java.util.Properties;

import com.google.code.javax.activation.ActivationDataFlavor;
import com.google.code.javax.activation.DataContentHandler;
import com.google.code.javax.activation.DataSource;
import com.google.code.javax.mail.Message;
import com.google.code.javax.mail.MessageAware;
import com.google.code.javax.mail.MessageContext;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.Session;
import com.google.code.javax.mail.internet.MimeMessage;


/**
 * @author	Christopher Cotton
 */


public class message_rfc822 implements DataContentHandler {

    ActivationDataFlavor ourDataFlavor = new ActivationDataFlavor(
	com.google.code.javax.mail.Message.class,
	"message/rfc822",
	"Message");

    /**
     * return the DataFlavors for this <code>DataContentHandler</code>
     * @return The DataFlavors.
     */
    public DataFlavor[] getTransferDataFlavors() {
	return new DataFlavor[] { ourDataFlavor };
    }

    /**
     * return the Transfer Data of type DataFlavor from InputStream
     * @param df The DataFlavor.
     * @param ins The InputStream corresponding to the data.
     * @return a Message object
     */
    public Object getTransferData(DataFlavor df, DataSource ds)
				throws IOException {
	// make sure we can handle this DataFlavor
	if (ourDataFlavor.equals(df))
	    return getContent(ds);
	else
	    return null;
    }

    /**
     * Return the content.
     */
    public Object getContent(DataSource ds) throws IOException {
	// create a new MimeMessage
	try {
	    Session session;
	    if (ds instanceof MessageAware) {
		MessageContext mc = ((MessageAware)ds).getMessageContext();
		session = mc.getSession();
	    } else {
		// Hopefully a rare case.  Also hopefully the application
		// has created a default Session that can just be returned
		// here.  If not, the one we create here is better than
		// nothing, but overall not a really good answer.
		session = Session.getDefaultInstance(new Properties(), null);
	    }
	    return new MimeMessage(session, ds.getInputStream());
	} catch (MessagingException me) {
	    throw new IOException("Exception creating MimeMessage in " +
		    "message/rfc822 DataContentHandler: " + me.toString());
	}
    }

    /**
     * construct an object from a byte stream
     * (similar semantically to previous method, we are deciding
     *  which one to support)
     */
    public void writeTo(Object obj, String mimeType, OutputStream os)
			throws IOException {
	// if the object is a message, we know how to write that out
	if (obj instanceof Message) {
	    Message m = (Message)obj;
	    try {
		m.writeTo(os);
	    } catch (MessagingException me) {
		throw new IOException(me.toString());
	    }

	} else {
	    throw new IOException("unsupported object");
	}
    }
}
