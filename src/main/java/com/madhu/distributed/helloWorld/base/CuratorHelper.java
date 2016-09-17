package com.madhu.distributed.helloWorld.base;

import org.apache.curator.framework.CuratorFramework;

public class CuratorHelper {
	
	public static CuratorFramework zkClient=CuratorClient.getClient();
	
	
	public static void persist(String path,byte[] value){
		try {
			zkClient.blockUntilConnected();
			zkClient.create().creatingParentsIfNeeded().forPath(path,value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void delete(String path){
		try{
			zkClient.delete().deletingChildrenIfNeeded().forPath(path);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
