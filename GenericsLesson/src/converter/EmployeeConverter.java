package converter;

import java.util.StringTokenizer;

import generics.Employee;

public class EmployeeConverter extends APersonConverter<Employee> {

	@Override
	public Employee parse(String value, Employee instance) {
		if(value != null) {


			StringTokenizer stringTokenizer = new StringTokenizer(value, ",");

			String firstname = stringTokenizer.nextToken();
			instance.setFirstname(firstname);

			String lastname = stringTokenizer.nextToken();
			instance.setLastname(lastname);

			String age = stringTokenizer.nextToken();
			instance.setAge(Integer.parseInt(age));

			String job = stringTokenizer.nextToken();
			instance.setJob(job);
			
//			String smartphon = stringTokenizer.nextToken();
//			instance.setSmartphoneKind(smartphon);

			String id = stringTokenizer.nextToken();
			instance.setId(id);
			
		}
		return instance;
	}

	@Override
	public String convert(Employee instance) {
		
		String result = "";
		
		result = instance.getFirstname()+
				","+instance.getLastname()+
				","+instance.getAge()+
				","+instance.getJob()+
//				","+instance.getSmartphoneKind()+
				","+instance.getId();
		
		return result;
	}

}
