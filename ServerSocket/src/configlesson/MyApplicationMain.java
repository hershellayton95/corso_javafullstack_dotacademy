package configlesson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Properties;

import listener.Listener;

public class MyApplicationMain {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
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
		
		Listener listener = new Listener();
		
		listener.start();
	}

}
