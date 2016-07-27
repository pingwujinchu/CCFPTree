package mine;

import java.io.Serializable;
import java.util.LinkedList;

import weka.core.Instance;

public class HeaderNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6192241785188784718L;
	int attr;
	int value;
	int count;
	LinkedList<TreeNode> link;
	int classcount[];
	
	public HeaderNode(int numClass){
		this.attr = -1;
		this.value = -1;
		this.count = 0;
		//link to the next node with the same attribute value in the tree
		link = new LinkedList<TreeNode>();
		//classcount is used to computer Support of a rule
		classcount = new int[numClass];
	}
	public HeaderNode(int attr, int value, int count, int numClass){
		this.attr = attr;
		this.value = value;
		this.count = count;
		link = new LinkedList<TreeNode>();
		classcount = new int[numClass];
	}
/*
 * judge if an instance covers the header node
 */
	public boolean containedBy(Instance instance) {
		   if (instance.isMissing(attr))
		        return false;
		   if (instance.value(attr) != value)
			   return false;
		    return true;
		  }
	
	public void addLink(TreeNode tn){
		link.add(tn);
	}
	public boolean containedBy(CpbItemSet cpbItem) {
		if(cpbItem.itemAt(attr)!=value)
				return false;
		return true;
	}
}
