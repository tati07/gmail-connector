/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.oauth;

/**
 * Represents a protocol that can be authenticated with XOAUTH.
 */
public enum XoauthProtocol {
  IMAP("imap"),
  SMTP("smtp");

  private final String name;

  XoauthProtocol(String name) {
    this.name = name;
  }

  /**
   * Returns the protocol name to be embedded in the XOAUTH URL.
   */
  public String getName() {
    return name;
  }
}
