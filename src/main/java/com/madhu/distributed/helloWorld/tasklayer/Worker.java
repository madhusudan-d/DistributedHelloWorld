package com.madhu.distributed.helloWorld.tasklayer;

public class Worker {
	public TaskWorker task = new TaskWorker();
	Thread taskThread = new Thread(task);

	public void start() {
		taskThread.start();
		System.out.println("started taskWorker thread");
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		taskThread.stop();;
	}

}
