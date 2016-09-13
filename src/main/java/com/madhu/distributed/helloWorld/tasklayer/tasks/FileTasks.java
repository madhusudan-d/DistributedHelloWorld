package com.madhu.distributed.helloWorld.tasklayer.tasks;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import com.madhu.distributed.helloWorld.base.Configuration;
import com.madhu.distributed.helloWorld.base.Configuration.AppConfig;

public class FileTasks {

	public static AppConfig appConfig = Configuration.getAppCofig();
	private int appPort=appConfig.port;

	public void addFile(String location) {

		if (appConfig.nodeIps == null) {
			System.out.println("This is one node set up OR Please add ip in application.conf");

		} else {

			for (String ip : appConfig.nodeIps) {

				String url = "http://" + ip + ":" + appPort + "/v1/internal/file/upload";
				System.out.println("connecting to inner ip node : " + url);

				File file = new File(location);

				Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

				WebTarget webTarget = client.target(url);
				MultiPart multiPart = new MultiPart();
				multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

				FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file", file,
						MediaType.APPLICATION_OCTET_STREAM_TYPE);

				multiPart.bodyPart(fileDataBodyPart);

				Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
						.post(Entity.entity(multiPart, multiPart.getMediaType()));

				System.out.println("Response for uploading files to node " + ip + ": " + response.getStatus());

			}

		}

	}

	public void deleteFile(String location) {

	}

}
