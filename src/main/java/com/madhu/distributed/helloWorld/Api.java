package com.madhu.distributed.helloWorld;

import java.io.InputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
//import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//import javax.ws.rs.core.Response.Status;
//import com.sun.jersey.multipart.FormDataParam;
//import com.sun.jersey.core.header.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.madhu.distributed.helloWorld.base.Configuration;
import com.madhu.distributed.helloWorld.base.Configuration.AppConfig;

@Path("/v1")
public class Api {
	private FileService fs = new FileService();
	private AppConfig appConfig = Configuration.getAppCofig();
	private String dataDirectoryPath = appConfig.dataDirectoryPath;

	/*
	 * TODO : add json as response
	 */
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String options() {
		return "Available Options:\n" + "-> file/upload" + "\n" + "->hello";
	}

	@POST
	@Path("file/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		String uploadedLocation = dataDirectoryPath + fileDetail.getFileName();
		fs.upload(uploadedInputStream, uploadedLocation);
		System.out.println("uploaded file " + uploadedLocation);
		String output = "File uploaded to : " + uploadedLocation;
		fs.replicateToOtherNodes(uploadedLocation);
		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/internal/file/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadfileInternal(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) throws UnknownHostException {
		System.out.println("Request received to /internal/file/upload to :" + Inet4Address.getLocalHost());
		String uploadedLocation = dataDirectoryPath + fileDetail.getFileName();
		fs.upload(uploadedInputStream, uploadedLocation);
		System.out.println("Internal call : uploaded file " + uploadedLocation);
		String output = "Internal call :File uploaded to : " + uploadedLocation;
		return Response.status(200).entity(output).build();

	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello World";
	}

}
