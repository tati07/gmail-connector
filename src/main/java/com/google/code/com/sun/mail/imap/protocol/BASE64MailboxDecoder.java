/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap.protocol;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * See the BASE64MailboxEncoder for a description of the RFC2060 and how
 * mailbox names should be encoded.  This class will do the correct decoding
 * for mailbox names.
 *
 * @author	Christopher Cotton
 */

public class BASE64MailboxDecoder {
    
    public static String decode(String original) {
	if (original == null || original.length() == 0)
	    return original;

	boolean changedString = false;
	int copyTo = 0;
	// it will always be less than the original
	char[] chars = new char[original.length()]; 
	StringCharacterIterator iter = new StringCharacterIterator(original);
	
	for(char c = iter.first(); c != CharacterIterator.DONE; 
	    c = iter.next()) {

	    if (c == '&') {
		changedString = true;
		copyTo = base64decode(chars, copyTo, iter);
	    } else {
		chars[copyTo++] = c;
	    }
	}
	
	// now create our string from the char array
	if (changedString) {
	    return new String(chars, 0, copyTo);
	} else {
	    return original;
	}	
    }


    protected static int base64decode(char[] buffer, int offset,
				      CharacterIterator iter) {
	boolean firsttime = true;
	int leftover = -1;

	while(true) {
	    // get the first byte
	    byte orig_0 = (byte) iter.next();
	    if (orig_0 == -1) break; // no more chars
	    if (orig_0 == '-') {
		if (firsttime) {
		    // means we got the string "&-" which is turned into a "&"
		    buffer[offset++] = '&';
		}
		// we are done now
		break;
	    }
	    firsttime = false;
	    
	    // next byte
	    byte orig_1 = (byte) iter.next();	    
	    if (orig_1 == -1 || orig_1 == '-')
		break; // no more chars, invalid base64
	    
	    byte a, b, current;
	    a = pem_convert_array[orig_0 & 0xff];
	    b = pem_convert_array[orig_1 & 0xff];
	    // The first decoded byte
	    current = (byte)(((a << 2) & 0xfc) | ((b >>> 4) & 3));

	    // use the leftover to create a Unicode Character (2 bytes)
	    if (leftover != -1) {
		buffer[offset++] = (char)(leftover << 8 | (current & 0xff));
		leftover = -1;
	    } else {
		leftover = current & 0xff;
	    }
	    
	    byte orig_2 = (byte) iter.next();
	    if (orig_2 == '=') { // End of this BASE64 encoding
		continue;
	    } else if (orig_2 == -1 || orig_2 == '-') {
	    	break; // no more chars
	    }
	    	    
	    // second decoded byte
	    a = b;
	    b = pem_convert_array[orig_2 & 0xff];
	    current = (byte)(((a << 4) & 0xf0) | ((b >>> 2) & 0xf));

	    // use the leftover to create a Unicode Character (2 bytes)
	    if (leftover != -1) {
		buffer[offset++] = (char)(leftover << 8 | (current & 0xff));
		leftover = -1;
	    } else {
		leftover = current & 0xff;
	    }

	    byte orig_3 = (byte) iter.next();
	    if (orig_3 == '=') { // End of this BASE64 encoding
		continue;
	    } else if (orig_3 == -1 || orig_3 == '-') {
	    	break;  // no more chars
	    }
	    
	    // The third decoded byte
	    a = b;
	    b = pem_convert_array[orig_3 & 0xff];
	    current = (byte)(((a << 6) & 0xc0) | (b & 0x3f));
	    
	    // use the leftover to create a Unicode Character (2 bytes)
	    if (leftover != -1) {
		buffer[offset++] = (char)(leftover << 8 | (current & 0xff));
		leftover = -1;
	    } else {
		leftover = current & 0xff;
	    }	    
	}
	
	return offset;
    }

    /**
     * This character array provides the character to value map
     * based on RFC1521, but with the modification from RFC2060
     * which changes the '/' to a ','.
     */  

    // shared with BASE64MailboxEncoder
    static final char pem_array[] = {
	'A','B','C','D','E','F','G','H', // 0
	'I','J','K','L','M','N','O','P', // 1
	'Q','R','S','T','U','V','W','X', // 2
	'Y','Z','a','b','c','d','e','f', // 3
	'g','h','i','j','k','l','m','n', // 4
	'o','p','q','r','s','t','u','v', // 5
	'w','x','y','z','0','1','2','3', // 6
	'4','5','6','7','8','9','+',','  // 7
    };

    private static final byte pem_convert_array[] = new byte[256];

    static {
	for (int i = 0; i < 255; i++)
	    pem_convert_array[i] = -1;
	for(int i = 0; i < pem_array.length; i++)
	    pem_convert_array[pem_array[i]] = (byte) i;
    }    
}
