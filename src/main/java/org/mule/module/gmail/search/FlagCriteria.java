/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.gmail.search;

import java.io.Serializable;


/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class FlagCriteria implements Serializable {

	private static final long serialVersionUID = 2483946826245503178L;
	
	private boolean set;
	private Flag flag;
	
	public boolean isSet() {
		return set;
	}
	public void setSet(boolean set) {
		this.set = set;
	}
	public Flag getFlag() {
		return flag;
	}
	public void setFlag(Flag flag) {
		this.flag = flag;
	}
	
	
}
