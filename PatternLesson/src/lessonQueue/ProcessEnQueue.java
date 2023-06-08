package lessonQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

import lessonQueue.entities.Persons;

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

		//prendi le persone da aggiungere al queue
		/*
		Person p1 = new Person();
		p1.setId("xx1");
		p1.setName("paolo");
		p1.setSurname("Verdi");
		queue.put(p1);

		p1 = new Person();
		p1.setId("xx2");
		p1.setName("Marco");
		p1.setSurname("Rossi");;
		queue.put(p1);

		p1 = new Person();
		p1.setId("xx3");
		p1.setName("Sara");
		p1.setSurname("Brambilla");
		queue.put(p1);*/

		status = "RUNNING";

		if (inputReader instanceof FileReader) {

			FileReader reader = (FileReader) inputReader;

			BufferedReader reader2 = new BufferedReader(reader);

			try {

				String myLine = "";

				while(myLine != null ) {

					myLine = reader2.readLine();

					if(myLine != null) {

						Persons person = new Persons();

						StringTokenizer stringTokenizer = new StringTokenizer(myLine, ",");

						String name = stringTokenizer.nextToken();
						person.setFirstname(name);

						String surname = stringTokenizer.nextToken();
						person.setLastname(surname);

						String age = stringTokenizer.nextToken();
						person.setAge(Integer.parseInt(age));

						String job = stringTokenizer.nextToken();
						person.setJob(job);

						String id = stringTokenizer.nextToken();
						person.setId(id);

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