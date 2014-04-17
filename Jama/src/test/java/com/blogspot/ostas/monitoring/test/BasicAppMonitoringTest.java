package com.blogspot.ostas.monitoring.test;

import com.blogspot.ostas.monitoring.test.service.FibonacciService;
import com.blogspot.ostas.monitoring.test.service.MyService;
import com.blogspot.ostas.monitoring.test.service.impl.FibonacciServiceImpl;
import com.blogspot.ostas.monitoring.test.service.impl.StringConcatenatorServiceImpl;
import com.blogspot.ostas.monitoring.interceptor.PerformanceMonitoringInterceptor;
import com.blogspot.ostas.monitoring.publish.naming.MethodNamingStrategy;
import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;
import com.blogspot.ostas.monitoring.test.tools.AttributeDetailsUtil;
import com.blogspot.ostas.monitoring.test.tools.MyJConsole;
import com.blogspot.ostas.monitoring.test.tools.MyJConsoleLocalJmx;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-app.xml","classpath:applicationContext-monitoring.xml"})
public class BasicAppMonitoringTest{
    private static final Logger LOGGER = Logger.getLogger(BasicAppMonitoringTest.class);
    private final MyJConsole myJConsole = new MyJConsoleLocalJmx();//new MyJConsoleAttachAPIImpl();
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PerformanceMonitoringInterceptor performanceMonitor;
    @Autowired
    private MyService myService;
    @Autowired
    private FibonacciService fibonacciService;
    @Autowired
    private MethodNamingStrategy methodNamingStrategy;
    //JMX bean name
    private String monitoringBeanName = "com.blogspot.ostas.monitoring:name=Monitoring";
    //method we'are going to call
    private Method method;
    //computed method id
    private String computedMethodId;
    @Before
    public void setUp() throws Exception {
        method = StringConcatenatorServiceImpl.class.getMethod("addIntToString", String.class, int.class);
        computedMethodId = methodNamingStrategy.getMethodAsString(method);
        performanceMonitor.clearStats();
    }
    @After
    public void clearCounters(){
        myService.resetCounter();
        performanceMonitor.clearStats();
    }
    @Test
    public void testContextUp(){
        assertNotNull(applicationContext);
    }

    @Test
    public void testCounter() throws NoSuchMethodException {
        int stringsCount = 100;
        myService.setStringsCount(stringsCount);
        myService.listOfStrings("test");
        Map<String,MethodInvocationStats> map = performanceMonitor.getMethodInvocationStatsMap();
        assertNotNull(map);
        AtomicLong hints = map.get(computedMethodId).getHits();
        Assert.assertEquals((new AtomicLong(stringsCount)).longValue(), hints.longValue());
    }


    @Test
    public void testRecursiveCall() throws NoSuchMethodException {
        method = FibonacciServiceImpl.class.getMethod("fibonacci", int.class);
        computedMethodId = methodNamingStrategy.getMethodAsString(method);
        LOGGER.info("FibonacciService method id : " + computedMethodId);

        int result = fibonacciService.fibonacci(20);
        assertEquals(6765,result);

        Map<String,MethodInvocationStats> map = performanceMonitor.getMethodInvocationStatsMap();
        LOGGER.info("stats map : "+map);

        assertNotNull(map);
        AtomicLong hints = map.get(computedMethodId).getHits();
        //that is how AOP works : recursive method call but hint is only one.
        Assert.assertEquals((new AtomicLong(1)).longValue(), hints.longValue());
    }
    @Test
    public void testJmxStuff() throws Exception {
        assertTrue(myJConsole.containsMBeanWithName(monitoringBeanName));
        int stringsCount = 100;
        try {
            myService.setStringsCount(stringsCount);
            myService.listOfStrings("test");
        }catch (Throwable t){}
        assertTrue(myJConsole.isMBeanHasAttribute(monitoringBeanName, computedMethodId));
        Assert.assertNotNull("Expecting attribute "+computedMethodId+" value not null !",myJConsole.getMBeanAttributeValue(monitoringBeanName,computedMethodId));
        String attributeValue = myJConsole.getMBeanAttributeValue(monitoringBeanName,computedMethodId).toString();
        MethodInvocationStats methodInvocationStats = AttributeDetailsUtil.getStatsFromString(attributeValue);
        LOGGER.info("Invocation stats : "+methodInvocationStats.toString());
        assertEquals(stringsCount, methodInvocationStats.getHits().intValue());
    }
}
