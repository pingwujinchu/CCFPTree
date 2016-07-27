package mine;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class Tree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 256354584445345069L;
	TreeNode root;
	int numClass;

	public Tree(int numClass) {
		this.numClass = numClass;
		root = new TreeNode(-1,-1, numClass);
		root.classcount = new int[numClass];
		for(int i=0;i<numClass;i++){
			root.classcount[i] = 0;
		}
	}
	public int countnode() {
		int nodecount[] = new int[1];
		printtree(root, nodecount);
		System.out.println(nodecount[0]);
		return nodecount[0];
	}
	private void printtree(TreeNode tn, int nodecount[]) {
		if(tn==null)
			return;
		Iterator<TreeNode> it = (Iterator<TreeNode>) tn.child.iterator();
		while(it.hasNext()){
			TreeNode t = it.next();
			nodecount[0]++;
			System.out.println(t.father.attr+"  "+t.father.value+"*******"+t.attr+"   " +t.value+"    "+t.classcount[0]);
			printtree(t, nodecount);
		}

	}

	/*
	 * build the CCFP tree with the Instances, Classes and the built header table
	 * @headertable the built header table
	 */

	public void treebuild(Instances instances, Instances onlyClass, FastVector[] headertable,Tree[] tree) {
		int numInstances = instances.numInstances();
		int classLabel;
		Instance inst;
		for(int i=0;i<numInstances;i++){
			classLabel = (int)onlyClass.instance(i).value(0);
			TreeNode currentnode = tree[classLabel].root;
			inst = instances.instance(i);
			for(int j=0;j<headertable[classLabel].size();j++){
				HeaderNode hn = (HeaderNode) headertable[classLabel].elementAt(j);
				TreeNode childnode = new TreeNode(hn.attr,hn.value,numClass);
				if(hn.containedBy(inst)){
					Iterator<TreeNode> it = currentnode.child.listIterator();
					int flag = 0;
					while(it.hasNext()){
						childnode = it.next();
						//if currentnode has a child hold the same item with hn, increase its class count
						if(childnode.equal(hn)){
							childnode.classcount[0]++;
							hn.classcount[0]++;
							flag = 1;
							break;
						}
					}
					//if currentnode does not have a child hold the same item with hn, create a new child
					if(flag==0){
						childnode = new TreeNode(hn.attr,hn.value,numClass);
						for(int c=0;c<numClass;c++){
							childnode.classcount[c]=0;
						}
						childnode.classcount[0] = 1;
						hn.addLink(childnode);
						hn.classcount[0]++;
						currentnode.addChild(childnode);
						childnode.father = currentnode;
					}
					currentnode = childnode;					
				}
			}
		}

	}
	public void contreebuild(FastVector cpblist, FastVector headertable) {
		int numCpb = cpblist.size();
		CpbItemSet cpbItem;
		for(int i=0;i<numCpb;i++){
			TreeNode currentnode = root;
			cpbItem = (CpbItemSet)cpblist.elementAt(i);
			//for all the header node from last to first
			for(int j=0;j<headertable.size();j++){
				HeaderNode hn = (HeaderNode) headertable.elementAt(j);
				TreeNode childnode = new TreeNode(hn.attr,hn.value,numClass);
				if(hn.containedBy(cpbItem)){
					Iterator<TreeNode> it = currentnode.child.listIterator();//it traverse all the child of currentnode
					int flag = 0;
					while(it.hasNext()){//while currentnode has childs
						childnode = it.next();
						//if currentnode has a child hold the same item with hn, increase its class count
						if(childnode.equal(hn)){
							for(int c=0;c<numClass;c++){
								childnode.classcount[c]+= cpbItem.class_count[c];;
								hn.classcount[c]+= cpbItem.class_count[c];
							}
							flag = 1;
							break;
						}
					}
					//if currentnode does not have a child hold the same item with hn, create a new child
					if(flag==0){
						childnode = new TreeNode(hn.attr,hn.value,numClass);
						for(int c=0;c<numClass;c++){
							childnode.classcount[c]=cpbItem.class_count[c];
							hn.classcount[c]+= cpbItem.class_count[c];
						}
						hn.addLink(childnode);
						currentnode.addChild(childnode);
						childnode.father = currentnode;
					}
					currentnode = childnode;					
				}
			}

		}

	}
	public boolean hasOnePath() {
		LinkedList<TreeNode> child = root.child;
		TreeNode tn;
		while(child!=null){
			if(child.size()>1){
				return false;
			}
			else if(child.size()!=0){
				tn = child.getFirst();
				child = tn.child;				
			}
			else
				break;
		}
		return true;
	}
}
