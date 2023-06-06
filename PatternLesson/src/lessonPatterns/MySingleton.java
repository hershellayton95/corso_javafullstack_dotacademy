package lessonPatterns;

public class MySingleton {

	public static Integer myNumber = null;	
	
	private static MySingleton instance = null;
	// lock è un oggetto generico che ci serve a creare un punto di blocco per chi vuole usare l'istanza
	private static Object lock = new Object(); 
	
	static {
		myNumber = 30;
		//variabili statiche o di sistema e logica
	}
	
	
	public Integer getMyNumber() {
		return myNumber;
	}
	
	public static MySingleton getInstance() {
		if(instance == null) {
			synchronized (lock) {
				if(instance == null) {
					instance = new MySingleton();
				}
			}
		}
		return instance;
	}
	
	public void eseguiProcesso() {
		synchronized (lock) { // synchronized in un helper che è anonimo di solito non si mette!
			System.out.println("Esecuzione processo!");
		}
	}
	
	
}
