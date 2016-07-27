package mine;

import java.io.Serializable;

import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class CCFP implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8035375214153683682L;
	Instances instances, onlyClass;
	double minsup, minconv;
	int ruleNumLimit, numRule;
	int numAttr, numClass, numInstances;
	double []classSup;
	int necSupport;
	int []attrvalue;
	boolean terminal;
	private int depth;
	int [] classNumber;

	public CCFP(Instances insts, Instances onlyclass, double minsup, double minconv, int necSupport,
			int ruleNumLimit, int[]attrvalue) {
		this.instances = insts;
		this.onlyClass = onlyclass;
		this.minsup = minsup;
		this.minconv = minconv;
		this.necSupport = necSupport;
		this.ruleNumLimit = ruleNumLimit;
		numRule = 0;
		numAttr = insts.numAttributes();
		numClass = onlyclass.numDistinctValues(0);
		numInstances = insts.numInstances();
		classSup = calClassSup(onlyclass);
		this.attrvalue = attrvalue;
		classNumber = new int[numClass];
		for(int i = 0 ; i < onlyClass.numInstances() ; i++){
			classNumber[(int)onlyClass.instance(i).numValues()] ++;
		}
	}

	private double[] calClassSup(Instances onlyclass2) {
		double class_sup[] = new double[numClass];
		int class_count[] = new int[numClass];
		int class_label, num_instances = onlyclass2.numInstances();
		for(int i=0;i<num_instances;i++){
			class_label = (int) onlyclass2.instance(i).value(0);
			class_count[class_label]++;
		}
		for(int j=0;j<numClass;j++){
			class_sup[j] = (double)class_count[j]/num_instances;
		}
		return class_sup;
	}

	public Tree[] buildTree(FastVector []headertable) throws Exception{
		Tree []t = new Tree[numClass];
		for(int i = 0 ; i < numClass ; i++){
			t[i] = new Tree(1);
		}
		t[0].treebuild(instances, onlyClass, headertable,t);
		return t;
	}

	public double[] vote(Instance instance, FastVector[] headertable) {
		double votePro[] = new double[numClass];
		int[] numHeaderNode = new int[numClass];
		for(int i = 0 ; i < numClass ; i++){
			numHeaderNode[i] = headertable[i].size();
		}
		for(int k = 0 ; k < numClass ; k++){
		numRule = 0;
		double conv;
		HeaderNode hn;
		FastVector prefix = new FastVector();
		CpbList cpbList = new CpbList(attrvalue, numClass);
		FastVector cpblist;
		int len;
		boolean nofit;
		terminal = false;
		for(int i=numHeaderNode[k]-1; i>=0;i--){
			hn = (HeaderNode)headertable[k].elementAt(i);
			nofit = true;
			len = 1;
			cpblist = new FastVector();
			prefix = new FastVector();
			if(hn.containedBy(instance)){
//				for(int j=0;j<numClass;j++){
					if(hn.classcount[0]>necSupport*classNumber[k]/numInstances){
						nofit = false;
						conv = compConv(hn, k);
						if(conv>=minconv){
							votePro[k] += calWeight(conv, len, numAttr);
							numRule++;
							if(numRule>ruleNumLimit){
								terminal = true;
							}
						}
//					}
				}
			}
			if(nofit)
				continue;
			cpblist = cpbList.genCpblist(instance, headertable[k], i);
			ConCCFP cfp = new ConCCFP(cpblist, numClass);
			FastVector conheadertable = cfp.buildConTreeHead(cpbList.hashAttribute.hashattr, instance, minsup, minconv, necSupport, attrvalue);
			Tree t = cfp.contreeBuild(conheadertable);
			prefix.addElement(hn);
			if(t.root.child.size()>0){
				depth = 1;
				ccfpGrow(prefix, t, conheadertable, instance, votePro);
				System.out.println(depth);
				if(terminal==true){
					return votePro;
				}
			}
		}
		}
		return votePro;
	}

	//this method is different from description of the paper
	private double calWeight(double conv, int rulelen, int size) {
		double weight = 0;
		double d = size - rulelen;
		if (d == 0)
			d = 0.01;
		//weight = conv /d;
		weight = conv*rulelen;
		return weight;
	}

	private double compConv(HeaderNode hn, int j) {
		double conf = (double)hn.classcount[0]/hn.count;
		if(conf==1)
			conf = 0.999;
		return (double)(1-classSup[j])/(1-conf);
	}

	private int cal(int m,int n){
		double result = 1;
		if (n == 0)
			return (int)result;
		else{

			double mm = m;
			double nn;
			if (m-n > n){
				nn = n;
			}
			else
				nn = m - n;
			while (nn > 0){
				result = result * ( mm / nn) ;
				mm--;
				nn--;
			}
		}
		return (int)result;
	}

	private void ccfpGrow(FastVector prefix, Tree t, FastVector headertable,
			Instance instance, double[] votePro) {
		depth ++;
		HeaderNode hn;
		double conv, w;
		int len,num=1;
		CpbList cpbList = new CpbList(attrvalue, numClass);
		FastVector cpblist = new FastVector();
		FastVector curprefix;
		boolean nofit,flag;
		//if the tree t has only one path
		if(t.hasOnePath()==true){
			//from the last header node to the first
			int size = headertable.size();
			flag = true;
			for(int i=0;i<size;i++){
				hn = (HeaderNode)headertable.elementAt(i);
				//for each class label cl, j is the index of cl
				for(int j=0;j<numClass;j++){
					if(hn.classcount[j]>necSupport){
						flag  =false;
						conv = compConv(hn,j);
						if(conv>=minconv)
							for(int k=0;k<=i;k++){
								len = prefix.size()+k+1;
								w = calWeight(conv,len,numAttr);// weight of the rule
								num = cal(i,k);
								votePro[j]+=num*w;
								numRule += num;
								if(numRule>ruleNumLimit){
									terminal = true;
									return;
								}
							}
					}
				}
				if (flag){
					break;
				}
			}
		}
		//else the tree has more than one path
		else
		{
			for(int i=headertable.size()-1;i>=0;i--){
				nofit = true;
				hn = (HeaderNode)headertable.elementAt(i);
				curprefix = new FastVector();
				for(int k=0;k<prefix.size();k++){
					curprefix.addElement(prefix.elementAt(k));
				}
				curprefix.addElement(hn);
				len = curprefix.size();
				for(int j=0;j<numClass;j++){	
					if(hn.classcount[j]>necSupport){
						nofit = false;
						conv = compConv(hn,j);
						if(conv>=minconv){
							votePro[j] += calWeight(conv,len,numAttr);
							numRule++;
						}
						if(numRule>ruleNumLimit){
							terminal = true;
							return;
						}
					}
				}
				if(nofit)
					continue;
				cpblist = cpbList.genCpblist(instance, headertable, i);
				ConCCFP cfp = new ConCCFP(cpblist, numClass);
				FastVector conheadertable = cfp.buildConTreeHead(cpbList.hashAttribute.hashattr, instance, minsup, minconv, necSupport, attrvalue);
				Tree tree = cfp.contreeBuild(conheadertable);
				if(tree.root.child.size()>0){
					ccfpGrow(curprefix, tree, conheadertable, instance, votePro);
					if(numRule>ruleNumLimit){
						terminal = true;
						return;
					}
				}
			}
		}
	}
	public FastVector []buildHeaderTable(int numClass, int necSupport) throws Exception {
		HeaderTable ht = new HeaderTable();
		FastVector []headertable = ht.buildTreeHead(instances,onlyClass, numClass, necSupport,classNumber);
		return headertable;
	}
}
