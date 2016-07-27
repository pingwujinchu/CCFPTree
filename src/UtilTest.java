import junit.framework.TestCase;

/**
 * 
 *  @fileName :   .UtilTest.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月10日 下午10:40:47
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

public class UtilTest extends TestCase {

	public static void main(String[]args) {
		ConditionalPatternBases[] test = new ConditionalPatternBases[10];
		for(int i =0 ; i < 10 ; i++){
			ClassCount cc = new ClassCount();
			cc.setCount((int)(Math.random()*10));
			test[i] = new ConditionalPatternBases();
			test[i].setCc(cc);
		}
		Util.quickSort(test, 0, 9);
		
		for(int i = 0 ; i < test.length ; i++){
			System.out.println(test[i].getCc().getCount());
		}
	}

	public void testPartition() {
		fail("Not yet implemented");
	}

	public void testSwap() {
		fail("Not yet implemented");
	}

	public void testGetPivot() {
		fail("Not yet implemented");
	}

}
