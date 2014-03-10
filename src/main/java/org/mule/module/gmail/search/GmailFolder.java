/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.gmail.search;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public enum GmailFolder {
	
	ALL_MAIL("AllMail"),
	DRAFTS("Drafts"),
	IMPORTANT("Important"),
	SENT("Sent"),
	SPAM("Spam"),
	STARRED("Starred"),
	TRASH("Trash"),
	INBOX("Inbox");
	
	private String description;
	
	private GmailFolder(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
