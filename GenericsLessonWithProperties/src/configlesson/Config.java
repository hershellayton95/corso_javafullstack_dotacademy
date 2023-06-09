package configlesson;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class Config {

	private static Config instance = null;
	private static Object lock = new Object();
	private static Properties defaultProp = null;
	
	private String host=null;
	private String port=null;
	private Map<String, InstanceType> instanceTypes= new HashMap<String, InstanceType>();


	public Config(Properties prop) {

		this.host = prop.getProperty("host");
		this.port = prop.getProperty("port");
		
		String[] factoryTypes = prop.getProperty("factoryTypes").split(",");
		for(String type : factoryTypes) {
			instanceTypes.put(type, new InstanceType(type, prop.getProperty(type+".converterClass"), prop.getProperty(type+".class")));
		}

	}
	
	public static Config get() {
		
		if(instance == null) {
			synchronized (lock) {
				if(instance == null) {
					instance = new Config(defaultProp);
				}
			}
		}
		return instance;
	}

	public static Properties getDefaultProp() {
		return defaultProp;
	}
	
	public static void setDefaultProp(Properties prop) {
		defaultProp = prop;
	}
	
	public String getHost() {
		return host;
	}
  
	public String getPort() {
		return port;
	}

	public Map<String, InstanceType> getInstanceTypes() {
		return instanceTypes;
	}





}
