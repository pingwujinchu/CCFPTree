package mine;

import weka.associations.ItemSet;
import weka.core.FastVector;

public class CpbItemSet extends ItemSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7210872793774584202L;
	//private static final long serialVersionUID = 9153297436049397741L;
	public int count;
	public int class_count[];
	public static int numAttr;
	public static int numClass;

	public CpbItemSet(int totalTrans, int num_class, int num_attr) {
		super(totalTrans);
		numAttr = num_attr;
		numClass = num_class;
		count = 0;
		class_count = new int[numClass];
		m_items = new int[numAttr];
		for(int j=0;j<numAttr;j++){
			m_items[j] = -1;
		}
	}

	public static void upDateCounters(FastVector cpbItemSets) {

		CpbItemSet cpbItem;
		for (int i = 0; i < cpbItemSets.size(); i++) {
			cpbItem = (CpbItemSet) cpbItemSets.elementAt(i);
			cpbItem.upDateCounter((CpbItemSet) cpbItemSets.elementAt(i));
		}
	}

	private void upDateCounter(CpbItemSet cpbItemCheck) {
		if(containSameItem(cpbItemCheck)){
			for(int i=0; i<numClass;i++){
				count = count+cpbItemCheck.class_count[i];
			}
		}

	}

	private boolean containSameItem(CpbItemSet cpbItemCheck) {
		for(int i=0;i<numAttr;i++){
			if(m_items[i]!=cpbItemCheck.itemAt(i)){
				return false;
			}
		}
		return true;
	}

}
