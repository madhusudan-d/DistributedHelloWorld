package com.madhu.distributed.helloWorld;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Application;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.server.ResourceConfig;

import com.madhu.distributed.helloWorld.base.Configuration;
import com.madhu.distributed.helloWorld.base.Configuration.AppConfig;
import com.madhu.distributed.helloWorld.base.ElectLeader;
import com.madhu.distributed.helloWorld.tasklayer.Worker;

public class Main {

	public static void main(String[] args) throws Exception {

		AppConfig appConfig = Configuration.getAppCofig();
		int appPort = appConfig.port;

		Worker worker = new Worker();
		worker.start();

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		context.setContextPath("/");

		Application application = new MyApplication();

		final ResourceConfig resourceConfig = ResourceConfig.forApplication(application);
		ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(resourceConfig));

		jerseyServlet.setInitOrder(0);
		context.addServlet(jerseyServlet, "/*");

		Server server = new Server(appPort);
		server.setHandler(context);

		try {
			server.start();
		} catch (Exception e) {
			System.out.println("Exception staring server");
			e.printStackTrace();
		}
		System.out.println("started server");

		// server.join();

	}

	public void startLeaderElection() {
		ElectLeader elect = new ElectLeader();
		try {
			elect.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while starting a leader");
			e.printStackTrace();
		}
	}

}
