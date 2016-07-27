import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 *  @fileName :   .ConditionalPatternBases.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月9日 下午9:13:52
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

public class ConditionalPatternBases {
		private ClassCount cc;
		private List cpb;
		public ConditionalPatternBases(ClassCount cc, List cpb) {
			super();
			this.cc = cc;
			this.cpb = cpb;
		}
		public ConditionalPatternBases() {
			super();
			this.cpb = new ArrayList();
		}
		
		public ClassCount getCc() {
			return cc;
		}
		public void setCc(ClassCount cc) {
			this.cc = cc;
		}
		public List getCpb() {
			return cpb;
		}
		public void setCpb(List cpb) {
			this.cpb = cpb;
		}
}
