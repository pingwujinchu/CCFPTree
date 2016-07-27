import java.util.List;

/**
 * 
 *  @fileName :   .Header.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月1日 下午6:30:51
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

public class Header {
	private Item item;
	private int count;
	private Node head_of_node_link;

	
	
	public Header(Item item, int count, Node head_of_node_link) {
		super();
		this.item = item;
		this.count = count;
		this.head_of_node_link = head_of_node_link;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Node getHead_of_node_link() {
		return head_of_node_link;
	}

	public void setHead_of_node_link(Node head_of_node_link) {
		this.head_of_node_link = head_of_node_link;
	}

}
