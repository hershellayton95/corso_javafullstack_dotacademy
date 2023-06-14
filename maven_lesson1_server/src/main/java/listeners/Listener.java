package listeners;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import configs.Config;

public class Listener extends Thread{

	private static final Logger log = LogManager.getLogger(Listener.class);
	
	private ServerSocket serverSocket = null;
	private String status = "TO_RUN";

	public Listener() throws NumberFormatException, IOException {

		serverSocket = new ServerSocket(Integer.parseInt(Config.get().getPort()));

	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void run() {
		status = "RUNNING";
		log.debug("Status is "+status);
		try {
			while(status.equals("RUNNING")) {
				
				@SuppressWarnings("unused")
				Socket socket = serverSocket.accept();
				
				ServiceProcess process = new ServiceProcess(socket);
				process.start();

			}
		}
		catch(IOException e) {
			status= "STOPPED";
			log.error("An Error has occured", e);
		}
		status= "STOPPED";
		log.debug("Status is "+status);
	}
}
