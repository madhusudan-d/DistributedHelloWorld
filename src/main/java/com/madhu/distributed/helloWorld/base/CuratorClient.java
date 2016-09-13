package com.madhu.distributed.helloWorld.base;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorClient {

	public static CuratorFramework zkClient = create("localhost:2181");
	public static CuratorFramework getClient(){
		//zkClient.start();
		return zkClient;
	}

	public static CuratorFramework create(String connectionString) {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		System.out.println("creating zk client");
		return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);

	}

	public static CuratorFramework createWithOptions(String connectionString, RetryPolicy retryPolicy,
			int connectionTimeoutMs, int sessionTimeoutMs) {

		return CuratorFrameworkFactory.builder().connectString(connectionString).retryPolicy(retryPolicy)
				.connectionTimeoutMs(connectionTimeoutMs).sessionTimeoutMs(sessionTimeoutMs).build();
	}

	// TBD
	public String getConnectionString() {
		return "s";
	}

}
