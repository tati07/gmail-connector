/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.oauth;

import java.security.Security;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.google.code.com.sun.mail.imap.IMAPSSLStore;
import com.google.code.com.sun.mail.imap.IMAPStore;
import com.google.code.com.sun.mail.smtp.SMTPTransport;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.Session;
import com.google.code.javax.mail.URLName;


/**
 * Performs OAuth2 authentication.
 *
 * <p>Before using this class, you must call {@code initialize} to install the
 * OAuth2 SASL provider.
 */
public class OAuth2Authenticator {
  
  /**
   * Installs the OAuth2 SASL provider. This must be called exactly once before
   * calling other methods on this class.
   */
  public static void initialize() {
	  Security.addProvider(new OAuth2Provider());
  }

  /**
   * Connects and authenticates to an IMAP server with OAuth2. You must have called {@code initialize}.
   *
   * @param userEmail Email address of the user to authenticate, for example {@code oauth@gmail.com}.
   * @param oauthToken The user's OAuth token.
   *
   * @return An authenticated IMAPStore that can be used for IMAP operations.
   */
  public static IMAPStore connectToImap(String userEmail, String oauthToken) throws MessagingException {
    Properties props = new Properties();
    props.put("mail.imaps.sasl.enable", "true");
    props.put("mail.imaps.sasl.mechanisms", "XOAUTH2");
    props.put(OAuth2SaslClientFactory.OAUTH_TOKEN_PROP, oauthToken);
    Session session = Session.getInstance(props);

    IMAPSSLStore store = new IMAPSSLStore(session, null);
    store.connect("imap.gmail.com", 993, userEmail, StringUtils.EMPTY);
    return store;
  }

  /**
   * Connects and authenticates to an SMTP server with OAuth2. You must have
   * called {@code initialize}.
   *
   * @param host Hostname of the smtp server, for example {@code
   *     smtp.googlemail.com}.
   * @param port Port of the smtp server, for example 587.
   * @param userEmail Email address of the user to authenticate, for example
   *     {@code oauth@gmail.com}.
   * @param oauthToken The user's OAuth token.
   * @param debug Whether to enable debug logging on the connection.
   *
   * @return An authenticated SMTPTransport that can be used for SMTP
   *     operations.
   */
  public static SMTPTransport connectToSmtp(String host,
                                            int port,
                                            String userEmail,
                                            String oauthToken,
                                            boolean debug) throws Exception {
    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.starttls.required", "true");
    props.put("mail.smtp.sasl.enable", "true");
    props.put("mail.smtp.sasl.mechanisms", "XOAUTH2");
    props.put(OAuth2SaslClientFactory.OAUTH_TOKEN_PROP, oauthToken);
    Session session = Session.getInstance(props);
    session.setDebug(debug);

    final URLName unusedUrlName = null;
    SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
    // If the password is non-null, SMTP tries to do AUTH LOGIN.
    final String emptyPassword = "";
    transport.connect(host, port, userEmail, emptyPassword);

    return transport;
  }

}
