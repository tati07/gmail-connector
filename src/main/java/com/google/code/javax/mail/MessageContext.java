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
 * The context in which a piece of Message content is contained.  A
 * <code>MessageContext</code> object is returned by the
 * <code>getMessageContext</code> method of the
 * <code>MessageAware</code> interface.  <code>MessageAware</code> is
 * typically implemented by <code>DataSources</code> to allow a
 * <code>DataContentHandler</code> to pass on information about the
 * context in which a data content object is operating.
 *
 * @see javax.mail.MessageAware
 * @see javax.activation.DataSource
 * @see javax.activation.DataContentHandler
 * @since	JavaMail 1.1
 */
public class MessageContext {
    private Part part;

    /**
     * Create a MessageContext object describing the context of the given Part.
     */
    public MessageContext(Part part) {
	this.part = part;
    }

    /**
     * Return the Part that contains the content.
     *
     * @return	the containing Part, or null if not known
     */
    public Part getPart() {
	return part;
    }

    /**
     * Return the Message that contains the content.
     * Follows the parent chain up through containing Multipart
     * objects until it comes to a Message object, or null.
     *
     * @return	the containing Message, or null if not known
     */
    public Message getMessage() {
	try {
	    return getMessage(part);
	} catch (MessagingException ex) {
	    return null;
	}
    }

    /**
     * Return the Message containing an arbitrary Part.
     * Follows the parent chain up through containing Multipart
     * objects until it comes to a Message object, or null.
     *
     * @return	the containing Message, or null if none
     * @see javax.mail.BodyPart#getParent
     * @see javax.mail.Multipart#getParent
     */
    private static Message getMessage(Part p) throws MessagingException {
	while (p != null) {
	    if (p instanceof Message)
		return (Message)p;
	    BodyPart bp = (BodyPart)p;
	    Multipart mp = bp.getParent();
	    if (mp == null)	// MimeBodyPart might not be in a MimeMultipart
		return null;
	    p = mp.getParent();
	}
	return null;
    }

    /**
     * Return the Session we're operating in.
     *
     * @return	the Session, or null if not known
     */
    public Session getSession() {
	Message msg = getMessage();
	return msg != null ? msg.session : null;
    }
}
