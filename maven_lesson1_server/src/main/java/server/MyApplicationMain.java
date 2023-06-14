package server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import configs.Config;
import listeners.Listener;

public class MyApplicationMain {

	private static final Logger log = LogManager.getLogger(MyApplicationMain.class);
	
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
			configFileName = "./config.properties";
		}
		log.debug("Config file = " + configFileName);
		
		file = new File(configFileName);
		
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(file));
		} catch (Exception e) {
			log.error("Failed to load " + configFileName,e);
		}
		
		Config.setDefaultProp(prop);
		
		Listener listener = new Listener();
		
		listener.start();
		
		log.info("Server started");
	}
	
}
