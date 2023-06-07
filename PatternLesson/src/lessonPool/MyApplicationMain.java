package lessonPool;

import lessonPool.entities.Persons;

public class MyApplicationMain {

	public static void main(String[] args) throws InterruptedException {
//		Pool pool = new Pool();
		Pool pool = Pool.getInstance();
		
		
		Persons p1 = new Persons();
		p1.setId("xx1");
		p1.setFirstname("paolo");
		p1.setLastname("rossi");
		pool.put(p1);
		
		p1 = new Persons();
		p1.setId("xx2");
		p1.setFirstname("giovanni");
		p1.setLastname("verdi");
		pool.put(p1);
		
		p1 = new Persons();
		p1.setId("xx2");
		p1.setFirstname("sabrina");
		p1.setLastname("gialli");
		pool.put(p1);
		

		System.out.println("pool size = " +  pool.size());
		Persons p = pool.get();
		System.out.println("pool size = " +  pool.size());
		Persons p2 = pool.get();
		System.out.println("pool size = " +  pool.size());
		Persons p3 = pool.get();
		System.out.println("pool size = " +  pool.size());
//		Persons p4 = pool.get();
//		System.out.println("pool size = " +  pool.size());
		pool.put(p);
		pool.put(p2);
		System.out.println("pool size = " +  pool.size());
		Persons p5 = pool.get();
		System.out.println("pool size = " +  pool.size());
		// se prendo tutte le persone in questo thread
		
		System.out.println("---------");
		System.out.println("---------");

		
		Pool employees = Pool.getInstance("employees");
		Pool consultants = Pool.getInstance("consultants");
		
		p1 = new Persons();
		p1.setId("xx1");
		p1.setFirstname("paolo");
		p1.setLastname("rossi");
		employees.put(p1);
		
		p1 = new Persons();
		p1.setId("xx2");
		p1.setFirstname("giovanni");
		p1.setLastname("verdi");
		consultants.put(p1);
		
		p1 = new Persons();
		p1.setId("xx2");
		p1.setFirstname("sabrina");
		p1.setLastname("gialli");
		employees.put(p1);
		
		System.out.println("pool employees size = " +  employees.size());
		System.out.println("pool consultants size = " +  consultants.size());
	}
}
