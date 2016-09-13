package com.madhu.distributed.helloWorld.base;

//import java.io.IOException;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.recipes.leader.*;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.Participant;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

public class ElectLeader {

	public CuratorFramework zkClient;
	public String leaderPath = "/leader-test";
	private LeaderLatch leaderLatch;
	ExecutorService executorservice;

	static String id;

	static {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();

			id = inetAddress.getHostAddress();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void start() throws Exception {
		this.zkClient = CuratorClient.zkClient;
		executorservice = Executors.newSingleThreadExecutor();
		zkClient.start();
		// CuratorHelper.persist(leaderPath, "".getBytes());
		zkClient.getZookeeperClient().blockUntilConnectedOrTimedOut();
		leaderLatch = new LeaderLatch(zkClient, leaderPath, id.toString());
		System.out.println("starting leader Election");
		leaderLatch.addListener(listener, executorservice);
		leaderLatch.start();
		System.out.println("election started");

	}

	public void stop() throws Exception {
		leaderLatch.close();
		zkClient.close();
		executorservice.shutdown();
	}

	public String getHostIp() {
		return id;
	}

	public boolean isLeader() {
		return leaderLatch.hasLeadership();
	}

	public Participant currentLeader() throws Exception {
		return leaderLatch.getLeader();
	}

	public void electionStatus() throws Exception {
		System.out.println(leaderLatch.getParticipants());
	}

	private String getPartcipants() throws Exception {
		return leaderLatch.getParticipants().toString();
	}

	LeaderLatchListener listener = new LeaderLatchListener() {

		@Override
		public void notLeader() {
			// TODO Auto-generated method stub
			try {
				System.out.println("Node " + currentLeader() + " acquired leadership");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("Participants in election are " + getPartcipants());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void isLeader() {
			// TODO Auto-generated method stub
			System.out.println("Node " + id + " acquired leadership");
		}
	};

}
