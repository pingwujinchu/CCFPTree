import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import weka.associations.Apriori;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * 
 * @fileName : .CCFPTree.java
 *
 * @version : 1.0
 *
 * @see { }
 *
 * @author : fan
 *
 * @since : JDK1.4
 * 
 *        Create date : 2016年2月25日 下午8:29:33 Last modified time :
 * 
 *        Test or not : No Check or not: No
 *
 *        The modifier : The checker :
 * 
 * @describe : ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED 2016
 */

public class CCFPTree {
	private HeaderTable ht;
	private Node root;
	private int numIns;
	int necSupport;
	
	public CCFPTree() {
		super();
		ht = new HeaderTable();
		root = new Node();
	}

	public CCFPTree constructCCFPTree(Instances ins,double minSup) throws Exception {
		FastVector sortedFreItems = this.getSortedFrequentItem(ins, minSup);
		buildHeadTable(sortedFreItems);
		root = new Node();
		Header curEle;
		Node R = root;
		numIns = ins.numInstances();
		
		for(int i = 0 ; i < ins.numInstances() ; i++){
			Instance inst = ins.instance(i);
			double cl = inst.value(ins.attribute(ins.numAttributes()-1));
			R = root;
			for(int j = 0 ; j < ht.getHead().length ; j++){
				curEle = ht.getHead()[j];
				if(this.isInInstance(curEle, inst)){

					if(this.isInList(R.child,curEle)){
						 R = this.getRChild(R.child,curEle);
						 this.ccPlus(R.getClassLabel_count(), cl);
					}else{
						Node m = new Node();
						m.setItemName(curEle.getItem());
						ClassCount cc = new ClassCount();
						cc.setClassValue(cl);
						cc.setCount(1);
						m.getClassLabel_count().add(cc);
						m.setParentNode(R);
						R.addChild(m);
						m.setNodeLink(curEle.getHead_of_node_link());
						curEle.setHead_of_node_link(m);
						R = m;
					}
				}
			}
		}
		
		return this;
	}
	
	private boolean isInList(List<Node> child, Header curEle) {
		// TODO method stub
		if(child == null){
			return false;
		}
		
		Iterator it = child.iterator();
		boolean isIn = false;
		while(it.hasNext()){
			Node n = (Node) it.next();
			if(n.getItemName().equal(curEle.getItem())){
				isIn = true;
				break;
			}
		}
		return isIn;
	}

	public void ccPlus(List l,double cl){
		boolean isIn = false;
		
		for(int i = 0 ;i < l.size() ; i++){
			ClassCount cc = (ClassCount) l.get(i);
			if(cc.getClassValue()==cl){
				l.remove(cc);
				cc.setCount(cc.getCount()+1);
				l.add(cc);
				isIn = true;
			}
		}
		
		if(!isIn){
			l.add(new ClassCount(cl,1));
		}
	}
	
	public boolean isInInstance(Header it,Instance ins){
		boolean isIn = false;
		Attribute attr = it.getItem().getAttr();
		double classValue = ins.value(attr);
		if(classValue == it.getItem().getValue()){
			isIn = true;
		}
		
		return isIn;
	}
	
	public boolean isInNode(Header h, Node child){
		
		boolean isIn = false;
		for(int i =0 ; i < ht.getHead().length ; i++){
			Node n = ht.getHead()[i].getHead_of_node_link();
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
	
	public Node getRChild(List<Node> child,Header curEle){
		
		Node result = null;
		Iterator it = child.iterator();
		while(it.hasNext()){
			Node n = (Node)it.next();
			if(n.getItemName().equals(curEle.getItem())){
				result = n;
				break;
			}
		}
		return result;

	}
	public void buildHeadTable(FastVector sortedFreItems){
		for(int i = 0 ; i < sortedFreItems.size() ; i++){
			FrequentItem fi = (FrequentItem) sortedFreItems.elementAt(i);
			Item it = new Item(fi.getAttr(),fi.getValue());
			Header h = new Header(it,fi.getNumTotle(),null);
			ht.insert(h);
		}
	}
	
	public FastVector getSortedFrequentItem(Instances ins,double minSup){          //获取频繁项集L(1)
		
		FastVector fv = new FastVector();
		int numAttr = ins.numAttributes();
//		for(int i = 0 ; i < numAttr ; i ++){
//			Attribute attr = ins.attribute(i);
//			ins.deleteWithMissing(attr);
//		}
		int numIns = ins.numInstances();
		for(int i = 0 ; i < numIns ; i ++){
			Instance in = ins.instance(i);
		    for(int j = 0 ; j < numAttr-1 ; j++){
		    	Attribute attr = ins.attribute(j);
		    	double value = in.value(attr);
		    	int index = -1;
		    	if((index = this.attrValueIsInFrequentItems(fv, attr, value))>=0){
		    	    FrequentItem fi = (FrequentItem) fv.elementAt(index);
		    	    fi.setNumTotle(fi.getNumTotle()+1);
		    	    fv.removeElementAt(index);
		    	    fv.addElement(fi);
		    	}else{
		    		FrequentItem fi = new FrequentItem(attr,value,1);
		    		fv.addElement(fi);
		    	}
		    }
		}
		fv = this.sortFastVector(fv);
		System.out.println(fv.size());
		for(int i = 0; i <fv.size() ; i++){
			FrequentItem fi =(FrequentItem) fv.elementAt(i);
			if(((double)fi.getNumTotle())/numIns<=minSup || ((double)fi.getNumTotle())/numIns == 1){
				fv.removeElementAt(i);
				i--;
			}
		}
		
		return fv;
	}
	
	public FastVector sortFastVector(FastVector fv){
		FastVector newVector = new FastVector();
		int numVector = fv.size();
		for(int i = 0 ; i < numVector ; i++ ){
			FrequentItem fi = this.getMax(fv);
			newVector.addElement(fi);
		}
		return newVector;
	}
	
	public FrequentItem getMax(FastVector fv){
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
	
	public int attrValueIsInFrequentItems(FastVector fv,Attribute attr,double value){
		int isIn = -1;
		for(int i = 0 ; i < fv.size() ; i++){
			FrequentItem fi = (FrequentItem) fv.elementAt(i);
			if(fi.getAttr()==attr && (double)fi.getValue() == value){
				isIn = i;
				break;
			}
		}
		return isIn;
	}
	
	public boolean classIsIn(Map m, double value){
		boolean isIn = false;
		Iterator it = m.keySet().iterator();
		while(it.hasNext()){
			double key = (double) it.next();
			if (value == key){
				isIn = true;
			}
		}
		return isIn;
	}

//	public CCFPTree buildTree(Instances ins,HeaderTable HT){
//		
//	}

	public void printTree(){
//		System.out.println("root  "+ root);
		printAll(root);
	}
	
	public void printAll(Node n){
		List l = n.child;
		Iterator<Node> it = l.iterator();
		
		while(it.hasNext()){
			Node t = it.next();
			if(t.getParentNode().getItemName().getAttr() != null){
			System.out.println(t.getParentNode().getItemName().getAttr().numValues()+"  "+t.getParentNode().getItemName().getValue()+"*******"+t.getItemName().getAttr().numValues()+"   " +t.getItemName().getValue()+"    "+((ClassCount)t.getClassLabel_count().get(0)).getClassValue()+":"+((ClassCount)t.getClassLabel_count().get(0)).getCount()+","+((ClassCount)t.getClassLabel_count().get(1)).getClassValue()+":"+((ClassCount)t.getClassLabel_count().get(1)).getCount());
			}
			printAll(t);
	   }

	}
	
	public static void main(String []arg) throws Exception{
		
	}

	public HeaderTable getHt() {
		return ht;
	}

	public void setHt(HeaderTable ht) {
		this.ht = ht;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
}
