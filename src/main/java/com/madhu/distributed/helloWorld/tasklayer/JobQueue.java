package com.madhu.distributed.helloWorld.tasklayer;

import java.util.LinkedList;
import java.util.Queue;

public class JobQueue {

	Queue<TaskMessage> queue = new LinkedList<TaskMessage>();
	
	private static JobQueue jobQueue;
	private JobQueue(){}
	
	public static JobQueue getJobQueueInstance(){
		if(jobQueue==null){
			jobQueue=new JobQueue();
		}
		return jobQueue;
		
	}
	

	public boolean addJob(TaskMessage msg) {
		System.out.println("JobQueue: Task got added");
		return queue.add(msg);
	}

	public TaskMessage removeJob() {
		System.out.println("JobQueue: Task got removed");
		return queue.remove();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

}
