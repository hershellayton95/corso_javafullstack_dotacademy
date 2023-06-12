package factory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import configlesson.Config;
import configlesson.InstanceType;
import configlesson.UnsupportedRecordException;
import converter.PersonConverter;
import generics.Person;

public class PersonFactory {

	@SuppressWarnings({ "unused", "unchecked", "rawtypes", "deprecation" })
	public static Person createPerson(String personValue) throws UnsupportedRecordException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		PersonConverter converter = null;
		
		Person person = null;
		
		Map<String,InstanceType> personMap = Config.get().getInstanceTypes();
		
		Set<Entry<String, InstanceType>> entries = personMap.entrySet();
		
		for(Entry<String, InstanceType> entry: entries) {
			String key = entry.getKey();
			if(personValue.contains(key)) {
				converter = (PersonConverter) Class.forName(((InstanceType) entry.getValue()).getConverterClass()).newInstance();
				person = (Person) Class.forName(((InstanceType) entry.getValue()).getClazz()).newInstance();
				break;
			}
		}
		
		if(converter == null) {
			throw new UnsupportedRecordException();
		}
		
		converter = converter.getInstance(personValue, converter.getClass());
		person = (Person) converter.parse(personValue, person);
		return person;
	}
	
}
