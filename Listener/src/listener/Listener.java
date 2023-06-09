package listener;

import java.net.ServerSocket;

public class Listener extends Thread{

	private ServerSocket serverSocket = null;
	
	public Listener() {
		serverSocket = new ServerSocket(Integer.parseInt(Config.get().getPort()));
	}
	
}
