package com.madhu.distributed.helloWorld.tasklayer;

public class TaskMessage {

	String taskId;
	String jobType;
	String filePath;

	public TaskMessage setTaskId(String taskId) {
		this.taskId = taskId;
		return this;
	}

	public TaskMessage setJobType(String jobType) {
		this.jobType = jobType;
		return this;
	}

	public TaskMessage setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
}
