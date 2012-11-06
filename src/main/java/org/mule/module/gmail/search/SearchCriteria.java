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
