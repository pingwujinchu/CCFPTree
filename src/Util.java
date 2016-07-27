
/**
 * 
 *  @fileName :   .Util.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年3月10日 下午9:36:12
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

public class Util {
		public static void quickSort(Object[]obj,int start,int end){
			if(end<=start){
				return;
			}
			if(obj instanceof ConditionalPatternBases[]){
				int index = getPivot(obj,start,end);
//				System.out.println(index);
				int tp = partition(obj,start,end);
				if(start<tp){
					quickSort(obj,start,tp-1);
				}
				if(end>tp){
					quickSort(obj,tp+1,end);
				}
			}
		}
		
		public static int  partition(Object[]obj,int start,int end){
			int index = start;
			
			while(start<end){
			while(end>start&&((ConditionalPatternBases)obj[end]).getCc().getCount()< ((ConditionalPatternBases)obj[index]).getCc().getCount()){
				end--;
			}
			while(start<end&&((ConditionalPatternBases)obj[start]).getCc().getCount()>= ((ConditionalPatternBases)obj[index]).getCc().getCount()){
				start++;
			}
			
			if(start>=end){
				break;
			}
			
			swap(obj,start,end);
			}
			if(((ConditionalPatternBases)obj[start]).getCc().getCount()>= ((ConditionalPatternBases)obj[index]).getCc().getCount()){
				swap(obj,start,index);
			}
			
			index = start;
			return index;
		}
		
		
		public static void swap(Object[]obj,int i , int j){
		
			Object tem = obj[i];
			obj[i] = obj[j];
			obj[j] = tem;
			
		}
		
		
		public static int getPivot(Object [] obj,int start,int end){
			int index = start;
			if(obj instanceof ConditionalPatternBases[]){
				int startClassCount = ((ConditionalPatternBases)obj[start]).getCc().getCount();
				int midClassCount = ((ConditionalPatternBases)obj[(end+start)/2]).getCc().getCount();
				int endClassCount = ((ConditionalPatternBases)obj[end]).getCc().getCount();
				if(startClassCount>midClassCount){
					if(startClassCount<endClassCount){
						return start;
					}else{
						if(midClassCount>endClassCount){
							return (start+end)/2;
						}else{
							return end;
						}
					}
				}else{
					if(midClassCount<endClassCount){
						return (start+end)/2;
					}else{
						if(startClassCount>endClassCount){
							return start;
						}else{
							return end;
						}
					}
				}
				
			}
			return index;
		}
		
		public static void heapSort(Object [] obj,String str){
			if(obj instanceof Integer[]){
			   buildHeap(obj, str);
			}
		}
		
		public static void buildHeap(Object[]obj,String str){
			for(int i =(obj.length-2)/2 ; i >= 0 ;i--){
			   fillDown(obj, str, i,obj.length-1);
			}
		}
		
		public static void fillDown(Object[]obj,String str,int current,int maxIndex){
			if(obj instanceof Integer[]){
				
			}
		}
}
