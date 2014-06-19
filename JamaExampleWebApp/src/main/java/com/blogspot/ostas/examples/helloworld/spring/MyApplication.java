package com.blogspot.ostas.examples.helloworld.spring;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Register JAX-RS application components.
 */
public class MyApplication extends ResourceConfig {
    public MyApplication () {
        register(RequestContextFilter.class);
        register(MyWebService.class);
    }
}
