import junit.framework.TestCase;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


/**
 * 
 *  @fileName :   .CCFPTreeTest.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月2日 上午10:35:05
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

class CCFPTreeTest extends TestCase {

	public void testCCFPTree() {
		fail("Not yet implemented");
	}

	public void testConstructCCFPTree() throws Exception {
	}

	public void testCcPlus() {
		fail("Not yet implemented");
	}

	public void testIsInInstance() {
		fail("Not yet implemented");
	}

	public void testIsInNode() {
		fail("Not yet implemented");
	}

	public void testGetRChild() {
		fail("Not yet implemented");
	}

	public void testBuildHeadTable() {
		fail("Not yet implemented");
	}

	public void testGetSortedFrequentItem() {
		fail("Not yet implemented");
	}

	public void testSortFastVector() {
		fail("Not yet implemented");
	}

	public void testGetMax() {
		fail("Not yet implemented");
	}

	public void testAttrValueIsInFrequentItems() {
		fail("Not yet implemented");
	}

	public void testClassIsIn() {
		fail("Not yet implemented");
	}

	public void testPrintTree() {
		fail("Not yet implemented");
	}

	public void testMain() {
		fail("Not yet implemented");
	}

	public void testGetHt() {
		fail("Not yet implemented");
	}

	public void testSetHt() {
		fail("Not yet implemented");
	}

	public void testGetRoot() {
		fail("Not yet implemented");
	}

	public void testSetRoot() {
		fail("Not yet implemented");
	}
	
	
	public static void main(String [] args) throws Exception{
		
		DataSource loaderTrain = new DataSource("data/TEST.arff");
		DataSource loaderTest = new DataSource("data/temIns.arff");
		Instances ins = new Instances(loaderTrain.getDataSet());
		Instances insT = new Instances(loaderTest.getDataSet());
		
		int attrNum = ins.numAttributes();
		
		ACWV wv = new ACWV();
		Instance temIns = ins.instance(ins.numInstances()-3);
		wv.buildClassifier(ins);
		double v =wv.classifyInstance(temIns);
		double [] result = wv.getVoted();
//		for(int i = 0 ; i < result.length ; i++){
//			System.out.println(result[i]);
//		}
//		System.out.print(wv.C(5, 3));
	 }

}
