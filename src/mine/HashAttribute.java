package mine;


public class HashAttribute {
	int numHashCode;//total number of values all the attributes can be
	int hashattr[];
	int attr, value;
	int attrvalue[];
	public HashAttribute(int []attrvalue){
		int numAttr = attrvalue.length;
		for(int i=0;i<numAttr;i++){
			numHashCode += attrvalue[i];
		}
		hashattr = new int[numHashCode];
		this.attrvalue = attrvalue;
	}
	public void increase(int attr, int value, int count) {
		int hashcode = getHashCode(attr, value);
		hashattr[hashcode] += count;		
	}
	public int getHashCode(int attr, int value) {
		int hashcode = 0;
		if(attr<0){
			return -1;
		}
		else
			for(int i=0;i<attr;i++){
				hashcode += attrvalue[i];
			}
		hashcode += value;
		return hashcode;
	}

	public void transfromHashCode(int hashcode) {
		value = hashcode;
		int i = 0;
		for(;i<numHashCode;i++){
			if(value<attrvalue[i])
				break;
			else
				value -= attrvalue[i];
		}
		attr = i;
	}
	public int getAttr(){
		return attr;
	}
	public int getValue(){
		return value;
	}

}
