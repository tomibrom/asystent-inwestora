package com.agsupport.core.service.share;


import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

public class MyRESTApplication extends Application {

	public MyRESTApplication() {
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(HelloWorldResource.class);
		return s;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> s = new HashSet<Object>();
		s.add(new JacksonJsonProvider());
		return s;
	}

}
