

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import mine.Calculation;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;

/**
 * 
 *  @fileName :   .WV.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月2日 上午10:39:19
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

public class ACWV extends Classifier  implements OptionHandler,Serializable{
	private int numClass;
	private int numIns;
	private CCFPTree ccfptree;
	private double minSup = 0.01;
	protected Instances m_Instances;
	protected double minConv = 1.1;
	protected int ruleNum = 100000;
	protected HeaderTable headTab;
	protected Attribute classAttr;
	protected double[]classArray;
	protected double [] result;
	protected double []v;
	protected int curRuleNum;
	private int necSupport, necMaxSupport;		// minimum support
	private int numAttr;
	static int buildCount = 0;
	public int depth;
	
	@Override
	public void buildClassifier(Instances ins) throws Exception {
		// TODO method stub
		double upperBoundMinSupport=1;
		buildCount++;
		ins = new Instances(ins);
		Calculation cal = new Calculation();
		cal.calSupport(minSup, upperBoundMinSupport, ins.numInstances());
		necSupport = cal.getNecSupport();
//	    ins.deleteWithMissingClass();
	    numIns = ins.numInstances();
	    numClass = ins.attribute(ins.numAttributes()-1).numValues();
	    
	    classAttr = ins.attribute(ins.numAttributes()-1);
	    numAttr = ins.numAttributes();
	    if(buildCount>1){	
	    ccfptree = new CCFPTree();
	    ccfptree.constructCCFPTree(ins, minSup);
//	    ccfptree.printTree();
	    headTab = ccfptree.getHt();
	    }
	}
	
	public double[] getCalWeiVotResult(Instance ins,HeaderTable ht,double minSup, double minConv,int ruleNumLim){
		v = new double[numClass];
		for(int i=0 ; i<numClass;i++){
			v[i] = 0;
		}
		
		curRuleNum = 0;
		Header curEle = null;
		if(ht.getHead() == null){
			return null;
		}
		for(int i = ht.getHead().length-1 ; i>=0 ; i-- ){
			curEle = ht.getHead()[i];
			boolean nofit = true;
			int len = 1;
			if(curEle.getItem().getValue() == ins.value(curEle.getItem().getAttr())){
				for(int j = 0 ; j < numClass ; j++){
					double support = this.getSupport(numIns, j, curEle);
					double conviction = this.getConviction(numIns, support, curEle);
					if(support >= minSup && conviction >= minConv){
						nofit = false;
//						double w = this.calWeight(conviction, len, numAttr);   //????
						double w = 1 *conviction; 
						v[j] += w;
						curRuleNum++;
						if(curRuleNum >= ruleNumLim){
							return v;
						}
					}
				}
				
				if(nofit)
					continue;
				
				Set cpbL = this.genCPB(ins, ht, i);
				
				//test whether the result of genCPB is right.
				Iterator it = cpbL.iterator();
				while(it.hasNext()){
					ConditionalPatternBases cb = (ConditionalPatternBases) it.next();
					List path = cb.getCpb();
					Iterator it2 = path.iterator();
					while(it2.hasNext()){
						Item item = (Item) it2.next();
//						System.out.print("("+item.getAttr()+","+item.getValue()+")"+"---->");
					}
//					System.out.print(cb.getCc().getClassValue()+"  "+cb.getCc().getCount()+"\n");
				}
				
				ConditionalPatternBases [] cpbl = this.sortSet(cpbL);
				
//				for(int l = 0 ; l < cpbl.length ; l ++){
//				System.out.print(cpbl[l].getCc().getClassValue()+"  "+cpbl[l].getCc().getCount());
//				for(int k = 0 ; k < cpbl[l].getCpb().size() ; k++){
//					System.out.print(((Item)cpbl[l].getCpb().get(k)).getAttr()+"  "+((Item)cpbl[l].getCpb().get(k)).getValue());
//				}
//				System.out.print("\n");
//				}
				
				HeaderTable hTab = ConditionalTree.buildHeadTable(cpbl);
				ConditionalTree ctree = new ConditionalTree();
				ctree.contree_build(cpbl, hTab);
				ctree.printTree();
				List l = new ArrayList();
				l.add(curEle.getItem());
				depth = 1;
				this.CCFP_grouth(l, ctree, hTab, ins, minSup, minConv, ruleNumLim);
//				System.out.println(depth);
				if(curRuleNum >= ruleNumLim){
					return v;
				}
			}
		}
		return v;
	}

	private double calWeight(double conv, int rulelen, int size) {
		double weight = 0;
		double d = size - rulelen;
		if (d == 0)
			d = 0.01;
		//weight = conv /d;
		weight = conv*rulelen;
		return weight;
	}
	
	public ConditionalPatternBases [] sortSet(Set set){
		 ConditionalPatternBases []cpb = new ConditionalPatternBases[set.size()];
		 Iterator it = set.iterator();
		 int i = 0 ; 
		 while(it.hasNext()){
			 cpb[i++] = (ConditionalPatternBases) it.next();
		 }
		 Util.quickSort(cpb, 0, cpb.length-1);

         return cpb;
	}
	
	public double getSupport(int numIns,double classValue,Header head){
		double support = 0;
	    int count = 0;
	    Node n = head.getHead_of_node_link();
	    while(n!=null){
	    	List l = n.getClassLabel_count();
	    	for(int i = 0 ; i < l.size() ; i++){
	    		ClassCount cc = (ClassCount) l.get(i);
	    		if(cc.getClassValue()== classValue){
	    			count += cc.getCount();
	    		}
	    	}
	    	n = n.getNodeLink();
	    }
	    
		support = (double)count / numIns;
		return support;
	}
	
	public double getConviction(int numIns,double support,Header head){
		 double conViction = 0;
		 double countC = support*numIns;
		 double confidence = (double)countC/head.getCount();
		 if(confidence == 1){
			 confidence = 0.999;
		 }
		 conViction = (1-support)/(1-confidence);
		 return conViction;
	}
	
	public double getConfidence(double classValue,Header head){
		double confidence = 0;
		Node n = head.getHead_of_node_link();
		int countClass = 0;
		int countIns = head.getCount();
		
		while(n!=null){
			List l =n.getClassLabel_count();
			for(int i = 0 ; i < l.size() ; i++){
				ClassCount cc = (ClassCount) l.get(i);
				if(cc.getClassValue()==classValue){
					countClass += cc.getCount();
				}
			}
			n = n.getNodeLink();
		}
		double result = countClass/countIns;
		return result;
	}
	
	public Set genCPB(Instance ins,HeaderTable ht,int i){
		Node iNode = ht.getHead()[i].getHead_of_node_link();
	    Set cpbL = new HashSet();
	    ConditionalPatternBases cpb = new ConditionalPatternBases();
	    while(iNode!=null){
	    	List l = iNode.getClassLabel_count();
	    	for(int j = 0 ; j < l.size() ; j++){
	    		ClassCount cc = (ClassCount) l.get(j);
	    		cpb = new ConditionalPatternBases();
	    		cpb.setCc(cc);
	    		Node M = iNode.getParentNode();
	    		while(M.getItemName()!=null&&M.getParentNode()!=null){
	    			if(this.isInInstance(M.getItemName(), ins)){
	    				cpb.getCpb().add(M.getItemName());
	    			}
	    			M = M.getParentNode();
	    		}
	    		if(cpb.getCpb().size()>0){
	    			cpbL.add(cpb);
	    		}
	    	}
	    	iNode = iNode.getNodeLink();
	    }
	    return cpbL;
	}
	
	public boolean isInInstance(Item it,Instance ins){
		boolean isIn = false;
		Attribute attr = it.getAttr();
//		System.out.println(attr+"   "+it.getValue());
		double classValue = ins.value(attr);
		if(classValue == it.getValue()){
			isIn = true;
		}
		return isIn;
	}
	
	public int C(int i,int k){
		int result = 1;
		int div = 1;
		for(int l = i ; l>k ; l--){
			result*= l;
		}
		for(int m = i-k ; m>0 ; m--){
			div*=m;
		}
		if(div == 0){
			return 1;
		}
		return result / div;
		
	}
	
	public void CCFP_grouth(List prefixList, ConditionalTree tree,HeaderTable hTab,Instance ins, double minSup,double minConv,int ruleNumLimit){
		  depth++;
		  boolean nofit,flag;
		  Header curEle = null;
		  if(hTab.getHead()==null){
			  return;
		  }
		  if(tree.isOnePath()){
			   for( int i = hTab.getHead().length-1; i>=0 ; i--){
				   flag  =true;
				   curEle = hTab.getHead()[i];
				   for(int j = 0 ; j < numClass ; j++){
					   int clNum = 0;  //record the number of instances whose class label is cl
					   for(int k = 0 ; k < curEle.getHead_of_node_link().getClassLabel_count().size() ; k ++){
						   ClassCount cc =  (ClassCount) curEle.getHead_of_node_link().getClassLabel_count().get(k);
					       if(cc.getClassValue() == j){
					    	   clNum = cc.getCount();
					       }
					   }
					   double support = (double)clNum / numIns;
					   double conv = getConviction(numIns, support, curEle);
//					   System.out.println(j+"  support : "+support+"  conviction : "+conv);
					   
					   if(support >= minSup && conv >= minConv){
						   flag = false;
						   for(int m = 0 ; m <= i ; m ++){
							   int d = prefixList.size()+m+1;
							   double w = conv* d;
							   v[j] += C(i,m)*w;
							   curRuleNum += C(i,m);
							   if(curRuleNum >= ruleNumLimit){
								   v[j] -= (curRuleNum - ruleNumLimit)*w;
								   return;
							   }
						   }
					   }
				   }
				   if(flag){
					   break;
				   }
			   }
		   }else{
			   for(int i = hTab.getHead().length-1;i>=0;i--){
				   nofit = true;
				   curEle = hTab.getHead()[i];
				   prefixList.add(curEle.getItem());
				   for(int j = 0 ; j < numClass ; j++){
					   int clNum = 0;
					   Node n = hTab.getHead()[0].getHead_of_node_link();
					   while(n!=null){
						   List cclist = n.getClassLabel_count();
						   for(int s = 0; s < cclist.size() ;s++){
							   if(((ClassCount)cclist.get(s)).getClassValue()==j){
								   clNum += ((ClassCount)cclist.get(s)).getCount();
							   }
						   }
						   n = n.getNodeLink();
					   }
					   double support = (double)clNum / numIns;
					   double conv = getConviction(clNum, support, curEle);
					   if(support >= minSup && conv >= minConv){
						   nofit = false;
						   double w  = (prefixList.size()+1)*conv;
						  v[j] +=w;
						   curRuleNum ++;
						   if(curRuleNum >= ruleNumLimit){
							   return;
						   }
						   
					   }
				   }
				   if(nofit){
					   continue;
				   }
				 Set  cpbL = genCPB(ins,hTab,i);
				 ConditionalPatternBases [] cpbl = this.sortSet(cpbL);
				 HeaderTable hTab2 = ConditionalTree.buildHeadTable(cpbl);
				 ConditionalTree ctree2 = new ConditionalTree();
				 ctree2.contree_build(cpbl, hTab2);
				 if(hTab2.getHead()!=null){
					 CCFP_grouth(prefixList, ctree2, hTab2, ins, minSup, minConv, ruleNumLimit);
				     if(curRuleNum >= ruleNumLimit){
				    	return; 
				     }
				 }
			   }
		   }
	}

	@Override
	public double classifyInstance(Instance arg0) throws Exception {
		// TODO method stub
	    double classNum = 0 ;
	    if(buildCount>1){	
	    result = this.getCalWeiVotResult(arg0, headTab, minSup, minConv, ruleNum);
	    int index = 0;
	    double temValue = 0;
	    for(int i = 0 ; i < result.length ; i++){
	    	if(temValue < result[i]){
	    		index = i;
	    		temValue = result[i];
	    	}
	    }
	    classNum = index;
	    }
	    return classNum;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO method stub
		return super.clone();
	}

	@Override
	public String[] getOptions() {
		// TODO method stub
		 Vector result = new Vector();

		    //Add Hyperparameter value to options
		    result.add("-D");
		   
		    result.add("-S");
		    result.add("" + minSup);

		    result.add("-C");
		    result.add("" + minConv);

		    result.add("-R");
		    result.add("" + ruleNum);
		    result.add("-N");

		    //Add Prior Class to options

		    return (String[]) result.toArray(new String[result.size()]);
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		super.setOptions(options);
		// TODO method stub
		String minSupport = Utils.getOption("S", options);
	    if (minSupport.length() != 0) {
	        minSup = Double.parseDouble(minSupport);
	      }
	    String mincon = Utils.getOption("C", options);
	    
	    if (mincon.length() != 0) {
	        minConv = Double.parseDouble(mincon);
	      }
	}

	public double getMinSup() {
		return minSup;
	}

	public void setMinSup(double minSup) {
		this.minSup = minSup;
	}

	public Instances getM_Instances() {
		return m_Instances;
	}

	public void setM_Instances(Instances m_Instances) {
		this.m_Instances = m_Instances;
	}

	public double getMinConv() {
		return minConv;
	}

	public void setMinConv(double minConv) {
		this.minConv = minConv;
	}

	public int getRuleNum() {
		return ruleNum;
	}

	public void setRuleNum(int ruleNum) {
		this.ruleNum = ruleNum;
	}
	
	public double [] getVoted(){
		return result;
	}
	
	public static void main(String[] argv){
//		String[] arg ={"-t","data/annealD.arff"};
//		runClassifier(new ACWV(), arg);

//		String[] arg1 ={"-t","data/arrhythmiaD.arff"};
//		runClassifier(new ACWV(), arg1);
//
//		String[] arg2 ={"-t","data/carD.arff"};
//		runClassifier(new ACWV(), arg2);
//
//		String[] arg3 ={"-t","data/ecoliD.arff"};
//		runClassifier(new ACWV(), arg3);
//		
//		String[] arg4 ={"-t","data/vehicleD.arff"};
//		runClassifier(new ACWV(), arg4);
//		

		String[] arg34={"-t","data/wine.arff"};
		runClassifier(new ACWV(), arg34);	
//
//		String[] arg35={"-t","data/taeD.arff"};
//		runClassifier(new ACWV(), arg35);
//		
//		String[] arg36={"-t","data/irisD.arff"};
//		runClassifier(new ACWV(), arg36);

//		String[] arg37={"-t","data/balance-scaleD.arff"};
//		runClassifier(new ACWV(), arg37);
		
//		String[] arg38={"-t","data/postoperative-patient-dataD.arff"};
//		runClassifier(new ACWV(), arg38);
		
//		String[] arg39={"-t","data/sonarD.arff"};
//		runClassifier(new ACWV(), arg39);
		
//		String[] arg39={"-t","data/hypothyroidD.arff"};
//		runClassifier(new ACWV(), arg39);
	}
	
}
