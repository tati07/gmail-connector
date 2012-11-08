/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap;

import java.util.Vector;

import com.google.code.com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.google.code.javax.mail.BodyPart;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.MultipartDataSource;
import com.google.code.javax.mail.internet.MimePart;
import com.google.code.javax.mail.internet.MimePartDataSource;

/**
 * This class 
 *
 * @author  John Mani
 */

public class IMAPMultipartDataSource extends MimePartDataSource
				     implements MultipartDataSource {
    private Vector parts;

    protected IMAPMultipartDataSource(MimePart part, BODYSTRUCTURE[] bs, 
				      String sectionId, IMAPMessage msg) {
	super(part);

	parts = new Vector(bs.length);
	for (int i = 0; i < bs.length; i++)
	    parts.addElement(
		new IMAPBodyPart(bs[i], 
				 sectionId == null ? 
				   Integer.toString(i+1) : 
				   sectionId + "." + Integer.toString(i+1),
				 msg)
	    );
    }

    public int getCount() {
	return parts.size();
    }

    public BodyPart getBodyPart(int index) throws MessagingException {
	return (BodyPart)parts.elementAt(index);
    }
}
