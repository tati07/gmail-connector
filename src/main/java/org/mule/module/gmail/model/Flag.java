package org.mule.module.gmail.model;

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
