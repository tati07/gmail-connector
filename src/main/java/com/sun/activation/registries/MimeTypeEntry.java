/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)MimeTypeEntry.java	1.5 07/05/14
 */

package com.sun.activation.registries;


public class MimeTypeEntry {
    private String type;
    private String extension;

    public MimeTypeEntry(String mime_type, String file_ext) {
	type = mime_type;
	extension = file_ext;
    }

    public String getMIMEType() {
	return type;
    }

    public String getFileExtension() {
	return extension;
    }

    public String toString() {
	return "MIMETypeEntry: " + type + ", " + extension;
    }
}
