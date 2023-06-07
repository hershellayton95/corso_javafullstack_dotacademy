package lessonQueue;

public class MyApplicationMain {

	public static void main(String[] args) throws InterruptedException {

		Queue queue = new Queue();
		
		ProcessEnQueue enQueue = new ProcessEnQueue(queue);
//		ProcessDeQueue deQueue = new ProcessDeQueue(queue);
//		ProcessDeQueue deQueue2 = new ProcessDeQueue(queue);
//		ProcessDeQueue deQueue3 = new ProcessDeQueue(queue);
		enQueue.start();
		Thread.sleep(1000);
		System.out.println("queue size= "+queue.size());
//		deQueue.start();
//		deQueue2.start();
//		deQueue3.start();
		
		ProcessDeQueue[] deQueues = new ProcessDeQueue[2];
		deQueues[0] = new ProcessDeQueue(queue);
		deQueues[1] = new ProcessDeQueue(queue);
		
		for(ProcessDeQueue deQueue: deQueues) {
			deQueue.start();
		}
		
		int retry = 0;
		int retry1 = 0;
		boolean  stopall = false;
		
		while(!stopall) {
			
			if(queue.size() > 0) {
				Thread.sleep(1000);
			} else if(retry < 4) {
				Thread.sleep(1000);
				retry++;
			} else {
				for(ProcessDeQueue deQueue: deQueues) {
					if(!deQueue.getStatus().equals("STOPPED")) {
						deQueue.setStatus("TO_STOP");
					}
				}
				boolean onFail = false;
				for(ProcessDeQueue deQueue: deQueues) {
					if(!deQueue.getStatus().equals("STOPPED")) {
						onFail = true;
					}
				}
				if(onFail) retry1++;
				if(retry1>4) {
					for(ProcessDeQueue deQueue: deQueues) {
						deQueue.interrupt();
					}
					stopall=true;
				}
				
			}
			
			
		}
		Thread.sleep(5000);
		for(ProcessDeQueue deQueue: deQueues) {
			System.out.println(deQueue.getStatus());
		}
	}
}
