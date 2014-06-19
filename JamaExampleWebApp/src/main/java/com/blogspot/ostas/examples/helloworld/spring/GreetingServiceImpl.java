package com.blogspot.ostas.examples.helloworld.spring;

import com.blogspot.ostas.monitoring.annotation.MonitoringTarget;

public class GreetingServiceImpl implements GreetingService {
    @MonitoringTarget(internalName = "internalName")
    @Override
    public String greet(String who) {
        return String.format("hello, %s!", who);
    }
}
