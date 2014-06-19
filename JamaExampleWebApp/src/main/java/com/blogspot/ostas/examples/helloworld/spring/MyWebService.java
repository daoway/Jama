package com.blogspot.ostas.examples.helloworld.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("spring-hello")
@Component
public class MyWebService {
    @Autowired
    private GreetingService greetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return greetingService.greet("world");
    }
}
