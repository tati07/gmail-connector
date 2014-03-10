/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.gmail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.mule.module.gmail.model.Attachment;
import org.mule.module.gmail.model.MailMessage;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.code.com.sun.mail.imap.IMAPMessage;
import com.google.code.javax.mail.Address;
import com.google.code.javax.mail.HasRawInputStream;
import com.google.code.javax.mail.Message;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.Multipart;
import com.google.code.javax.mail.Part;
import com.google.code.javax.mail.internet.MimeMessage.RecipientType;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public abstract class ModelAdapter {

	private static final Pattern addressExtractorPattern = Pattern.compile(".*<(.*)>");
	
	public static List<MailMessage> toModel(List<IMAPMessage> imaps, boolean includeAttachments) {
		if (CollectionUtils.isEmpty(imaps)) {
			return Collections.emptyList();
		}
		
		List<MailMessage> messages = new ArrayList<MailMessage>(imaps.size());
		for (IMAPMessage imap : imaps) {
			messages.add(toModel(imap, includeAttachments));
		}
		
		return messages;
	}
	
	public static MailMessage toModel(IMAPMessage imap, boolean includeAttachments) {
		MailMessage message = new MailMessage();
		
		message.setGoogleId(String.valueOf(imap.getGoogleMessageId()));
		message.setSubject(message.getSubject());
		
		try {
			message.setFrom(parseAddress(imap.getFrom()).get(0));
			message.setBcc(parseAddress(imap.getRecipients(RecipientType.BCC)));
			message.setCc(parseAddress(imap.getRecipients(RecipientType.CC)));
			message.setTo(parseAddress(imap.getRecipients(RecipientType.TO)));
			message.setTimestamp(imap.getReceivedDate() != null ? imap.getReceivedDate() : imap.getSentDate());
			message.setSubject(imap.getSubject());
			message.setLabels(imap.getGoogleMessageLabels());
			message.setThreadId(imap.getGoogleMessageThreadId());
			extractContentAndAttachments(message, imap, includeAttachments);
		} catch (MessagingException e) {
			throw new RuntimeException("Error accessing mailbox", e);
		}
		return message;
	}
	
	private static List<String> parseAddress(Address addresses[]) {
		if (addresses != null && addresses.length > 0) {
			List<String> result = new ArrayList<String>();
			for (Address address : addresses) {
				Matcher matcher = addressExtractorPattern.matcher(address.toString());
				result.add(matcher.find() ? matcher.group(1) : address.toString());
			}
			
			return result;
		} else {
			return Collections.emptyList();
		}
	}
	
	private static void handleMultipart(MailMessage email, Multipart multipart, StringBuilder body, boolean includeAttachments)  throws MessagingException, IOException {
		for (int i = 0, n = multipart.getCount(); i < n; i++) {
			handlePart(email, multipart.getBodyPart(i), body, includeAttachments);
	    }
	}
	
	private static void handlePart(MailMessage email, Part part, StringBuilder body, boolean includeAttachments) throws MessagingException, IOException {
	    String disposition = part.getDisposition();
	    if (disposition == null || disposition.equalsIgnoreCase(Part.INLINE)) {
	    	// When just body
    		body.append(partToString(part));
	    	
	    } else if (disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
	    	
	    	Attachment attach = new Attachment();
	    	String filename = part.getFileName();
	    	
	    	attach.setDisplayName(filename);
	    	attach.setFilename(filename);
	    	
	    	attach.setTimestamp(email.getTimestamp());
	    	attach.setSize(part.getSize());
	    	attach.setType(part.getContentType());
	    	
	    	if (includeAttachments) {
	    		attach.setData(Base64.encodeBase64String(partToString(part).getBytes()));
	    	}
	    	email.addAttachment(attach);
	    }
	}
	
	private static String partToString(Part part) {
		try {
			InputStream in = part instanceof HasRawInputStream ? ((HasRawInputStream) part).getRawInputStream() : part.getInputStream();
			return new String(IOUtils.toByteArray(in));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void extractContentAndAttachments(MailMessage email, Message message, boolean includeAttachments) throws MessagingException {
		try {
			Object content = message.getContent();
			StringBuilder body = new StringBuilder();
			
			if (content instanceof Multipart) {
				handleMultipart(email, (Multipart) content, body, includeAttachments);
			} else {
				handlePart(email, message, body, includeAttachments);
			}
			
			email.setBody(body.toString());
		} catch (IOException e) {
			throw new RuntimeException("Error reading email content stream", e);
		}
	}

}
