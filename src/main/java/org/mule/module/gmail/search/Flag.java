/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.gmail.search;

import com.google.code.javax.mail.Flags;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public enum Flag {

    ANSWERED(0x01),
    DELETED(0x02),
    DRAFT(0x04),
    FLAGGED(0x08),
    RECENT(0x10),
    SEEN(0x20),
    USER(0x80000000);
    
    private int value;
    
    private Flag(int value) {
    	this.value = value;
    }
    
    public Flags getValue() {
		return new Flags(new com.google.code.javax.mail.Flags.Flag(this.value));
	}
}
