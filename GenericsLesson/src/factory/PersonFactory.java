package factory;

import converter.ConsultantConverter;
import converter.EmployeeConverter;
import converter.PersonConverter;
import generics.Consultant;
import generics.Employee;
import generics.Person;

public class PersonFactory {

	@SuppressWarnings({ "unused", "unchecked" })
	public static Person createPerson(String personValue) throws Exception {
		
		@SuppressWarnings("rawtypes")
		PersonConverter converter = null;
		
		Person p = null;
		
		if(personValue.contains("employee")) {
			p = new Employee();
			converter = new EmployeeConverter();
		}else if(personValue.contains("consultant")) {
			p = new Consultant();
			converter = new ConsultantConverter();
		} else {
			throw new Exception("Unsupported person");
		}
		
		converter = converter.getInstance(personValue, converter.getClass());
		Person person = (Person) converter.parse(personValue, p);
		return person;
	}
	
}
