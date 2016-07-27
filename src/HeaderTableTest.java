import junit.framework.TestCase;

/**
 * 
 *  @fileName :   .HeaderTableTest.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月1日 下午7:54:41
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

public class HeaderTableTest extends TestCase {

	public void testHeaderTable() {
		fail("Not yet implemented");
	}

	public void testGetHead() {
		fail("Not yet implemented");
	}

	public void testSetHead() {
		fail("Not yet implemented");
	}

	public void testInsert() {
		HeaderTable ht = new HeaderTable();
		Header h = new Header(new Item(),1,null);
		ht.insert(h);
		System.out.println(ht.getHead().length);
		
	}

}
