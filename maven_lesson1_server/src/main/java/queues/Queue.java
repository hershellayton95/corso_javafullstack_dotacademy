package queues;

import java.util.LinkedList;

import entities.Person;


public class Queue {
	private  LinkedList<Person> list = new LinkedList<Person>();
	private Object lock = new Object();
	
	
	public void put(Person p) {
		synchronized (lock) {
			list.addFirst(p);
			lock.notify(); //notifyAll se aggiungo più persone (con un array)
		}
	}
	
	public Person get() throws InterruptedException {
		Person person = null;
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
