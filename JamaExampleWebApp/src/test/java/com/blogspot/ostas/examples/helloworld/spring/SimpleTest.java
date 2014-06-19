package com.blogspot.ostas.examples.helloworld.spring;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;

public class SimpleTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(MyWebService.class);
    }

    @Test
    public void test() {
        final String hello = target("spring-hello").request().get(String.class);
        assertEquals("Hello World!", hello);
    }
}