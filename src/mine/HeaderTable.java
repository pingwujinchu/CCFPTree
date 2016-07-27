package mine;

import java.util.Enumeration;

import weka.associations.ItemSet;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class HeaderTable {

	public FastVector[] buildTreeHead(Instances instances,Instances onlyClasses, int numClass, int necSupport,int[]classNumber) throws Exception{
		FastVector[] kSets = new FastVector[numClass];

		// find item sets of length one
		for(int i = 0 ; i < numClass ; i++){
			kSets[i] = ItemSet.singletons(instances);
			upDateCounters(kSets,i, instances,onlyClasses);
			kSets[i] = ItemSet.deleteItemSets(kSets[i], necSupport*classNumber[i]/instances.numInstances(),
					classNumber[i]);
		}
		// check if a item set of length one is frequent, if not delete it
		if (kSets.length == 0)
			return null;
		FastVector[] ht = new FastVector[numClass];
	
		for(int i = 0 ; i < numClass ; i++){
//		System.out.println(kSets[i].size());
		ht[i] = Transform(kSets[i], numClass);
		System.out.println(ht[i]);
		quicksort(ht[i],0,ht[i].size()-1);
		}
		return ht;
	}
	private void upDateCounters(FastVector[] itemSets,int classValue, Instances instances,Instances onlyClass) {
		for (int i = 0; i < instances.numInstances(); i++) {
			Enumeration enu = itemSets[classValue].elements();
			if(classValue != onlyClass.instance(i).value(0)){
				continue;
			}
			while (enu.hasMoreElements()) 
				((ItemSet)enu.nextElement()).upDateCounter(instances.instance(i));
		}

	}
	private FastVector Transform(FastVector kSets, int numClass) {
		FastVector ht = new FastVector();
		ItemSet ls;
		HeaderNode hn;
		for(int i=0;i<kSets.size();i++){
			hn = new HeaderNode(1);
			ls = (ItemSet) kSets.elementAt(i);
			int items[] = ls.items();
			for(int k=0;k<items.length;k++){
				if (items[k]!=-1){
					hn.attr = k;
					hn.value = items[k];
					hn.count = ls.counter();
				}
			}
			ht.addElement(hn);
		}
		return ht;
	}

	private void quicksort(FastVector headertable, int start, int end) {
		if  (start < end ){
			int k = partition(headertable, start, end );
			quicksort(headertable, start, k - 1);
			quicksort(headertable, k + 1, end);
		}
	}

	private int partition(FastVector headertable, int start, int end) {
		int pivot = ((HeaderNode)headertable.elementAt(start)).count;
		int r = end;
		int l = start;
		while(l<r){
			while(r>l&&((HeaderNode)headertable.elementAt(r)).count<=pivot){
				r--;
			}
			while(r>l&&((HeaderNode)headertable.elementAt(l)).count>=pivot){
				l++;
			}
			swap(headertable,l,r);
		}
		swap(headertable,start,l);
		return l;
	}

	private void swap(FastVector sets, int l, int r) {
		HeaderNode tmp = (HeaderNode)sets.elementAt(l);
		sets.setElementAt(sets.elementAt(r), l);
		sets.setElementAt(tmp, r);

	}

	public FastVector buildConTreeHead(int[] hashattr, Instance instance, int numClass,
			int necSupport, int[]attrvalue) {
		FastVector ht = new FastVector();
		HeaderNode hn;
		int size = hashattr.length;
		HashAttribute ha = new HashAttribute(attrvalue);
		for(int i=0;i<size;i++){
			int count = hashattr[i];
			if(count > necSupport){
				hn = new HeaderNode(numClass);
				ha.transfromHashCode(i);
				hn.attr = ha.getAttr();
				hn.value = ha.getValue();
				hn.count = count;
				if(hn.containedBy(instance)){
					ht.addElement(hn);
				}
			}
		}
		quicksort(ht, 0, ht.size()-1);
		return ht;
	}

}
