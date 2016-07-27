package mine;

import weka.core.FastVector;
import weka.core.Instance;

public class ConCCFP {
	FastVector cpblist;
	Tree t;
	int numClass;

	public ConCCFP(FastVector cpblist, int numClass) {
		this.cpblist = cpblist;
		this.numClass = numClass;
	}
	FastVector buildConTreeHead(int[] hashattr, Instance instance, double minsup, double minconv, int necSupport, int []attrvalue){
		HeaderTable ht = new HeaderTable();
		FastVector headertable = ht.buildConTreeHead(hashattr, instance, numClass, necSupport, attrvalue);
		return headertable;
	}

	public Tree contreeBuild(FastVector headertable) {
		t = new Tree(numClass);
		t.contreebuild(cpblist,headertable);
		return t;
		
	}

}
