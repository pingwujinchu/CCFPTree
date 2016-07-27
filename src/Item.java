import weka.core.Attribute;

/**
 * 
 *  @fileName :   .Item.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月1日 下午6:18:16
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

public class Item {
	private Attribute attr;
	private double value;
	
	
	public Item(Attribute attr, double value) {
		super();
		this.attr = attr;
		this.value = value;
	}
	
	
	public Item() {
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
	
	public boolean equal(Object item){
		boolean isEqual = false;
		if(item instanceof Item){
			Item it = (Item)item;
			if(item!=null&&this.attr.toString().equals(it.getAttr().toString())&&this.value==it.getValue()){
				isEqual = true;
			}
		}else{
			return false;
		}
		return isEqual;
	}
}