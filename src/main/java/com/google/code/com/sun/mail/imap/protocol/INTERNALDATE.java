/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.imap.protocol;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.code.com.sun.mail.iap.ParsingException;
import com.google.code.javax.mail.internet.MailDateFormat;


/**
 * This class 
 *
 * @author  John Mani
 */

public class INTERNALDATE implements Item {

    static final char[] name =
	{'I','N','T','E','R','N','A','L','D','A','T','E'};
    public int msgno;
    protected Date date;

    /*
     * Used to parse dates only.  The parse method is thread safe
     * so we only need to create a single object for use by all
     * instances.  We depend on the fact that the MailDateFormat
     * class will parse dates in INTERNALDATE format as well as
     * dates in RFC 822 format.
     */
    private static MailDateFormat mailDateFormat = new MailDateFormat();

    /**
     * Constructor
     */
    public INTERNALDATE(FetchResponse r) throws ParsingException {
	msgno = r.getNumber();
	r.skipSpaces();
	String s = r.readString();
	if (s == null)
	    throw new ParsingException("INTERNALDATE is NIL");
	try {
	    date = mailDateFormat.parse(s);
	} catch (ParseException pex) {
	    throw new ParsingException("INTERNALDATE parse error");
	}
    }

    public Date getDate() {
	return date;
    }

    // INTERNALDATE formatter

    private static SimpleDateFormat df = 
	// Need Locale.US, the "MMM" field can produce unexpected values
	// in non US locales !
	new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss ", Locale.US);

    /**
     * Format given Date object into INTERNALDATE string
     */
    public static String format(Date d) {
	/*
	 * SimpleDateFormat objects aren't thread safe, so rather
	 * than create a separate such object for each request,
	 * we create one object and synchronize its use here
	 * so that only one thread is using it at a time.  This
	 * trades off some potential concurrency for speed in the
	 * common case.
	 *
	 * This method is only used when formatting the date in a
	 * message that's being appended to a folder.
	 */
	StringBuffer sb = new StringBuffer();
	synchronized (df) {
	    df.format(d, sb, new FieldPosition(0));
	}

	// compute timezone offset string
	// XXX - Yes, I know this is deprecated
	int rawOffsetInMins = -d.getTimezoneOffset();
	/*
	 * XXX - in JavaMail 1.4 / J2SE 1.4, possibly replace above with:
	 *
	TimeZone tz = TimeZone.getDefault();
	int offset = tz.getOffset(d);	// get offset from GMT
	int rawOffsetInMins = offset / 60 / 1000; // offset from GMT in mins
	 */
	if (rawOffsetInMins < 0) {
	    sb.append('-');
	    rawOffsetInMins = (-rawOffsetInMins);
	} else
	    sb.append('+');
	
	int offsetInHrs = rawOffsetInMins / 60;
	int offsetInMins = rawOffsetInMins % 60;

	sb.append(Character.forDigit((offsetInHrs/10), 10));
	sb.append(Character.forDigit((offsetInHrs%10), 10));
	sb.append(Character.forDigit((offsetInMins/10), 10));
	sb.append(Character.forDigit((offsetInMins%10), 10));

	return sb.toString();
    }
}
