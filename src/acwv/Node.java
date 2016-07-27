package acwv;

import java.util.List;

/**
 * 
 *  @fileName :   acwv.TreeNode.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年5月10日 下午12:24:49
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

public class Node {
	private int attr;
	private int value;
	private Node parent;
	private List<Node> child;
	private int class_count[];
	
	public int getAttr(){
		return attr;
	}
	
	public int getValue(){
		return value;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public List<Node> getChild(){
		return child;
	}
	
	public int[] getClassCount(){
		return class_count;
	}

	public Node(int attr, int value, Node parent, List<Node> child, int[] class_count) {
		super();
		this.attr = attr;
		this.value = value;
		this.parent = parent;
		this.child = child;
		this.class_count = class_count;
	}
}
