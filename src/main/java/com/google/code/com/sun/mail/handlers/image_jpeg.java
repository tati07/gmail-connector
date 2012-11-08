/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.handlers;

import com.google.code.javax.activation.ActivationDataFlavor;

/**
 * DataContentHandler for image/jpeg.
 */
public class image_jpeg extends image_gif {
    private static ActivationDataFlavor myDF = new ActivationDataFlavor(
	java.awt.Image.class,
	"image/jpeg",
	"JPEG Image");

    protected ActivationDataFlavor getDF() {
	return myDF;
    }
}
