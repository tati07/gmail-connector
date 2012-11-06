package org.mule.module.gmail.model;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public enum Folder {
	
	ALL_MAIL("AllMail"),
	DRAFTS("Drafts"),
	IMPORTANT("Important"),
	SENT("Sent"),
	SPAM("Spam"),
	STARRED("Starred"),
	TRASH("Trash");
	
	private String description;
	
	private Folder(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
