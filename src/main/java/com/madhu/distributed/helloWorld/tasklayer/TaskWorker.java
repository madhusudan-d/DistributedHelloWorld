package com.madhu.distributed.helloWorld.tasklayer;

import com.madhu.distributed.helloWorld.tasklayer.tasks.FileTasks;

public class TaskWorker implements Runnable {

	JobQueue queue = JobQueue.getJobQueueInstance();

	public void processMessage() throws InterruptedException {

		while (true) {
			if (!queue.isEmpty()) {
				System.out.println("TaskWorker: Getting task from JobQueue");
				TaskMessage task = fetchTaskMessage();
				executeTask(task);		
			}
		}
	}

	public TaskMessage fetchTaskMessage() {
		return queue.removeJob();
	}

	public void executeTask(TaskMessage msg) {
		if (msg.jobType == "addFile") {
			System.out.println("starting addFile task");
			FileTasks task = new FileTasks();
			task.addFile(msg.filePath);
		} else if (msg.jobType == "deleteFile") {
			FileTasks task = new FileTasks();
			task.deleteFile(msg.filePath);
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			processMessage();
		} catch (InterruptedException e) {
			System.out.println("Exception processMessage");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
