package generics;

public class KeyValue<A, B extends Person> extends APair<A, B> {
	private A key = null;
	private B value = null;
	
	
	public KeyValue(A key, B value) {
		this.key = key;
		this.value = value;
	}


	public A getKey() {
		return key;
	}


	public void setKey(A key) {
		this.key = key;
	}


	public B getValue() {
		return value;
	}


	public void setValue(B value) {
		this.value = value;
	}


	@Override
	public String toString() {
		return "KeyValue [key=" + key + ", value=" + value + "]";
	}



	
}
