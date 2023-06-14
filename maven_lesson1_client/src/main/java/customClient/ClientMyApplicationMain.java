package customClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import configs.Config;
import entities.CustomRequest;
import entities.CustomRespose;



public class ClientMyApplicationMain {

	private static final Logger log = LogManager.getLogger(ClientMyApplicationMain.class);
	
	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException, ClassNotFoundException {
		
		log.info("Client started!");
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

		log.debug("Config filename = " + configFileName);
		
		file = new File(configFileName);

		Properties prop = new Properties();
		try {
			prop.load(new FileReader(file));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Config.setDefaultProp(prop);

		Socket clientSocket= new Socket(Config.get().getHost(),Integer.parseInt(Config.get().getPort()));
		//PrintWriter printWriter	= new PrintWriter(clientSocket.getOutputStream(),true);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		//String requestLine = "getDataFile;my_csv.csv;pippo;sync;";

		CustomRequest customRequest = new CustomRequest();
		customRequest.setFileName("my_csv.csv");
		customRequest.setName("pippo");
		//domyprocess getDataFile
		customRequest.setProcess("getDataFile");
		customRequest.setType("sync");

		ObjectOutputStream objectWriter = new ObjectOutputStream(clientSocket.getOutputStream());
		objectWriter.writeObject(customRequest);



		ObjectInputStream inputStrem = new ObjectInputStream(clientSocket.getInputStream());

		CustomRespose customRespose = new CustomRespose();

		customRespose = (CustomRespose) inputStrem.readObject();

		System.out.println("Respose status = "+customRespose.getStatus());
		if( customRespose.getStatus().equals("OK")&& customRespose.getResult().size()>0 ) {

			System.out.println(customRespose.getResult().get(0));

		}else if(customRespose.getError() != ""){
			System.out.println("Respose Error = "+customRespose.getError());
		}

		//printWriter.println(customRequest);

		//	printWriter.println(requestLine);
		//		String line = bufferedReader.readLine();
		//		while(line!=null&& line.length() != 0) {
		//			
		//			System.out.println(line );
		//			line = bufferedReader.readLine();
		//		}



		bufferedReader.close();
		//	printWriter.close();
		clientSocket.close();

		log.info("Client finished!");
	}

}
