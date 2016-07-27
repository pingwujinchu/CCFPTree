package mine;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * 
 *  @fileName :   mine.Test.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月28日 上午10:45:44
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

public class Test {
	public static void main(String []args) throws Exception{
		DataSource loaderTrain = new DataSource("data/vote.arff");
//		DataSource loaderTest = new DataSource("data/temIns.arff");
		Instances ins = new Instances(loaderTrain.getDataSet());
//		Instances insT = new Instances(loaderTest.getDataSet());
		
		int attrNum = ins.numAttributes();
		
		ACWV wv = new ACWV();
		Instance temIns = ins.instance(ins.numInstances()-3);
		wv.buildClassifier(ins);
		double v =wv.classifyInstance(temIns);
		double [] result = wv.getVoted();
		for(int i = 0 ; i < result.length ; i++){
			System.out.println(result[i]);
		}
	}
}
