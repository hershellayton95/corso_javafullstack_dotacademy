package generics;

public class MyApplicationMain {
	public static void main(String[] args) {
		
		Person person = new Person();
		person.setId("xx1");
		person.setFirstname("paolo");
		person.setLastname("Verdi");
		
		Consultant consultant = new Consultant();
		consultant.setId("xx2");
		consultant.setFirstname("marco");
		consultant.setLastname("rossi");
		consultant.setComputerKind("Lenovo");
		
		Employee employee = new Employee();
		employee.setId("xx3");
		employee.setFirstname("giovanni");
		employee.setLastname("gialli");
		employee.setSmartphoneKind("Iphone");
		
		
//		KeyValue<String, Person> keyValue = new KeyValue<String, Person>(person.getId(), person);
////		KeyValue<String, Double> keyValue2 = new KeyValue<String, Double>("Costo", 2.5);
//		KeyValue<String, Person> keyValueConsultant = new KeyValue<String, Person>(consultant.getId(), consultant);
//		KeyValue<String, Person> keyValueEmployee = new KeyValue<String, Person>(employee.getId(), employee);
		
		// usiamo l'intefaccia così esponiamo solo i get e non i set come succedeva su
		Pair<String, Person> keyValue = new KeyValue<String, Person>(person.getId(), person);
		Pair<String, Person> keyValueConsultant = new KeyValue<String, Person>(consultant.getId(), consultant);
		Pair<String, Person> keyValueEmployee = new KeyValue<String, Person>(employee.getId(), employee);
		
		
		System.out.println(keyValue);
//		System.out.println(keyValue2);
		
//		System.out.println(keyValueConsultant);
//		System.out.println(keyValueEmployee);
		keyValueConsultant.doSomething();
		keyValueEmployee.doSomething();
		
////////manipolazione classi
//		Person.class.
//		person.getClass().
//		Class<Person> p = Person.class;
		
	}
}
