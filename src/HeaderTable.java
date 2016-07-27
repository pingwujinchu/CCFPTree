import java.util.List;

/**
 * 
 * @fileName : .HeaderTable.java
 *
 * @version : 1.0
 *
 * @see { }
 *
 * @author : fan
 *
 * @since : JDK1.4
 * 
 *        Create date : 2016年2月25日 下午8:35:55 Last modified time :
 * 
 *        Test or not : No Check or not: No
 *
 *        The modifier : The checker :
 * 
 * @describe : ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED 2016
 */

public class HeaderTable {
	private Header []head;
	
	
	public HeaderTable() {
		super();
	}

	public Header[] getHead() {
		return head;
	}

	public void setHead(Header[] head) {
		this.head = head;
	}
	
	public void insert(Header h){
		if(head==null){
			head = new Header[1];
			head[0] = h;
		}else{
			Header[]newHeader = new Header[head.length+1];
			for(int i = 0 ; i < head.length ; i++){
				newHeader[i] = head[i];
			}
			newHeader[head.length] = h;
			head = newHeader;
		}
	}
	
}
