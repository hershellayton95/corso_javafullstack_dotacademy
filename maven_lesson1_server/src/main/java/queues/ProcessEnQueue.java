package queues;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.lang.management.ManagementFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import entities.Person;
import factories.PersonFactory;

public class ProcessEnQueue extends Thread {

	private static final Logger log = LogManager.getLogger(ProcessEnQueue.class);

	private Queue queue = null;

	private Reader inputReader = null;

	private String status = "TO_RUN";

	public ProcessEnQueue(Queue queue, Reader inputReader) {
		super();
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
		log.debug("Status: ", status);

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
				log.error("Create Person Error: ", e);
			}

		}

		status = "STOPPED";
		log.debug("Status: ", status);
	}
}