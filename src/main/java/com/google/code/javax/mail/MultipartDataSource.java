/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail;

import com.google.code.javax.activation.DataSource;

/**
 * MultipartDataSource is a <code>DataSource</code> that contains body
 * parts.  This allows "mail aware" <code>DataContentHandlers</code> to
 * be implemented more efficiently by being aware of such
 * <code>DataSources</code> and using the appropriate methods to access
 * <code>BodyParts</code>. <p>
 *
 * Note that the data of a MultipartDataSource is also available as
 * an input stream. <p>
 *
 * This interface will typically be implemented by providers that
 * preparse multipart bodies, for example an IMAP provider.
 *
 * @author	John Mani
 * @see		javax.activation.DataSource
 */

public interface MultipartDataSource extends DataSource {

    /**
     * Return the number of enclosed BodyPart objects.
     *
     * @return          number of parts
     */
    public int getCount();

    /**
     * Get the specified Part.  Parts are numbered starting at 0.
     *
     * @param index     the index of the desired Part
     * @return          the Part
     * @exception       IndexOutOfBoundsException if the given index
     *			is out of range.
     * @exception       MessagingException
     */
    public BodyPart getBodyPart(int index) throws MessagingException;

}
