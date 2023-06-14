package configs;

public class InstanceType {
	private String name = null;
	private String converterClass = null;
	private String clazz = null;
	
	
	public InstanceType(String name, String converterClass, String clazz) {
		this.name = name;
		this.converterClass = converterClass;
		this.clazz = clazz;
	}
	
	public String getName() {
		return name;
	}
	public String getConverterClass() {
		return converterClass;
	}
	public String getClazz() {
		return clazz;
	}
	
}
