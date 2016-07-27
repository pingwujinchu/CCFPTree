package mine;

import java.io.Serializable;
import java.util.LinkedList;


public class TreeNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1776671077375905607L;
	public int attr;
	public int value;
	public TreeNode father;
	public LinkedList<TreeNode> child;
	public int classcount[];
	public TreeNode(int attr, int value, int numClass){
		this.attr = attr;
		this.value = value;
		child = new LinkedList<TreeNode>();
		classcount = new int[numClass];
	}
	public boolean equal(HeaderNode hn) {
		if(hn.attr == this.attr&&hn.value==this.value)
			return true;
		else
			return false;
	}
	public void addChild(TreeNode tnode) {
		child.add(tnode);
		
	}
	public int count() {
		int len = classcount.length;
		int count = 0;
		for(int i=0;i<len;i++){
			count += classcount[i];
		}
		return count;
	}

}
