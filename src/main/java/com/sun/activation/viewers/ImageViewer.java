/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)ImageViewer.java	1.9 07/05/14
 */

package com.sun.activation.viewers;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.CommandObject;
import javax.activation.DataHandler;

public class ImageViewer extends Panel implements CommandObject {
    // UI Vars...
    private ImageViewerCanvas canvas = null;
    
    // File Vars
    //    private InputStream data_ins = null;
    private Image image = null;
    private DataHandler _dh = null;
    
    private boolean DEBUG = false;
    /**
     * Constructor
     */
    public ImageViewer(){
	
	// create the ImageViewerCanvas
	canvas = new ImageViewerCanvas();
	add(canvas);
    }
    /**
     * Set the DataHandler for this CommandObject
     * @param DataHandler the DataHandler
     */
    public void setCommandContext(String verb, DataHandler dh)	throws IOException{
	_dh = dh;
	this.setInputStream( _dh.getInputStream() );
    }
    //--------------------------------------------------------------------
    
    /**
     * Set the data stream, component to assume it is ready to
     * be read.
     */
    private void setInputStream(InputStream ins) throws IOException {
	MediaTracker mt = new MediaTracker(this);
	int bytes_read = 0;
	byte data[] = new byte[1024];
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
	while((bytes_read = ins.read(data)) >0)
	    baos.write(data, 0, bytes_read);
	ins.close();
	
	// convert the buffer into an image
	image = getToolkit().createImage(baos.toByteArray());
	
	mt.addImage(image, 0);
	
	try {
	    mt.waitForID(0);
	    mt.waitForAll();
	    if(mt.statusID(0, true ) != MediaTracker.COMPLETE){
		System.out.println("Error occured in image loading = " +
				   mt.getErrorsID(0));
		
	    }
	    
	}
	catch(InterruptedException e) {
	    throw new IOException("Error reading image data");
	}
	
	canvas.setImage(image);
	if(DEBUG)
	    System.out.println("calling invalidate");
	
    }
    //--------------------------------------------------------------------
    public void addNotify(){
	super.addNotify(); // call the real one first...
	this.invalidate();
	this.validate();
	this.doLayout();
    }
    //--------------------------------------------------------------------
    public Dimension getPreferredSize(){
	return canvas.getPreferredSize();
    }

}











