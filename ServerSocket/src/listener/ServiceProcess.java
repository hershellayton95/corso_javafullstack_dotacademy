package listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.Properties;
import java.util.StringTokenizer;

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

				while(line != null && line.length()>0) {
					System.out.println(line);
					line = buffReader.readLine();
				}
				String[] arrayMyInput = null;
				if(myInputLine !=null) {
					arrayMyInput = myInputLine.split(" "); 
				}
				
				String myProtocol = "CUSTOM";

				if ( arrayMyInput != null && arrayMyInput.length >2 && arrayMyInput[2].contains("HTTP") ){
					myProtocol="HTTP";
				}
				
				String myProcessToDo=null;
				Properties params = null;

				if (myProtocol.equals("HTTP")) {
					StringTokenizer tokenizer = new StringTokenizer(arrayMyInput[1],"?");
					myProcessToDo =tokenizer.nextToken();
					String arrayInputs = tokenizer.nextToken();
					params=new Properties ();
					params.load(new StringReader (arrayInputs.replace("&", "\n")));
					String inputName= params.getProperty("name");
					String processType=params.getProperty("type");
					if(myProcessToDo.equals("/getDataFile")) {
						FileReader fileReader = new FileReader(new File("./resources/"+inputName));
						BufferedReader reader2 = new BufferedReader(fileReader);

						try {
							String myLine = "";
							String myJsonPersons = "";
							while(myLine != null ) {

								myLine = reader2.readLine();

								if(myLine != null) {

									Person person = PersonFactory.createPerson(myLine);
									myJsonPersons = myJsonPersons +",{"+ person.toJson()+"}";
								}
							}

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
						if (inputName.equals("pippo")) {
							Queue queue = new Queue();
							try {

								FileReader fileReader = new FileReader(new File("./resources/my_csv.csv"));
								ProcessEnQueue enQueue = new ProcessEnQueue(queue, fileReader);

								ProcessDeQueue deQueues[] = new ProcessDeQueue[2];
								deQueues[0] = new ProcessDeQueue(queue);
								deQueues[1] = new ProcessDeQueue(queue);

								if(processType.equals("sync")) {
									enQueue.run();

								}else if(processType.equals("async")) {
									enQueue.start();
								}

								Thread.sleep(500);

								for(ProcessDeQueue deQueue:deQueues) {
									deQueue.start();
								}

								if(processType.equals("sync")) {
									int retry = 0;
									int retry1 = 0;
									boolean stopall = false;
									while(!stopall) {
										if(queue.size() > 0 || enQueue.getStatus().equals("RUNNING")) {
											Thread.sleep(3000);
										}
										else if(retry > 4){
											Thread.sleep(3000);
											retry++;
										}
										else {
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

											if(oneFail)retry1++;
											if(retry1 > 4 ) {
												for(ProcessDeQueue deQueue:deQueues) {
													deQueue.interrupt();
												}
												stopall = true;
											}

										}

									}
									Thread.sleep(5000);
									for(ProcessDeQueue deQueue:deQueues) {
										System.out.println(deQueue.getStatus());
									}

								}

								String json = "[{\"processStatus\":\"done\"}]";
								if(processType.equals("async")) {
									json="[{\"processStatus\":\"started\"}]";    }

								String respCode = "HTTP/1.1 200 OK\r\n"
										+ "Content-Type: application/json\r\n\r\n" + json + "\r\n";


								writer.print(respCode);
								writer.flush();
							}catch(Exception e){
								System.out.println("file non trovato");
								e.printStackTrace();
							}
						}
					}

				}
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
}
