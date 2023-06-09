package listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.time.LocalDate;

public class ServiceProcess extends Thread {

	private String status = "TO_RUN";
	private Socket socket = null;

	public ServiceProcess(Socket socket) {
		this.socket = socket;
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

				InputStream input = socket.getInputStream();
				InputStreamReader reader = new InputStreamReader(input);
				BufferedReader buffReader = new BufferedReader(reader);

				OutputStream output = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(output, false);

				String line = buffReader.readLine();

				while(line != null && line.length()>0) {
					System.out.println(line);
					line = buffReader.readLine();
				}

				//Elabora dati di input
				//				String html = "<html><body><h1>Hello Kitty!</h1></body></html>";
				//				String respCode = "HTTP/1.1 200 OK\r\n"
				//						+ "Content-Type: text/html\r\n\r\n" + html + "\r\n";

				String json = "[{\"name\":\"Filippo\",\"surname\":\"Di Marco\"}]";
				String respCode = "HTTP/1.1 200 OK\r\n"
						+ "Content-Type: application/json\r\n\r\n" + json + "\r\n";

				writer.print(respCode);
				writer.flush();
				writer.close();
				buffReader.close();
				socket.close();
				break;
			}
		}
		catch(Throwable e) {
			status= "STOPPED";
		}
		status= "STOPPED";
	}
}
