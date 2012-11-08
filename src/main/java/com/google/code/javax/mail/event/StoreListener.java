/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.event;


/**
 * This is the Listener interface for Store Notifications.
 *
 * @author John Mani
 */

public interface StoreListener extends java.util.EventListener {

   /**
    * Invoked when the Store generates a notification event.
    *
    * @see StoreEvent#ALERT
    * @see StoreEvent#NOTICE
    */
   public void notification(StoreEvent e);
}
