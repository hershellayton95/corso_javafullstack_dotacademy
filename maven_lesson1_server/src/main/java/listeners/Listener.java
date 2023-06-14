package listeners;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import configs.Config;

public class Listener extends Thread{

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
		}
		status= "STOPPED";
	}
}
