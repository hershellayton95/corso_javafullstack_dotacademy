package configlesson;

import java.io.File;
import java.io.FileReader;
import java.lang.annotation.Annotation;
import java.util.Properties;

public class MyApplicationMain {

	public static void main(String[] args) {
		
		String configFileName = null;
		File file = null;
		
		if(args.length > 0) {
			for(String arg: args) {
				if(arg.split("=")[0].equals("config")) {
					configFileName = arg.split("=")[1];
					break;
				}
			}
		}
		if(configFileName == null) {
			configFileName = "./resources/config.properties";
		}
		
		file = new File(configFileName);
		
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Config.setDefaultProp(prop);
		Config cfg = Config.get();
		
		System.out.println(prop.get("port"));
		System.out.println(prop.containsKey("greetings"));
		System.out.println(prop.getOrDefault("hostname", "127.0.0.1"));
		prop.putIfAbsent("hostname", "127.0.0.2");
		System.out.println(prop.get("hostname"));
		
	}

}
