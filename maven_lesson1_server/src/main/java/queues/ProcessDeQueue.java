package queues;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entities.Person;

public class ProcessDeQueue extends Thread  {

	private static final Logger log = LogManager.getLogger(ProcessDeQueue.class);
	
	private Queue queue = null;
	private String status = "TO_RUN";
 
	public String getStatus() {
		return status;
	}
 
	public void setStatus(String status) {
		this.status = status;
	}
 
	public ProcessDeQueue(Queue queue){
		super();
		this.queue = queue;
		status = "TO_RUN";
	}
 
	public void run() {
		status = "RUNNING";
		log.debug("Status: ", status);
		try {
			while(status.equals("RUNNING")) {
				Person person= queue.get();
				if(person != null) {
					log.debug(person);
				}
			}
		}
		catch(InterruptedException e) {
			status= "STOPPED";
			log.error("An Error has occured", e);
		}
		status= "STOPPED";
		log.debug("Status: ", status);
	}

}