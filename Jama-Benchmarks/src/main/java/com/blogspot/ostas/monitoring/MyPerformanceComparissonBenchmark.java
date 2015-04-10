package com.blogspot.ostas.monitoring;

import com.blogspot.ostas.monitoring.test.service.MyService;
import com.blogspot.ostas.monitoring.test.service.impl.MyServiceImpl;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class MyPerformanceComparissonBenchmark {
    MyService myServiceMonitored;
    MyService myServiceMetrics;

    @Setup
    public void setUp() {
        System.out.println("init of spring contexts....");
        ApplicationContext applicationContextMonitoredJama = new ClassPathXmlApplicationContext("applicationContext-app.xml","applicationContext-monitoring.xml");
        ApplicationContext applicationContextMonitoredMetrics = new ClassPathXmlApplicationContext("applicationContext-app.xml","metrics-config.xml");
        myServiceMonitored = applicationContextMonitoredJama.getBean(MyServiceImpl.class);
        myServiceMetrics = applicationContextMonitoredMetrics.getBean(MyServiceImpl.class);
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testMonitoredServiceWithMetrics() {
        myServiceMetrics.setStringsCount(1000);
        myServiceMetrics.listOfStrings("test");
    }
    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testMonitoredServiceWithJama() {
        myServiceMonitored.setStringsCount(1000);
        myServiceMonitored.listOfStrings("test");
    }

}
