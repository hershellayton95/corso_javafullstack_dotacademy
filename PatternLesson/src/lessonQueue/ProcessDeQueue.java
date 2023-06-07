package lessonQueue;

import lessonQueue.entities.Persons;

public class ProcessDeQueue extends Thread  {
	private Queue queue = null;

	private String status = "TO_RUN";

	public ProcessDeQueue (Queue queue) {
		this.queue=queue;
		this.status = "TO_RUN";
	}

	public void run() {
		if(status == "TO_RUN") {
			status = "RUNNING";
		}

		try {
			while(status == "RUNNING") {
				Persons person= queue.get();
				System.out.printf("person id= %s person surname = %s \n",person.getId(), person.getLastname());
			}
		}catch(InterruptedException e) {
			this.status = "STOPPED";
		}

		this.status = "STOPPED";
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

}