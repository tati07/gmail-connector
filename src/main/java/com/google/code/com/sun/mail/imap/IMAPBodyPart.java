/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import com.google.code.com.sun.mail.iap.ConnectionException;
import com.google.code.com.sun.mail.iap.ProtocolException;
import com.google.code.com.sun.mail.imap.protocol.BODY;
import com.google.code.com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.google.code.com.sun.mail.imap.protocol.IMAPProtocol;
import com.google.code.com.sun.mail.util.PropUtil;
import com.google.code.javax.activation.DataHandler;
import com.google.code.javax.mail.FolderClosedException;
import com.google.code.javax.mail.IllegalWriteException;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.Multipart;
import com.google.code.javax.mail.internet.ContentType;
import com.google.code.javax.mail.internet.InternetHeaders;
import com.google.code.javax.mail.internet.MimeBodyPart;
import com.google.code.javax.mail.internet.MimeUtility;

/**
 * An IMAP body part.
 *
 * @author  John Mani
 */

public class IMAPBodyPart extends MimeBodyPart {
    private IMAPMessage message;
    private BODYSTRUCTURE bs;
    private String sectionId;

    // processed values ..
    private String type;
    private String description;

    private boolean headersLoaded = false;

    private static final boolean decodeFileName =
	PropUtil.getBooleanSystemProperty("mail.mime.decodefilename", false);

    protected IMAPBodyPart(BODYSTRUCTURE bs, String sid, IMAPMessage message) {
	super();
	this.bs = bs;
	this.sectionId = sid;
	this.message = message;
	// generate content-type
	ContentType ct = new ContentType(bs.type, bs.subtype, bs.cParams);
	type = ct.toString();
    }

    /* Override this method to make it a no-op, rather than throw
     * an IllegalWriteException. This will permit IMAPBodyParts to
     * be inserted in newly crafted MimeMessages, especially when
     * forwarding or replying to messages.
     */
    protected void updateHeaders() {
	return;
    }

    public int getSize() throws MessagingException {
	return bs.size;
    }

    public int getLineCount() throws MessagingException {
	return bs.lines;
    }

    public String getContentType() throws MessagingException {
	return type;
    }

    public String getDisposition() throws MessagingException {
	return bs.disposition;
    }

    public void setDisposition(String disposition) throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public String getEncoding() throws MessagingException {
	return bs.encoding;
    }

    public String getContentID() throws MessagingException {
	return bs.id;
    }

    public String getContentMD5() throws MessagingException {
	return bs.md5;
    }

    public void setContentMD5(String md5) throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public String getDescription() throws MessagingException {
	if (description != null) // cached value ?
	    return description;

	if (bs.description == null)
	    return null;
	
	try {
	    description = MimeUtility.decodeText(bs.description);
	} catch (UnsupportedEncodingException ex) {
	    description = bs.description;
	}

	return description;
    }

    public void setDescription(String description, String charset)
			throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public String getFileName() throws MessagingException {
	String filename = null;
	if (bs.dParams != null)
	    filename = bs.dParams.get("filename");
	if (filename == null && bs.cParams != null)
	    filename = bs.cParams.get("name");
	if (decodeFileName && filename != null) {
	    try {
		filename = MimeUtility.decodeText(filename);
	    } catch (UnsupportedEncodingException ex) {
		throw new MessagingException("Can't decode filename", ex);
	    }
	}
	return filename;
    }

    public void setFileName(String filename) throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    protected InputStream getContentStream() throws MessagingException {
	InputStream is = null;
	boolean pk = message.getPeek();	// acquire outisde of message cache lock

        // Acquire MessageCacheLock, to freeze seqnum.
        synchronized(message.getMessageCacheLock()) {
	    try {
		IMAPProtocol p = message.getProtocol();

		// Check whether this message is expunged
		message.checkExpunged();

		if (p.isREV1() && (message.getFetchBlockSize() != -1))
		    return new IMAPInputStream(message, sectionId, bs.size, pk);

		// Else, vanila IMAP4, no partial fetch 

		int seqnum = message.getSequenceNumber();
		BODY b;
		if (pk)
		    b = p.peekBody(seqnum, sectionId);
		else
		    b = p.fetchBody(seqnum, sectionId);
		if (b != null)
		    is = b.getByteArrayInputStream();
	    } catch (ConnectionException cex) {
		throw new FolderClosedException(
			message.getFolder(), cex.getMessage());
	    } catch (ProtocolException pex) { 
		throw new MessagingException(pex.getMessage(), pex);
	    }
	}

	if (is == null)
	    throw new MessagingException("No content");
	else
	    return is;
    }
	    
    public synchronized DataHandler getDataHandler() 
		throws MessagingException {
	if (dh == null) {
	   if (bs.isMulti())
		dh = new DataHandler(
			new IMAPMultipartDataSource(
				this, bs.bodies, sectionId, message)
		     );
	    else if (bs.isNested() && message.isREV1())
		dh = new DataHandler(
			new IMAPNestedMessage(message, 
					      bs.bodies[0],
					      bs.envelope,
					      sectionId),
			type
		     );
	}

	return super.getDataHandler();
    }

    public void setDataHandler(DataHandler content) throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setContent(Object o, String type) throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setContent(Multipart mp) throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public String[] getHeader(String name) throws MessagingException {
	loadHeaders();
	return super.getHeader(name);
    }

    public void setHeader(String name, String value)
		throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void addHeader(String name, String value)
		throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void removeHeader(String name) throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public Enumeration getAllHeaders() throws MessagingException {
	loadHeaders();
	return super.getAllHeaders();
    }

    public Enumeration getMatchingHeaders(String[] names)
		throws MessagingException {
	loadHeaders();
	return super.getMatchingHeaders(names);
    }

    public Enumeration getNonMatchingHeaders(String[] names)
		throws MessagingException {
	loadHeaders();
	return super.getNonMatchingHeaders(names);
    }

    public void addHeaderLine(String line) throws MessagingException {
	throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public Enumeration getAllHeaderLines() throws MessagingException {
	loadHeaders();
	return super.getAllHeaderLines();
    }

    public Enumeration getMatchingHeaderLines(String[] names)
		throws MessagingException {
	loadHeaders();
	return super.getMatchingHeaderLines(names);
    }

    public Enumeration getNonMatchingHeaderLines(String[] names)
		throws MessagingException {
	loadHeaders();
	return super.getNonMatchingHeaderLines(names);
    }

    private synchronized void loadHeaders() throws MessagingException {
	if (headersLoaded)
	    return;

	if (headers == null)
	    headers = new InternetHeaders();

	// load headers

	// Acquire MessageCacheLock, to freeze seqnum.
	synchronized(message.getMessageCacheLock()) {
	    try {
		IMAPProtocol p = message.getProtocol();

		// Check whether this message got expunged
		message.checkExpunged();

		if (p.isREV1()) {
		    int seqnum = message.getSequenceNumber();
		    BODY b = p.peekBody(seqnum, sectionId + ".MIME");

		    if (b == null)
			throw new MessagingException("Failed to fetch headers");

		    ByteArrayInputStream bis = b.getByteArrayInputStream();
		    if (bis == null)
			throw new MessagingException("Failed to fetch headers");

		    headers.load(bis);

		} else {

		    // RFC 1730 does not provide for fetching BodyPart headers
		    // So, just dump the RFC1730 BODYSTRUCTURE into the
		    // headerStore
		    
		    // Content-Type
		    headers.addHeader("Content-Type", type);
		    // Content-Transfer-Encoding
		    headers.addHeader("Content-Transfer-Encoding", bs.encoding);
		    // Content-Description
		    if (bs.description != null)
			headers.addHeader("Content-Description",
							    bs.description);
		    // Content-ID
		    if (bs.id != null)
			headers.addHeader("Content-ID", bs.id);
		    // Content-MD5
		    if (bs.md5 != null)
			headers.addHeader("Content-MD5", bs.md5);
		}
	    } catch (ConnectionException cex) {
		throw new FolderClosedException(
			    message.getFolder(), cex.getMessage());
	    } catch (ProtocolException pex) {
		throw new MessagingException(pex.getMessage(), pex);
	    }
	}
	headersLoaded = true;
    }
}
