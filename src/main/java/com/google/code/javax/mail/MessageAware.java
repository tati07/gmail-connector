/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail;

/**
 * An interface optionally implemented by <code>DataSources</code> to
 * supply information to a <code>DataContentHandler</code> about the
 * message context in which the data content object is operating.
 *
 * @see javax.mail.MessageContext
 * @see javax.activation.DataSource
 * @see javax.activation.DataContentHandler
 * @since	JavaMail 1.1
 */
public interface MessageAware {
    /**
     * Return the message context.
     */
    public MessageContext getMessageContext();
}
