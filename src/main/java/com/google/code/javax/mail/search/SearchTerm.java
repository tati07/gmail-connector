/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.search;

import java.io.Serializable;

import com.google.code.javax.mail.Message;

/**
 * Search criteria are expressed as a tree of search-terms, forming
 * a parse-tree for the search expression. <p>
 *
 * Search-terms are represented by this class. This is an abstract
 * class; subclasses implement specific match methods. <p>
 *
 * Search terms are serializable, which allows storing a search term
 * between sessions.
 *
 * <strong>Warning:</strong>
 * Serialized objects of this class may not be compatible with future
 * JavaMail API releases.  The current serialization support is
 * appropriate for short term storage. <p>
 *
 * <strong>Warning:</strong>
 * Search terms that include references to objects of type
 * <code>Message.RecipientType</code> will not be deserialized
 * correctly on JDK 1.1 systems.  While these objects will be deserialized
 * without throwing any exceptions, the resulting objects violate the
 * <i>type-safe enum</i> contract of the <code>Message.RecipientType</code>
 * class.  Proper deserialization of these objects depends on support
 * for the <code>readReplace</code> method, added in JDK 1.2.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public abstract class SearchTerm implements Serializable {

    private static final long serialVersionUID = -6652358452205992789L;

    /**
     * This method applies a specific match criterion to the given
     * message and returns the result.
     *
     * @param msg	The match criterion is applied on this message
     * @return		true, it the match succeeds, false if the match fails
     */

    public abstract boolean match(Message msg);
}
