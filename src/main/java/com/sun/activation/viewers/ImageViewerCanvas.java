/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * @(#)ImageViewerCanvas.java	1.4 07/05/14
 */

package com.sun.activation.viewers;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class ImageViewerCanvas extends Canvas
{
  private Image canvas_image = null;
  
  /**
   * The constructor
   */
  public ImageViewerCanvas()
    {
	
    }

  /**
   * set the image
   */
  public void setImage(Image new_image)
    {
      canvas_image = new_image;
      this.invalidate();
      this.repaint();
    }
  
  /**
   * getPreferredSize
   */
  public Dimension getPreferredSize()
    {
      Dimension d = null;
      
      if(canvas_image == null)
	{
	  d = new Dimension(200, 200);
	}
      else
	d = new Dimension(canvas_image.getWidth(this), 
			  canvas_image.getHeight(this));

      return d;
    }
  /**
   * paint method
   */
  public void paint(Graphics g)
    {

      if(canvas_image != null)
	g.drawImage(canvas_image, 0, 0, this);

    }
  
}
