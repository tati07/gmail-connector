package org.mule.module.gmail.search;

import java.io.Serializable;

import org.mule.module.gmail.model.Flag;

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
