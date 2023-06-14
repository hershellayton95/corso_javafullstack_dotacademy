package converters;

import java.util.HashMap;
import java.util.Map;

import entities.Person;

public abstract class APersonConverter<T> implements PersonConverter<T>{

	private static Map<String, PersonConverter<? extends Person>> map = null;
	
	private static Object lock = new Object();
	
	
	@SuppressWarnings("unchecked")
	public PersonConverter<T> createInstance(Class<PersonConverter<?>> clazz){
	
//		Type type =(Type) clazz;
//		ParameterizedType pt = (ParameterizedType) type;
//		@SuppressWarnings({ "rawtypes" })
//		Class clazz2 = (Class) pt.getActualTypeArguments()[0];
//		
//		try {
//			return (PersonConverter<T>) clazz2.newInstance();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			return (PersonConverter<T>) clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public PersonConverter<T> getInstance(String converterNamer, Class<PersonConverter<?>> clazz) {
		if(map == null) {
			synchronized (lock) {
				if(map == null) {
					map =  new HashMap<String, PersonConverter<? extends Person>>();
				} 
			}
		}
		if(map.get(converterNamer) == null) {
			synchronized (lock) {
				if(map.get(converterNamer) == null) {
					PersonConverter<T> converter = null;
					converter = this.createInstance(clazz);
					if(converter != null) {
						map.put(converterNamer, (PersonConverter<? extends Person>)createInstance(clazz));
					}
				} 
			}
		}
		return (PersonConverter<T>) map.get(converterNamer);
	}
	
}
