
/**
 * 
 *  @fileName :   .ClassCount.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月1日 下午9:30:42
 *  Last modified time :
 *	
 * 	Test or not : No
 *	Check or not: No
 *
 * 	The modifier :
 *	The checker	: 
 *	 
 *  @describe :
 *  ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED 2016
*/

public class ClassCount {
	private double classValue;
	private int count;
	public double getClassValue() {
		return classValue;
	}
	public void setClassValue(double classValue) {
		this.classValue = classValue;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ClassCount(double classValue, int count) {
		super();
		this.classValue = classValue;
		this.count = count;
	}
	public ClassCount() {
		super();
	}
	
	
}
