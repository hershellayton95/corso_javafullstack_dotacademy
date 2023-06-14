package converters;

public interface PersonConverter<T> {
	public T parse(String value, T instance);
	public String convert(T instance);
	public PersonConverter<T> getInstance(String converterNamer, Class<PersonConverter<?>> clazz);
}
