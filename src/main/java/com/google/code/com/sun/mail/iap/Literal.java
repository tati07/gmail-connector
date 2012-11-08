/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.iap;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An interface for objects that provide data dynamically for use in
 * a literal protocol element.
 *
 * @author  Bill Shannon
 */

public interface Literal {
    /**
     * Return the size of the data.
     */
    public int size();

    /**
     * Write the data to the OutputStream.
     */
    public void writeTo(OutputStream os) throws IOException;
}
