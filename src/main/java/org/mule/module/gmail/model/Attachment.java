/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.gmail.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an email attachment.
 * Carries the attachment's content in Base64 encoding in the data field
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class Attachment implements Serializable {

	private static final long serialVersionUID = -5890247106330933897L;

	private String displayName;
	private String filename;
	private String type;
	
	/**
	 * Base64 encoding of the attachment's data
	 */
	private String data;
	
	private double size = 0;
	private Date timestamp;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
