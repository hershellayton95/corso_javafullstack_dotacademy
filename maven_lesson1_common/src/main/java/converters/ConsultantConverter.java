package converters;

import java.util.StringTokenizer;

import entities.Consultant;

public class ConsultantConverter extends APersonConverter<Consultant> {

	public Consultant parse(String value, Consultant instance) {
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
			
//			String computer = stringTokenizer.nextToken();
//			instance.setComputerKind(computer);

			String id = stringTokenizer.nextToken();
			instance.setId(id);
			
		}
		return instance;
	}

	public String convert(Consultant instance) {
		
		String result = "";
		
		result = instance.getFirstname()+
				","+instance.getLastname()+
				","+instance.getAge()+
				","+instance.getJob()+
//				","+instance.getComputerKind()+
				","+instance.getId();
		
		return result;
	}

}
