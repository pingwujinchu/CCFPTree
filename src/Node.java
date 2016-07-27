import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @fileName : .Node.java
 *
 * @version : 1.0
 *
 * @see { }
 *
 * @author : fan
 *
 * @since : JDK1.4
 * 
 *        Create date : 2016年2月25日 下午8:31:40 Last modified time :
 * 
 *        Test or not : No Check or not: No
 *
 *        The modifier : The checker :
 * 
 * @describe : ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED 2016
 */

public class Node {
	private Item itemName; // 节点名字
	private List classLabel_count;// 节点的类标签
	private Node nodeLink; // 下一个节点
	private Node parentNode; // 父节点
	public List<Node> child;

	public Item getItemName() {
		return itemName;
	}

	public void setItemName(Item itemName) {
		this.itemName = itemName;
	}

	
	public List getClassLabel_count() {
		return classLabel_count;
	}

	public void setClassLabel_count(List classLabel_count) {
		this.classLabel_count = classLabel_count;
	}

	
	public Node getNodeLink() {
		return nodeLink;
	}

	public void setNodeLink(Node nodeLink) {
		this.nodeLink = nodeLink;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public Node(Item itemName, List classLabel_count, Node nextNode, Node parentNode) {
		super();
		this.itemName = itemName;
		this.classLabel_count = classLabel_count;
		this.nodeLink = nextNode;
		this.parentNode = parentNode;
		child = new ArrayList();
	}

	public Node(FrequentItem fi){
		 
		this.itemName = new Item(fi.getAttr(),fi.getValue());
		this.nodeLink = null;
		this.parentNode = null;
		this.classLabel_count = new ArrayList();
		child = new ArrayList();
	}
	
	public Node() {
		super();
		this.itemName = new Item();
		nodeLink = null;
		this.parentNode = null;
		this.classLabel_count = new ArrayList();
		child = new ArrayList();
	}
	
	public void addChild(Node n){
		this.child.add(n);
	}
	
}
