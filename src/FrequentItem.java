import java.util.Map;

import weka.core.Attribute;

/**
 * 
 *  @fileName :   .FrequentItem.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月1日 下午4:55:18
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

public class FrequentItem{
		private Attribute attr;
		private double value;
		
		private int numTotle;
//		private Map<Double,Integer>classCountMap;
		
		
		public FrequentItem(Attribute attr, double value, int numTotle) {
			super();
			this.attr = attr;
			this.value = value;
			this.numTotle = numTotle;
//			this.classCountMap = classCountMap;
		}
		
		
		public FrequentItem() {
			super();
		}


		public Attribute getAttr() {
			return attr;
		}
		public void setAttr(Attribute attr) {
			this.attr = attr;
		}
		public double getValue() {
			return value;
		}
		public void setValue(double value) {
			this.value = value;
		}
		public int getNumTotle() {
			return numTotle;
		}
		public void setNumTotle(int numTotle) {
			this.numTotle = numTotle;
		}
//		public Map<Double, Integer> getClassCountMap() {
//			return classCountMap;
//		}
//		public void setClassCountMap(Map<Double, Integer> classCountMap) {
//			this.classCountMap = classCountMap;
//		}
	}
	
	
