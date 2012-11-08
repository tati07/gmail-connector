/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.event;

import java.util.EventObject;

/**
 * Common base class for mail events, defining the dispatch method.
 *
 * @author Bill Shannon
 */

public abstract class MailEvent extends EventObject {
    private static final long serialVersionUID = 1846275636325456631L;

    public MailEvent(Object source) {
        super(source);
    }

    /**
     * This method invokes the appropriate method on a listener for
     * this event. Subclasses provide the implementation.
     */
    public abstract void dispatch(Object listener);
}
