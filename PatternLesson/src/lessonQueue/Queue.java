package lessonQueue;

import java.util.LinkedList;

import lessonQueue.entities.Persons;


public class Queue {
	private  LinkedList<Persons> list = new LinkedList<Persons>();
	private Object lock = new Object();
	
	
	public void put(Persons p) {
		synchronized (lock) {
			list.addFirst(p);
			lock.notify(); //notifyAll se aggiungo più persone (con un array)
		}
	}
	
	public Persons get() throws InterruptedException {
		Persons person = null;
		if(list.size()>0) {
			synchronized (lock) {
				if(list.size()>0) {
					person = list.removeLast();
				} else {
//					Thread.sleep(1000);  per una maggior pulizia posso bloccare il lock
					lock.wait(1000);
					person = get();
				}
			}
		} else {
			Thread.sleep(1000);
//			lock.wait(1000); qui il tread non è proprietario del lock perché non sto sincronizzando
			person = get();
		}
		return person;
	}

	
	public int size() {
		return list.size();
	}
	
}
