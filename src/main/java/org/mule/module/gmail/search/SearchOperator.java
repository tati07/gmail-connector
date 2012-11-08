/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.gmail.search;

import com.google.code.javax.mail.search.ComparisonTerm;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public enum SearchOperator {
	
	LE(ComparisonTerm.LE),
    LT(ComparisonTerm.LT),
    EQ(ComparisonTerm.EQ),
    NE(ComparisonTerm.NE),
    GT(ComparisonTerm.GT),
    GE(ComparisonTerm.GE);
	
    private int term;
    
    private SearchOperator(int term) {
    	this.term = term;
    }
	public int getTerm() {
		return this.term;
	}

}
