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
		
		Person p = null;
		
		Map<String,InstanceType> personMap = Config.get().getInstanceTypes();
		
		Set<Entry<String, InstanceType>> entries = personMap.entrySet();
		
		for(Entry<String, InstanceType> entry: entries) {
			String key = entry.getKey();
			if(personValue.contains(key)) {
				converter = (PersonConverter) Class.forName(((InstanceType) entry.getValue()).getConverterClass()).newInstance();
				p = (Person) Class.forName(((InstanceType) entry.getValue()).getClazz()).newInstance();
				break;
			}
		}
		
		if(converter == null) {
			throw new UnsupportedRecordException();
		}
		
		converter = converter.getInstance(personValue, converter.getClass());
		Person person = (Person) converter.parse(personValue, p);
		return person;
	}
	
}
