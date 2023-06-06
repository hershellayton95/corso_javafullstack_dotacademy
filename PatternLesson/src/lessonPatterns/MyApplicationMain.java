package lessonPatterns;



public class MyApplicationMain {

	private MySingleton singleton = MySingleton.getInstance();
	
	public static void main(String[] args) {

		MyApplicationMain myApplicationMain1 = new MyApplicationMain();
		myApplicationMain1.singleton.eseguiProcesso();
		
		
		MyApplicationMain myApplicationMain2 = new MyApplicationMain();
		myApplicationMain2.singleton.eseguiProcesso();
//		System.out.println(MySingleton.myNumber);
		
		
//		MySingleton mysingleton = new MySingleton();
		MySingleton mysingleton = MySingleton.getInstance();
		System.out.println(MySingleton.getInstance());
		System.out.println(MySingleton.getInstance().getMyNumber());
		MySingleton.getInstance().eseguiProcesso();
//		System.out.println(mysingleton.myNumber);
//		System.out.println(mysingleton.getMyNumber());
	}

}
