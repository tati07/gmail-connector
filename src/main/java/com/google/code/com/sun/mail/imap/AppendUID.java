/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap;


/**
 * Information from the APPENDUID response code
 * defined by the UIDPLUS extension -
 * <A HREF="http://www.ietf.org/rfc/rfc2359.txt">RFC 2359</A>.
 *
 * @author  Bill Shannon
 */

public class AppendUID { 
    public long uidvalidity = -1;
    public long uid = -1;

    public AppendUID(long uidvalidity, long uid) {
	this.uidvalidity = uidvalidity;
	this.uid = uid;
    }
}
