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
