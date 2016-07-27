import java.util.Iterator;
import java.util.List;
import java.util.Set;

import weka.core.FastVector;

/**
 * 
 *  @fileName :   .ConditionalTree.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月9日 下午9:39:43
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

public class ConditionalTree {
	private HeaderTable hTab;
	private Node root;
	
	public ConditionalTree contree_build(ConditionalPatternBases [] cpb,HeaderTable hTab){
		this.hTab = hTab;
		root = new Node();
		Header curEle;
		int num = cpb.length;
		Node R = root;
		for(int i = 0 ; i < num ; i++){
           	R = root;	
           	ConditionalPatternBases cp = cpb[i];
           	double cl = cp.getCc().getClassValue();
           	int classCount = cp.getCc().getCount();
           	for(int j = 0 ; j < hTab.getHead().length ; j++){
           		curEle = hTab.getHead()[j];
           		if(isInList(curEle.getItem(),cp.getCpb())){
           			if(this.isInNode(curEle,R)){
						 R = this.getRChild(curEle, R);
						 this.ccPlus(R.getClassLabel_count(), cl,classCount);
					}else{
						Node n = new Node();
						n.setItemName(curEle.getItem());
						ClassCount cc = new ClassCount();
						cc.setClassValue(cl);
						cc.setCount(classCount);
						n.getClassLabel_count().add(cc);
						n.setParentNode(R);
						n.setNodeLink(curEle.getHead_of_node_link());
						curEle.setHead_of_node_link(n);
						R = n;
					}
           		}
           	}
		}
		return this;
	}
	
	public void ccPlus(List l,double cl,int classCount){
		boolean isIn = false;
		for(int i = 0 ; i < l.size() ; i++){
			ClassCount cc = (ClassCount) l.get(i);
//			System.out.println(cc);
			if(cc.getClassValue()==cl){
				l.remove(cc);
				cc.setCount(cc.getCount()+classCount);
				l.add(cc);
				isIn = true;
			}
		}
		
		if(!isIn){
			l.add(new ClassCount(cl,classCount));
		}
	}
	
	public boolean isInNode(Header h, Node child){
		
		boolean isIn = false;
		for(int i =0 ; i < hTab.getHead().length ; i++){
			Node n = hTab.getHead()[i].getHead_of_node_link();
			while(n!=null){
				if(n.getParentNode() == child){
					if(h.getItem().equal(n.getItemName())){
						isIn = true;
					}
				}
				n = n.getNodeLink();
			}
		}
		return isIn;
	}
	
	public Node getRChild(Header h,Node R){
		
		Node result = null;
		for(int i =0 ; i < hTab.getHead().length ; i++){
			Node n = hTab.getHead()[i].getHead_of_node_link();
			while(n!=null){
				if(n.getParentNode() == R){
					if(h.getItem().equal(n.getItemName())){
						result = n;
					}
				}
				n = n.getNodeLink();
			}
		}
		return result;
	}
	
	public static boolean isInList(Item it,List s){
		boolean isIn = false;
		Iterator iterator = s.iterator();
		while(iterator.hasNext()){
			Item curr = (Item) iterator.next();
			if(it.equal(curr)){
				isIn = true;
			}
		}
		return isIn;
	}
	
	public static HeaderTable buildHeadTable(ConditionalPatternBases [] cpbl){
		FastVector fv = new FastVector();
		HeaderTable hTab = new HeaderTable();
		for(int i = 0 ; i < cpbl.length ; i++){
			List cpb = cpbl[i].getCpb();
			Iterator it = cpb.iterator();
			FrequentItem fi ;
			int index = 0 ;
			while(it.hasNext()){
				Item item = (Item) it.next();
				if((index = isInFastVector(fv, item))>=0){
					FrequentItem temFv = (FrequentItem) fv.elementAt(index);
					temFv.setNumTotle(temFv.getNumTotle()+cpbl[i].getCc().getCount());
//					continue;
				}
				else{
				fi = new FrequentItem();	
				fi.setAttr(item.getAttr());
				fi.setValue(item.getValue());
				fi.setNumTotle(fi.getNumTotle()+cpbl[i].getCc().getCount());
				fv.addElement(fi);
               
				}
			}
		}
		fv = sortFastVector(fv);
		
		for(int j = 0 ; j < fv.size() ; j++){
			FrequentItem fi =  (FrequentItem) fv.elementAt(j);
			Item it = new Item(fi.getAttr(),fi.getValue());
			Header h = new Header(it,fi.getNumTotle(),null);
			hTab.insert(h);
		}
		return hTab;
	}
	
	public static FastVector sortFastVector(FastVector fv){
		FastVector newVector = new FastVector();
		int numVector = fv.size();
		for(int i = 0 ; i < numVector ; i++ ){
			FrequentItem fi = getMax(fv);
			newVector.addElement(fi);
		}
		return newVector;
	}
	
	public static FrequentItem getMax(FastVector fv){
		int numVector = fv.size();
		int numTotle = 0;
		int index = 0;
		for(int i = 0 ; i < numVector ; i++ ){
			FrequentItem fi = (FrequentItem) fv.elementAt(i);
			int freq = fi.getNumTotle();
			if(freq > numTotle){
				numTotle = freq;
				index = i;
			}
		}
		FrequentItem result = (FrequentItem) fv.elementAt(index);
		fv.removeElementAt(index);
		return result;
	}
	
	public static int isInFastVector(FastVector fv, Item item){
		FrequentItem fi = null;
		int index = -1;
		for(int i = 0 ; i < fv.size() ; i++){
		    fi = (FrequentItem) fv.elementAt(i);
		    if(fi.getAttr().toString().equals(item.getAttr().toString())&&fi.getValue()==item.getValue()){
		    	index = i;
		    }
		}
		return index;
	}
	
	public void printTree(){
		if(hTab == null || hTab.getHead()==null){
			return ;
		}
//		System.out.println("root  "+ root);
		for(int i = 0 ; i < hTab.getHead().length; i++){
			Header h = hTab.getHead()[i];
			Node n = h.getHead_of_node_link();
//			System.out.print(h.getItem().getAttr()+"  "+h.getItem().getValue()+"  "+h.getCount());
			while(n!=null){
				StringBuffer classLableCount = new StringBuffer();
				List temL = n.getClassLabel_count();
				for(int j = 0 ; j <temL.size() ; j++){
					ClassCount cc = (ClassCount) temL.get(j);
					classLableCount.append(" "+temL.size()+"-->class："+cc.getClassValue()+" count: "+cc.getCount());
				}
				
//				System.out.print("  ("+n+classLableCount.toString()+" parent:"+n.getParentNode()+")");
				n = n.getNodeLink();
			}
//			System.out.print("\n");

		}
	}
	
	public boolean isOnePath(){
		boolean isOnePath = true;
		for(int i = 0 ; i < this.hTab.getHead().length ; i++){
			if(this.hTab.getHead()[i].getHead_of_node_link().getNodeLink()!=null){
				isOnePath = false;
			}
		}
		return isOnePath;
	}
}
