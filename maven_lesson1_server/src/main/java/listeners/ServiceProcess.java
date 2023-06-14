package listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import configs.Config;
import entities.CustomRequest;
import entities.CustomRespose;
import entities.Person;
import exceptions.UnsupportedRecordException;
import factories.PersonFactory;
import queues.ProcessDeQueue;
import queues.ProcessEnQueue;
import queues.Queue;

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
			while (status.equals("RUNNING")) {

				InputStream input = socket.getInputStream();

				OutputStream output = socket.getOutputStream();
				CustomRequest customRequest = null;
				try {
					ObjectInputStream inputStrem = new ObjectInputStream(input);
					customRequest = (CustomRequest) inputStrem.readObject();
				} catch (Exception e) {

				}

				if (customRequest != null) {
					doCustomProcess(customRequest, output);
					input.close();
					output.close();
				} else {
					InputStreamReader reader = new InputStreamReader(input);
					BufferedReader buffReader = new BufferedReader(reader);

					PrintWriter writer = new PrintWriter(output, false);

					String line = buffReader.readLine();
					String myInputLine = line;

					// print request

					String[] arrayMyInput = null;

					if (myInputLine != null)
						arrayMyInput = myInputLine.split(" ");

					String myProtocol = "CUSTOM";

					if (arrayMyInput != null && arrayMyInput.length > 1 && arrayMyInput[1].contains("HTTP"))
						myProtocol = "HTTP";

					if (myProtocol.equals("HTTP")) {
						printRequest(line, buffReader);
						doHTTPProcess(arrayMyInput, writer);
					}
					writer.close();
					buffReader.close();
				}

				// Elabora dati di input
				// String html = "<html><body><h1>Hello Kitty!</h1></body></html>";
				// String respCode = "HTTP/1.1 200 OK\r\n"
				// + "Content-Type: text/html\r\n\r\n" + html + "\r\n";

				socket.close();
				break;

			}
		} catch (Throwable e) {
			status = "STOPPED";
			e.printStackTrace();
		}
		status = "STOPPED";
	}

	private void doCustomProcess(CustomRequest customRequest, OutputStream outputStream)
			throws IOException, ClassNotFoundException {
		// String processToDo = null;
		// System.out.println(customRequest);
		CustomRespose respose = new CustomRespose();

		ObjectOutputStream outputStream2 = new ObjectOutputStream(outputStream);
//		String[] param = myInputLine.split(";");
//		processToDo = param[0];
//		String fileName = param[1];
//		String name = param[2];
//		String type = param[3];

		if (customRequest.getProcess().equals("getDataFile")) {
			

			try {
				FileReader fileReader = new FileReader(new File(Config.get().getCsvPath() + customRequest.getFileName()));
				BufferedReader reader2 = new BufferedReader(fileReader);

				// create json string

				List<Person> persons = createPersons(reader2);
				respose.setStatus("OK");
				respose.setResult(persons);

			} catch (Exception e) {

				System.out.println("trovata exception");
				respose.setStatus("KO");
				respose.setError(e.getMessage());
				e.printStackTrace();
			}
		} else if (customRequest.getProcess().equals("domyprocess")) {
			if (customRequest.getName().equals("pippo")) {
				respose = printQueueFromFileCustom(customRequest.getType(), customRequest.getFileName(), outputStream2);
			}else {
				respose.setStatus("KO");
				respose.setError("Il nome non è pippo");
			}
			

		}

		outputStream2.writeObject(respose);

	}

	private CustomRespose printQueueFromFileCustom(String processType,String fileName, ObjectOutputStream outputStream) {
		Queue queue = new Queue();
		CustomRespose respose = new CustomRespose();
		try {

			FileReader fileReader = new FileReader(new File(Config.get().getCsvPath() + fileName));
			ProcessEnQueue enQueue = new ProcessEnQueue(queue, fileReader);

			ProcessDeQueue deQueues[] = new ProcessDeQueue[2];
			deQueues[0] = new ProcessDeQueue(queue);
			deQueues[1] = new ProcessDeQueue(queue);

			if (processType.equals("sync"))
				enQueue.run();
			else if (processType.equals("async"))
				enQueue.start();

			Thread.sleep(500);

			for (ProcessDeQueue deQueue : deQueues)
				deQueue.start();

			if (processType.equals("sync")) {
				doSyncProcess(queue, enQueue, deQueues);

				respose.setStatus("done");

			}
			if (processType.equals("async")) {
				respose.setStatus("STARTED");
				outputStream.writeObject(respose);

			}
		} catch (Exception e) {
			System.out.println("file non trovato");
			respose.setStatus("KO");
			respose.setError(e.getMessage());
			e.printStackTrace();
		}
		return respose;
	}

	private void doHTTPProcess(String[] arrayMyInput, PrintWriter writer) throws IOException {
		StringTokenizer tokenizer = new StringTokenizer(arrayMyInput[0], "?");
		String myProcessToDo = tokenizer.nextToken();
		String arrayInputs = tokenizer.nextToken();
		Properties params = new Properties();
		params.load(new StringReader(arrayInputs.replace("&", "\n")));
		String name = params.getProperty("name");
		String fileName = params.getProperty("fileName");
		String processType = params.getProperty("type");
		if (myProcessToDo.equals("/getDataFile")) {
			FileReader fileReader = new FileReader(new File(Config.get().getCsvPath() + fileName));
			BufferedReader reader2 = new BufferedReader(fileReader);

			try {

				// create json string
				String myJsonPersons = createPersonJson(reader2);

				String json = "[" + myJsonPersons + "]";
				String respCode = "HTTP/1.1 200 OK\r\n" + "Content-Type: application/json\r\n\r\n" + json + "\r\n";

				writer.print(respCode);
				writer.flush();

			} catch (Exception e) {

				System.out.println("trovata exception");
				e.printStackTrace();
			}
		} else if (myProcessToDo.equals("domyprocess")) {
			if (name.equals("pippo"))
				printQueueFromFile(processType, fileName, writer);
		}

	}

	private void printQueueFromFile(String processType,String fileName, PrintWriter writer) {
		Queue queue = new Queue();
		try {
			
			FileReader fileReader = new FileReader(new File(Config.get().getCsvPath() + fileName));
			ProcessEnQueue enQueue = new ProcessEnQueue(queue, fileReader);

			ProcessDeQueue deQueues[] = new ProcessDeQueue[2];
			deQueues[0] = new ProcessDeQueue(queue);
			deQueues[1] = new ProcessDeQueue(queue);

			if (processType.equals("sync"))
				enQueue.run();
			else if (processType.equals("async"))
				enQueue.start();

			Thread.sleep(500);

			for (ProcessDeQueue deQueue : deQueues)
				deQueue.start();

			if (processType.equals("sync"))
				doSyncProcess(queue, enQueue, deQueues);

			String json = "[{\"processStatus\":\"done\"}]";
			if (processType.equals("async"))
				json = "[{\"processStatus\":\"started\"}]";

			String respCode = "HTTP/1.1 200 OK\r\n" + "Content-Type: application/json\r\n\r\n" + json + "\r\n";

			writer.print(respCode);
			writer.flush();
		} catch (Exception e) {
			System.out.println("file non trovato");
			e.printStackTrace();
		}
	}

	private void doSyncProcess(Queue queue, ProcessEnQueue enQueue, ProcessDeQueue[] deQueues)
			throws InterruptedException {
		int retry = 0;
		int retry1 = 0;
		boolean stopall = false;
		while (!stopall) {

			if (queue.size() > 0 || enQueue.getStatus().equals("RUNNING"))
				Thread.sleep(3000);
			else if (retry > 4) {
				Thread.sleep(3000);
				retry++;
			} else {
				retry1 = stopDeQueue(deQueues, retry1);
				if (retry1 > 4)
					stopall = true;
			}
		}
		Thread.sleep(5000);
		for (ProcessDeQueue deQueue : deQueues) {
			System.out.println(deQueue.getStatus());
		}

	}

	private int stopDeQueue(ProcessDeQueue[] deQueues, int retry1) {
		int result = retry1;

		for (ProcessDeQueue deQueue : deQueues) {
			if (!deQueue.getStatus().equals("STOPPED")) {
				deQueue.setStatus("TO_STOP");
			}
		}
		boolean oneFail = false;
		for (ProcessDeQueue deQueue : deQueues) {
			if (!deQueue.getStatus().equals("STOPPED")) {
				oneFail = true;
			}
			;
		}

		if (oneFail)
			result++;
		if (result > 4) {
			for (ProcessDeQueue deQueue : deQueues) {
				deQueue.interrupt();
			}

		}
		return result;
	}

	private List<Person> createPersons(BufferedReader reader2) throws IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, UnsupportedRecordException {

		List<Person> persons = new ArrayList<Person>();

		String myLine = reader2.readLine();

		while (myLine != null && myLine.length() > 0) {
			System.out.println(myLine);
			myLine = reader2.readLine();

			if (myLine != null) {

				Person person = PersonFactory.createPerson(myLine);
				person.setTransientVar("pluto");
				persons.add(person);

			}

		}

		return persons;
	}

	private String createPersonJson(BufferedReader reader2) throws IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, UnsupportedRecordException {

		String myJsonPersons = "";
		String myLine = reader2.readLine();

		while (myLine != null && myLine.length() > 0) {
			System.out.println(myLine);
			myLine = reader2.readLine();

			if (myLine != null) {

				Person person = PersonFactory.createPerson(myLine);

				myJsonPersons = myJsonPersons + "{" + person.toJson() + "},";
			}

		}
		myJsonPersons = myJsonPersons.substring(0, myJsonPersons.length() - 1);
		return myJsonPersons;
	}

	private void printRequest(String line, BufferedReader buffReader) throws IOException {
		boolean isPost = false;
		if (line != null)
			isPost = line.startsWith("POST");
		int contentLength = 0;

		while (line != null && line.length() != 0) {
			System.out.println(line);
			if (isPost && line.startsWith("Content-Length: "))
				contentLength = Integer.parseInt(line.substring(("Content-Length: ").length()));
			line = buffReader.readLine();
		}

		if (isPost) {
			int c = 0;
			System.out.print("body: ");
			for (int i = 0; i < contentLength; i++) {
				c = buffReader.read();
				System.out.print((char) c);
			}
		}
	}
}
