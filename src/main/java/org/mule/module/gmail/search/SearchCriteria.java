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
public class SearchCriteria implements Serializable {

	private static final long serialVersionUID = -2561581699482719127L;
	
	private SearchOperator operator;
	private String value;
	
	public SearchOperator getOperator() {
		return operator;
	}
	public void setOperator(SearchOperator operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
