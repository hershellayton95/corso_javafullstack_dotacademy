package queues;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import entities.Person;
import factories.PersonFactory;

public class ProcessEnQueue extends Thread {

	private Queue queue = null;

	private Reader inputReader = null;

	private String status = "TO_RUN";

	public ProcessEnQueue(Queue queue, Reader inputReader) {

		this.queue = queue;
		this.inputReader = inputReader;

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public void run() {

		status = "RUNNING";

		if (inputReader instanceof FileReader) {

			FileReader reader = (FileReader) inputReader;

			BufferedReader reader2 = new BufferedReader(reader);

			try {

				String myLine = "";

				while(myLine != null ) {

					myLine = reader2.readLine();

					if(myLine != null) {

						Person person = PersonFactory.createPerson(myLine);

						queue.put(person);
					}

				}
			} catch(Exception e){

				System.out.println("trovata exception");
				e.printStackTrace();
			}

		}

		status = "STOPPED";
	}
}