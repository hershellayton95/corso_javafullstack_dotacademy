package generics;

public abstract class APair<A , B> implements Pair<A, B> {

	public void doSomething() {
		System.out.println(this);
	}
	
}
