package com.madhu.distributed.helloWorld.base;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Configuration {

	private static ZkConfig zkConfig;
	private static AppConfig appConfig;

	public static ZkConfig getZKConfig() {
		if (zkConfig == null) {
			zkConfig = new ZkConfig();
		}
		return zkConfig;
	}

	public static AppConfig getAppCofig() {
		if (appConfig == null) {
			appConfig = new AppConfig();
		}
		return appConfig;

	}
	
	public static class ZkConfig {

		public Config config = ConfigFactory.load();
		private Config zkConfig = config.getConfig("conf.zkClient");
		String serverIP = zkConfig.getString("serverIp");
		int port = zkConfig.getInt("port");
		int maxRetries = zkConfig.getInt("maxRetries");
		int timeoutMs = zkConfig.getInt("timeOutMs");
		String leaderPath = zkConfig.getString("leaderPath");
		int leaderDelayMs = zkConfig.getInt("leaderDelayMs");
		String namespace = zkConfig.getString("namespace");

	}

	public static class AppConfig {

		private Config config = ConfigFactory.load();
		private Config appConfig = config.getConfig("conf.app");
		public String serverIp = appConfig.getString("server");
		public int port = appConfig.getInt("port");
		public String[] nodeIps = extractIps(appConfig.getString("nodeIps"));
		public String dataDirectoryPath=appConfig.getString("dataDirectoryPath");

		private static String[] extractIps(String nodeIPs) {
			System.out.println("AppConfig :" +nodeIPs);
			return nodeIPs.split(",");
		}

	}

	
	
}

