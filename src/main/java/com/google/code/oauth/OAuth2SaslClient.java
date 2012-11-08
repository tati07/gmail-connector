/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.oauth;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;


/**
 * An OAuth2 implementation of SaslClient.
 */
class OAuth2SaslClient implements SaslClient {

  private final String oauthToken;
  private final CallbackHandler callbackHandler;

  private boolean isComplete = false;

  /**
   * Creates a new instance of the OAuth2SaslClient. This will ordinarily only
   * be called from OAuth2SaslClientFactory.
   */
  public OAuth2SaslClient(String oauthToken, CallbackHandler callbackHandler) {
    this.oauthToken = oauthToken;
    this.callbackHandler = callbackHandler;
  }

  public String getMechanismName() {
    return "XOAUTH2";
  }

  public boolean hasInitialResponse() {
    return true;
  }

  public byte[] evaluateChallenge(byte[] challenge) throws SaslException {
    if (isComplete) {
      // Empty final response from server, just ignore it.
      return new byte[] { };
    }

    NameCallback nameCallback = new NameCallback("Enter name");
    Callback[] callbacks = new Callback[] { nameCallback };
    try {
      callbackHandler.handle(callbacks);
    } catch (UnsupportedCallbackException e) {
      throw new SaslException("Unsupported callback: " + e);
    } catch (IOException e) {
      throw new SaslException("Failed to execute callback: " + e);
    }
    String email = nameCallback.getName();

    byte[] response = String.format("user=%s\1auth=Bearer %s\1\1", email, oauthToken).getBytes();
    isComplete = true;
    return response;
  }

  public boolean isComplete() {
    return isComplete;
  }

  public byte[] unwrap(byte[] incoming, int offset, int len)
      throws SaslException {
    throw new IllegalStateException();
  }

  public byte[] wrap(byte[] outgoing, int offset, int len)
      throws SaslException {
    throw new IllegalStateException();
  }

  public Object getNegotiatedProperty(String propName) {
    if (!isComplete()) {
      throw new IllegalStateException();
    }
    return null;
  }

  public void dispose() throws SaslException {
  }
}
