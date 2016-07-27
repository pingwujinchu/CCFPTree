

import weka.core.Instances;

public class Calculation {
	int necSupport, necMaxSupport;

	public void calSupport(double minsup, double upperBoundMinSupport, int numInstances) {

		double nextMinSupport = minsup * numInstances;
		double nextMaxSupport = upperBoundMinSupport * numInstances;
		if (Math.rint(nextMinSupport) == nextMinSupport) {
			necSupport = (int) nextMinSupport;
		} else {
			necSupport = Math.round((float) (nextMinSupport + 0.5));
		}
		if (Math.rint(nextMaxSupport) == nextMaxSupport) {
			necMaxSupport = (int) nextMaxSupport;
		} else {
			necMaxSupport = Math.round((float) (nextMaxSupport + 0.5));
		}		
	}

	public int getNecSupport() {
		return necSupport;
	}

	public int[] calAttrValue(Instances instances) {
		int numAttr = instances.numAttributes();
		int attrvalue[] = new int[numAttr];
		for(int i=0;i<numAttr;i++){
			attrvalue[i] = instances.attribute(i).numValues();
		}
		return attrvalue;
	}
}
