package listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.Properties;
import java.util.StringTokenizer;

import configlesson.UnsupportedRecordException;
import factory.PersonFactory;
import factory.ProcessDeQueue;
import factory.ProcessEnQueue;
import factory.Queue;
import generics.Person;

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
				String myInputLine=line;

				//print request
				printRequest(line, buffReader);

				String[] arrayMyInput = null;

				if(myInputLine !=null)  arrayMyInput = myInputLine.split(" ");

				String myProtocol = "CUSTOM";

				if (arrayMyInput != null &&
						arrayMyInput.length >2 &&
						arrayMyInput[2].contains("HTTP")) myProtocol="HTTP";


				if (myProtocol.equals("HTTP")) doHTTPProcess(arrayMyInput,writer);
				else if  (myProtocol.equals("CUSTOM"));//doHTTPProcess();

				//Elabora dati di input
				//    String html = "<html><body><h1>Hello Kitty!</h1></body></html>";
				//    String respCode = "HTTP/1.1 200 OK\r\n"
				//      + "Content-Type: text/html\r\n\r\n" + html + "\r\n";


				writer.close();
				buffReader.close();
				socket.close();
				break;
			}
		}
		catch(Throwable e) {
			status= "STOPPED";
			e.printStackTrace();
		}
		status= "STOPPED";
	}

	private void doHTTPProcess(String[] arrayMyInput, PrintWriter writer)
			throws IOException {
		StringTokenizer tokenizer = new StringTokenizer(arrayMyInput[1],"?");
		String myProcessToDo =tokenizer.nextToken();
		String arrayInputs = tokenizer.nextToken();
		Properties params=new Properties ();
		params.load(new StringReader (arrayInputs.replace("&", "\n")));
		String inputName= params.getProperty("name");
		String processType=params.getProperty("type");
		if(myProcessToDo.equals("/getDataFile")) {
			FileReader fileReader = new FileReader(new File("./resources/"+inputName));
			BufferedReader reader2 = new BufferedReader(fileReader);

			try {
				String myLine = "";
				//create json string
				String myJsonPersons = createPersonJson(myLine, reader2);



				String json = "["+ myJsonPersons +"]";
				String respCode = "HTTP/1.1 200 OK\r\n"
						+ "Content-Type: application/json\r\n\r\n" + json + "\r\n";

				writer.print(respCode);
				writer.flush();

			} catch(Exception e){

				System.out.println("trovata exception");
				e.printStackTrace();
			}
		}else if(myProcessToDo.equals("/domyprocess")) {
			if (inputName.equals("pippo")) printQueueFromFile(processType, writer);
		}

	}

	private void printQueueFromFile(String processType, PrintWriter writer) {
		Queue queue = new Queue();
		try {

			FileReader fileReader = new FileReader(new File("./resources/my_csv.csv"));
			ProcessEnQueue enQueue = new ProcessEnQueue(queue, fileReader);

			ProcessDeQueue deQueues[] = new ProcessDeQueue[2];
			deQueues[0] = new ProcessDeQueue(queue);
			deQueues[1] = new ProcessDeQueue(queue);

			if(processType.equals("sync"))  enQueue.run();
			else if(processType.equals("async")) enQueue.start();

			Thread.sleep(500);

			for(ProcessDeQueue deQueue:deQueues) deQueue.start();

			if(processType.equals("sync")) doSyncProcess(queue, enQueue, deQueues);

			String json = "[{\"processStatus\":\"done\"}]";
			if(processType.equals("async")) json="[{\"processStatus\":\"started\"}]";

			String respCode = "HTTP/1.1 200 OK\r\n"
					+ "Content-Type: application/json\r\n\r\n" + json + "\r\n";


			writer.print(respCode);
			writer.flush();
		}catch(Exception e){
			System.out.println("file non trovato");
			e.printStackTrace();
		}
	}

	private void doSyncProcess(Queue queue, ProcessEnQueue enQueue, ProcessDeQueue[] deQueues )
			throws InterruptedException {
		int retry = 0;
		int retry1 = 0;
		boolean stopall = false;
		while(!stopall) {
			if(queue.size() > 0 || enQueue.getStatus().equals("RUNNING")) Thread.sleep(3000); 
			else if(retry > 4){
				Thread.sleep(3000);
				retry++;
			}
			else retry1 = stopDeQueue(deQueues, retry1, stopall);

		}
		Thread.sleep(5000);
		for(ProcessDeQueue deQueue:deQueues) {
			System.out.println(deQueue.getStatus());
		}

	}


	private int stopDeQueue(ProcessDeQueue[] deQueues, int retry1, boolean stopall) {
		int result= retry1;
		for(ProcessDeQueue deQueue:deQueues) {
			if(!deQueue.getStatus().equals("STOPPED")) {
				deQueue.setStatus("TO_STOP");
			}
		}
		boolean oneFail = false;
		for(ProcessDeQueue deQueue:deQueues) {
			if(!deQueue.getStatus().equals("STOPPED")) {
				oneFail = true;
			};
		}

		if(oneFail)result++;
		if(result > 4 ) {
			for(ProcessDeQueue deQueue:deQueues) {
				deQueue.interrupt();
			}
			stopall = true;
		}
		return result;
	}

	private String createPersonJson(String myLine, BufferedReader reader2)
			throws IOException,
			InstantiationException,
			IllegalAccessException,
			ClassNotFoundException,
			UnsupportedRecordException {

		String myJsonPersons = "";
		while(myLine != null ) {

			myLine = reader2.readLine();

			if(myLine != null) {

				Person person = PersonFactory.createPerson(myLine);
				
				myJsonPersons = myJsonPersons +"{"+ person.toJson()+"},";
			}
			
		}
		myJsonPersons = myJsonPersons.substring(0, myJsonPersons.length()-1);
		return myJsonPersons;
	}

	private void printRequest(String line, BufferedReader buffReader)
			throws IOException {
		boolean isPost = false;
		if(line != null) isPost = line.startsWith("POST");
		int contentLength = 0;
		
		while(line != null && line.length()!=0){
			System.out.println(line);
			if(isPost && line.startsWith("Content-Length: ")) contentLength = Integer.parseInt(line.substring(("Content-Length: ").length()));
			line = buffReader.readLine();
		}
		
	    if(isPost){
	        int c = 0;
        	System.out.print("body: ");
	        for (int i = 0; i < contentLength; i++) {
	        	c = buffReader.read();
	        	System.out.print((char) c);
	        }
	    }
	}
}
