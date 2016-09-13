package com.madhu.distributed.helloWorld;

import java.io.*;

import com.madhu.distributed.helloWorld.tasklayer.JobQueue;
import com.madhu.distributed.helloWorld.tasklayer.TaskMessage;

public class FileService {
	public static int taskId = 1;

	public JobQueue jobQueueInstance = JobQueue.getJobQueueInstance();

	public void upload(InputStream inputStream, String location) {
		try {
			OutputStream out = new FileOutputStream(new File(location));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void replicateToOtherNodes(String location) {
		TaskMessage msg = createTaskMessage("addFile", location);
		addTaskToQueue(msg);
	}

	public TaskMessage createTaskMessage(String jobType, String path) {
		return new TaskMessage().setFilePath(path).setJobType(jobType).setTaskId(" task :" + taskId++);
	}

	public void addTaskToQueue(TaskMessage msg) {
		jobQueueInstance.addJob(msg);
	}

}
