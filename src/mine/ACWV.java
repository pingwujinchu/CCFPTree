
package mine;

import weka.associations.LabeledItemSet;
import weka.classifiers.Classifier;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class ACWV extends Classifier{
	public double minsup = 0.01;
	public double minconv = 1.1;
	public int ruleNumLimit = 100000;
	double[] classValue;
	int[] classCount;
	double [] voted;
	int numClass;
	static int buildCount = 0;
	Instances m_instances;
	Instances m_onlyclass;
	FastVector m_hashtables = new FastVector();
	public Instances m_onlyClass;
	int clIndex=0;
	int numAttr=0;
	CCFP fp;
	Tree[] t;
	FastVector []headertable;
	private int necSupport, necMaxSupport;		// minimum support
	int attrvalue[];//store number of values each attribute can be

	public void buildClassifier (Instances instances)throws Exception
	{ 

		double upperBoundMinSupport=1;
		buildCount++;
		// m_instances does not contain the class attribute
		m_instances = LabeledItemSet.divide(instances, false);

		// m_onlyClass contains only the class attribute
		m_onlyClass = LabeledItemSet.divide(instances, true);
		Calculation cal = new Calculation();
		cal.calSupport(minsup, upperBoundMinSupport, instances.numInstances());
		necSupport = cal.getNecSupport();
		attrvalue = cal.calAttrValue(m_instances);
		numClass=m_onlyClass.numDistinctValues(0);//number of classValue
		numAttr = m_instances.numAttributes();
		t = new Tree[numClass];
		if(buildCount>1){		
			fp = new CCFP(m_instances, m_onlyClass,minsup, minconv, necSupport, ruleNumLimit, attrvalue);
			headertable = fp.buildHeaderTable(numClass, necSupport);
			
			t = fp.buildTree(headertable);
//			for(int i = 0 ; i < numClass ; i++){
//				System.out.println(t[i].numClass);
//				t[i].countnode();
//			}
//			t.countnode();
		}
	}

	public double classifyInstance(Instance instance)
	{	
		int max = 0;
		if(buildCount>1){
			double[] vote = new double[numClass];
			vote = fp.vote(instance, headertable);
			voted = vote;
//			System.out.println(vote);
			max = findMax(vote);
		}
		return max;
	}

	public double[]getVoted(){
		return voted;
	}
	
	private int findMax(double[] d)
	{
		int l=d.length;
		int iMax=0;
		double temp=d[0];
		for(int i=1;i<l;i++)
		{
			if(d[i]>temp)
			{
				iMax=i;
				temp=d[i];
			}
		}
		return iMax;
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
		
//		String[] arg4 ={"-t","data/vehicleD.arff"};
//		runClassifier(new ACWV(), arg4);
//		

//		String[] arg34={"-t","data/wine.arff"};
//		runClassifier(new ACWV(), arg34);
//		
//		String[] arg35={"-t","data/taeD.arff"};
//		runClassifier(new ACWV(), arg35);
//		
//		String[] arg36={"-t","data/irisD.arff"};
//		runClassifier(new ACWV(), arg36);
//		
//		String[] arg37={"-t","data/balance-scaleD.arff"};
//		runClassifier(new ACWV(), arg37);

//		String[] arg38={"-t","data/postoperative-patient-dataD.arff"};
//		runClassifier(new ACWV(), arg38);
		
//		String[] arg39={"-t","data/hypothyroidD.arff"};
//		runClassifier(new ACWV(), arg39);
//		
		String[] arg40={"-t","data/sonarD.arff"};
		runClassifier(new ACWV(), arg40);
	}

	public FastVector[] getCCFPhead() {
		return headertable;
	}

	public Tree[] getCCFPTree(){
		return t;
	}
}

