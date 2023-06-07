package lessonQueue;

import lessonQueue.entities.Persons;

public class ProcessEnQueue extends Thread {
 
	private Queue queue = null;
	
	public ProcessEnQueue (Queue queue) {
		this.queue=queue;
	}

	@Override
	public void run() {

		Persons p1 = new Persons();
		p1.setId("xx1");
		p1.setFirstname("paolo");
		p1.setLastname("rossi");
		queue.put(p1);
		
		p1 = new Persons();
		p1.setId("xx2");
		p1.setFirstname("giovanni");
		p1.setLastname("verdi");
		queue.put(p1);
		
		p1 = new Persons();
		p1.setId("xx2");
		p1.setFirstname("sabrina");
		p1.setLastname("gialli");
		queue.put(p1);
		
	}
	
	
	
	
	
	
}
