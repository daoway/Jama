package com.blogspot.ostas.monitoring;

import com.blogspot.ostas.monitoring.test.service.MyService;
import com.blogspot.ostas.monitoring.test.service.impl.MyServiceImpl;
import com.blogspot.ostas.monitoring.test.service.layers.web.TestWebService;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class MyBenchmark {
    MyService myServicePlain;
    MyService myServiceMonitored;
    MyService myServiceEmptyMonitored;
    TestWebService testWebService;

    @Setup
    public void setUp() {
        System.out.println("init of spring contexts....");
        ApplicationContext applicationContextPlain = new ClassPathXmlApplicationContext("applicationContext-app.xml");
        ApplicationContext applicationContextMonitored = new ClassPathXmlApplicationContext("applicationContext-app.xml","applicationContext-monitoring.xml");
        ApplicationContext applicationContextEmptyMonitored = new ClassPathXmlApplicationContext("applicationContext-app-empty-interceptor.xml");
        ApplicationContext applicationContextWSMonitored = new ClassPathXmlApplicationContext("applicationContext-app-layers.xml","applicationContext-monitoring.xml");
        myServicePlain = applicationContextPlain.getBean(MyServiceImpl.class);
        myServiceMonitored = applicationContextMonitored.getBean(MyServiceImpl.class);
        myServiceEmptyMonitored = applicationContextEmptyMonitored.getBean(MyServiceImpl.class);
        testWebService = applicationContextWSMonitored.getBean(TestWebService.class);
    }
/*
     @GenerateMicroBenchmark
     @BenchmarkMode(Mode.AverageTime)
     //@Fork(0)
    public void testPlainService() {
        myServicePlain.setStringsCount(1);
        myServicePlain.listOfStrings("test");
    }
*/
/*
    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testEmptyInterceptorService() {
        myServiceEmptyMonitored.setStringsCount(1000);
        myServiceEmptyMonitored.listOfStrings("test");
    }
    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testMonitoredService() {
        myServiceMonitored.setStringsCount(1000);
        myServiceMonitored.listOfStrings("test");
    }
*/
    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testMonitoredLayersService() throws InterruptedException {
        testWebService.testWebServiceMethod1("test1");
    }
}
