/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.smtp;

import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.RealmChoiceCallback;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

import com.google.code.com.sun.mail.util.ASCIIUtility;
import com.google.code.com.sun.mail.util.BASE64DecoderStream;
import com.google.code.com.sun.mail.util.BASE64EncoderStream;
import com.google.code.javax.mail.MessagingException;

/**
 * This class contains a single method that does authentication using
 * SASL.  This is in a separate class so that it can be compiled with
 * J2SE 1.5.  Eventually it should be merged into SMTPTransport.java.
 */

public class SMTPSaslAuthenticator implements SaslAuthenticator {

    private SMTPTransport pr;
    private String name;
    private Properties props;
    private boolean debug;
    private PrintStream out;
    private String host;

    public SMTPSaslAuthenticator(SMTPTransport pr, String name,
		Properties props, boolean debug, PrintStream out, String host) {
	this.pr = pr;
	this.name = name;
	this.props = props;
	this.debug = debug;
	this.out = out;
	this.host = host;
    }

    public boolean authenticate(String[] mechs, final String realm,
				final String authzid, final String u,
				final String p) throws MessagingException {

	boolean done = false;
	if (debug) {
	    out.print("DEBUG SMTP SASL: Mechanisms:");
	    for (int i = 0; i < mechs.length; i++)
		out.print(" " + mechs[i]);
	    out.println();
	}

	SaslClient sc;
	CallbackHandler cbh = new CallbackHandler() {
	    public void handle(Callback[] callbacks) {
		if (debug)
		    out.println("DEBUG SMTP SASL: callback length: " +
							callbacks.length);
		for (int i = 0; i < callbacks.length; i++) {
		    if (debug)
			out.println("DEBUG SMTP SASL: callback " + i + ": " +
							callbacks[i]);
		    if (callbacks[i] instanceof NameCallback) {
			NameCallback ncb = (NameCallback)callbacks[i];
			ncb.setName(u);
		    } else if (callbacks[i] instanceof PasswordCallback) {
			PasswordCallback pcb = (PasswordCallback)callbacks[i];
			pcb.setPassword(p.toCharArray());
		    } else if (callbacks[i] instanceof RealmCallback) {
			RealmCallback rcb = (RealmCallback)callbacks[i];
			rcb.setText(realm != null ?
				    realm : rcb.getDefaultText());
		    } else if (callbacks[i] instanceof RealmChoiceCallback) {
			RealmChoiceCallback rcb =
			    (RealmChoiceCallback)callbacks[i];
			if (realm == null)
			    rcb.setSelectedIndex(rcb.getDefaultChoice());
			else {
			    // need to find specified realm in list
			    String[] choices = rcb.getChoices();
			    for (int k = 0; k < choices.length; k++) {
				if (choices[k].equals(realm)) {
				    rcb.setSelectedIndex(k);
				    break;
				}
			    }
			}
		    }
		}
	    }
	};

	try {
	    sc = Sasl.createSaslClient(mechs, authzid, name, host,
					(Map)props, cbh);
	} catch (SaslException sex) {
	    if (debug)
		out.println("DEBUG SMTP SASL: Failed to create SASL client: " +
								sex);
	    return false;
	}
	if (sc == null) {
	    if (debug)
		out.println("DEBUG SMTP SASL: No SASL support");
	    return false;
	}
	if (debug)
	    out.println("DEBUG SMTP SASL: SASL client " +
						sc.getMechanismName());

	int resp;
	try {
	    String mech = sc.getMechanismName();
	    String ir = null;
	    if (sc.hasInitialResponse()) {
		byte[] ba = sc.evaluateChallenge(new byte[0]);
		ba = BASE64EncoderStream.encode(ba);
		ir = ASCIIUtility.toString(ba, 0, ba.length);
	    }
	    if (ir != null)
		resp = pr.simpleCommand("AUTH " + mech + " " + ir);
	    else
		resp = pr.simpleCommand("AUTH " + mech);

	    /*
	     * A 530 response indicates that the server wants us to
	     * issue a STARTTLS command first.  Do that and try again.
	     */
	    if (resp == 530) {
		pr.startTLS();
		if (ir != null)
		    resp = pr.simpleCommand("AUTH " + mech + " " + ir);
		else
		    resp = pr.simpleCommand("AUTH " + mech);
	    }

	    if (resp == 235)
		return true;	// success already!

	    if (resp != 334)
		return false;
	} catch (Exception ex) {
	    if (debug)
		out.println("DEBUG SMTP SASL: AUTHENTICATE Exception: " + ex);
	    return false;
	}

	while (!done) { // loop till we are done
	    try {
	    	if (resp == 334) {
		    byte[] ba = null;
		    if (!sc.isComplete()) {
			ba = ASCIIUtility.getBytes(responseText(pr));
			if (ba.length > 0)
			    ba = BASE64DecoderStream.decode(ba);
			if (debug)
			    out.println("DEBUG SMTP SASL: challenge: " +
				ASCIIUtility.toString(ba, 0, ba.length) + " :");
			ba = sc.evaluateChallenge(ba);
		    }
		    if (ba == null) {
			if (debug)
			    out.println("DEBUG SMTP SASL: no response");
			resp = pr.simpleCommand("*");
		    } else {
			if (debug)
			    out.println("DEBUG SMTP SASL: response: " +
				ASCIIUtility.toString(ba, 0, ba.length) + " :");
			ba = BASE64EncoderStream.encode(ba);
			resp = pr.simpleCommand(ba);
		    }
		} else
		    done = true;
	    } catch (Exception ioex) {
		if (debug)
		    ioex.printStackTrace();
		done = true;
		// XXX - ultimately return true???
	    }
	}

	if (sc.isComplete() /*&& res.status == SUCCESS*/) {
	    String qop = (String)sc.getNegotiatedProperty(Sasl.QOP);
	    if (qop != null && (qop.equalsIgnoreCase("auth-int") ||
				qop.equalsIgnoreCase("auth-conf"))) {
		// XXX - NOT SUPPORTED!!!
		if (debug)
		    out.println("DEBUG SMTP SASL: " +
			"Mechanism requires integrity or confidentiality");
		return false;
	    }
	}

	return true;
    }

    private static final String responseText(SMTPTransport pr) {
	String resp = pr.getLastServerResponse().trim();
	if (resp.length() > 4)
	    return resp.substring(4);
	else
	    return "";
    }
}
