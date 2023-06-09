package factory;

import generics.Person;

public class ProcessDeQueue extends Thread  {
	private Queue queue = null;
	private String status = "TO_RUN";
 
	public String getStatus() {
		return status;
	}
 
	public void setStatus(String status) {
		this.status = status;
	}
 
	public ProcessDeQueue(Queue queue){
		this.queue = queue;
		status = "TO_RUN";
	}
 
	public void run() {
		status = "RUNNING";
		try {
			while(status.equals("RUNNING")) {
				Person person= queue.get();
				if(person != null) {
					System.out.printf("person id= %s person surname = %s \n",person.getId(), person.getLastname());
				}
			}
		}
		catch(InterruptedException e) {
			status= "STOPPED";
		}
		status= "STOPPED";
	}

}