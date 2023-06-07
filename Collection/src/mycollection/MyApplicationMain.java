package mycollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mycollection.entities.Persons;

public class MyApplicationMain {

	public static void main(String[] args) {
		
		//collection
		////List
		/////ArrayList
		////Map
		

		/*
		 * per ora sono castate a List, quindi per vedere i metodi
		 * dovrei fare il cast ((ArrayList<Persons>) list) ad esempio
		 * 
		*/
		List<Persons> list = new ArrayList<Persons>();//posso manipolare e anche aggiongere un item in qualsiasi posizione
		List<Persons> list2 = new LinkedList<Persons>();//posso manipolare e anche aggiongere un item in qualsiasi posizione
		
		Map<String, Persons> map = new HashMap<String, Persons>(); // posso avere null
		Map<String, Persons> map2 = new Hashtable<String, Persons>(); // non posso avere null
		
		HashMap<String, Persons> map3 = (HashMap<String, Persons>)map;
		Map<String, Persons> map4 = (Map<String, Persons>)map3;
		
		
		
		Persons p1 = new Persons();
		p1.setId("xx1");
		p1.setFirstname("paolo");
		p1.setLastname("rossi");
		list.add(p1);
		map.put(p1.getId(), p1);
		
		p1 = new Persons();
		p1.setId("xx2");
		p1.setFirstname("giovanni");
		p1.setLastname("verdi");
		list.add(p1);
		map.put(p1.getId(), p1);
		
		p1 = new Persons();
		p1.setId("xx2");
		p1.setFirstname("sabrina");
		p1.setLastname("gialli");
		list.add(p1);
		map.put(p1.getId(), p1);
		
		
		for(Persons person : list) {
			System.out.printf("person id = %s person surname = %s \n", person.getId(), person.getLastname());
		}
		for(Persons person : (Persons[]) list.toArray(new Persons[0])) {
			System.out.printf("person id = %s person surname = %s \n", person.getId(), person.getLastname());
		}
		

		System.out.printf("person id = %s person surname = %s \n", list.get(1).getId(), list.get(1).getLastname());
		System.out.printf("person id = %s person surname = %s \n", map.get("xx1").getId(), map.get("xx1").getLastname());

	}

}
