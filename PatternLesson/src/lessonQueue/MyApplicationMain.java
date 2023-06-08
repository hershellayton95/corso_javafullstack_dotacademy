package lessonQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MyApplicationMain {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		/*
		//		Queue queue = new Queue();
		//		FileReader fileReader = new FileReader(new File("resources/my_csv.csv"));
		//		ProcessEnQueue enQueue = new ProcessEnQueue(queue, fileReader);
		////		ProcessDeQueue deQueue = new ProcessDeQueue(queue);
		////		ProcessDeQueue deQueue2 = new ProcessDeQueue(queue);
		////		ProcessDeQueue deQueue3 = new ProcessDeQueue(queue);
		//		enQueue.start();
		//		Thread.sleep(1000);
		//		System.out.println("queue size= "+queue.size());
		////		deQueue.start();
		////		deQueue2.start();
		////		deQueue3.start();
		//		
		//		ProcessDeQueue[] deQueues = new ProcessDeQueue[2];
		//		deQueues[0] = new ProcessDeQueue(queue);
		//		deQueues[1] = new ProcessDeQueue(queue);
		//		
		//		for(ProcessDeQueue deQueue: deQueues) {
		//			deQueue.start();
		//		}
		//		
		//		int retry = 0;
		//		int retry1 = 0;
		//		boolean  stopall = false;
		//		
		//		while(!stopall) {
		//			
		//			if(queue.size() > 0) {
		//				Thread.sleep(1000);
		//			} else if(retry < 4) {
		//				Thread.sleep(1000);
		//				retry++;
		//			} else {
		//				for(ProcessDeQueue deQueue: deQueues) {
		//					if(!deQueue.getStatus().equals("STOPPED")) {
		//						deQueue.setStatus("TO_STOP");
		//					}
		//				}
		//				boolean onFail = false;
		//				for(ProcessDeQueue deQueue: deQueues) {
		//					if(!deQueue.getStatus().equals("STOPPED")) {
		//						onFail = true;
		//					}
		//				}
		//				if(onFail) retry1++;
		//				if(retry1>4) {
		//					for(ProcessDeQueue deQueue: deQueues) {
		//						deQueue.interrupt();
		//					}
		//					stopall=true;
		//				}
		//				
		//			}
		//			
		//			
		//		}
		//		Thread.sleep(5000);
		//		for(ProcessDeQueue deQueue: deQueues) {
		//			System.out.println(deQueue.getStatus());
		//		}
		*/
		Queue queue = new Queue();
		try {

			FileReader fileReader = new FileReader(new File("./resources/my_csv.csv"));
			ProcessEnQueue enQueue = new ProcessEnQueue(queue, fileReader);

			ProcessDeQueue deQueues[] = new ProcessDeQueue[2];
			deQueues[0] = new ProcessDeQueue(queue);
			deQueues[1] = new ProcessDeQueue(queue);

			enQueue.start();
			Thread.sleep(500);

			for(ProcessDeQueue deQueue:deQueues) {
				deQueue.start();
			}


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

		}catch(Exception e){
			System.out.println("file non trovato");
		}
	}
}
