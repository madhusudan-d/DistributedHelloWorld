package com.madhu.distributed.helloWorld;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class MyApplication extends Application{
	 public Set<Class<?>> getClasses() {
	        Set<Class<?>> s = new HashSet<Class<?>>();
	        s.add(Api.class);
	        s.add(MultiPartFeature.class);
	        return s;
	    }

}
