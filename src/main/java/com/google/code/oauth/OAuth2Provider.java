/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.oauth;

import java.security.Provider;

public class OAuth2Provider extends Provider {
    
	private static final long serialVersionUID = 1L;

    public OAuth2Provider() {
      super("Google OAuth2 Provider", 1.0, "Provides the XOAUTH2 SASL Mechanism");
      put("SaslClientFactory.XOAUTH2", "com.google.code.oauth.OAuth2SaslClientFactory");
    }
}
