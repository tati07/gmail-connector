/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.pop3;

/**
 * Result of POP3 STAT command.
 */
class Status {
    int total = 0;		// number of messages in the mailbox
    int size = 0;		// size of the mailbox
};
