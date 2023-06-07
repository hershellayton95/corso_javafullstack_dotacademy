package lessonPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lessonPool.entities.Persons;

public class Pool {
	private List<Persons> list = new ArrayList<Persons>();
	//un lock per il singleton
	// il secondo per il Pool
	private static Object lock1 = new Object();
	private Object lock2 = new Object();
	
	private static Pool instance = null;
	
	private static Map<String, Pool> mapPool = null;
	
	public static Pool getInstance() {
		if(instance == null) {
			synchronized (lock1) {
				if(instance == null) {
					instance = new Pool();
				} 
			}
		}
		return instance;
	}
	
	public static Pool getInstance(String poolName) {
		if(mapPool == null) {
			synchronized (lock1) {
				if(mapPool == null) {
					mapPool =  new HashMap<String, Pool>();
				} 
			}
		}
		if(mapPool.get(poolName) == null) {
			synchronized (lock1) {
				if(mapPool.get(poolName) == null) {
					mapPool.put(poolName, new Pool());
				} 
			}
		}
		return mapPool.get(poolName);
	}
	
	public Persons get() throws InterruptedException {
		Persons person = null;
		if(list.size()>0) {
			synchronized (lock2) {
				if(list.size()>0) {
					person = list.remove(list.size()-1);
				} else {
					Thread.sleep(1000);
					person = get();
				}
			}
		} else {
			Thread.sleep(1000);
			person = get();
		}
		return person;
	}
	
	public void put(Persons p) {
		synchronized (lock2) {
			list.add(p);
			lock2.notify(); //notifyAll se aggiungo più persone (con un array)
		}
	}
	
	public int size() {
		return list.size();
	}
	
	
}
