package com.blogspot.ostas.monitoring.test;

import com.blogspot.ostas.monitoring.interceptor.PerformanceMonitoringInterceptor;
import com.blogspot.ostas.monitoring.publish.naming.MethodNamingStrategy;
import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;
import com.blogspot.ostas.monitoring.test.service.layers.dao.TestDaoLayer;
import com.blogspot.ostas.monitoring.test.service.layers.web.TestWebService;
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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-app-layers.xml","classpath:applicationContext-monitoring.xml"})
public class LayersMonitoringTest {
    private static final Logger LOGGER = Logger.getLogger(LayersMonitoringTest.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PerformanceMonitoringInterceptor performanceMonitor;
    @Autowired
    private TestWebService testWebService;
    @Autowired
    private MethodNamingStrategy methodNamingStrategy;

    @Before
    public void setUp() throws Exception {
        performanceMonitor.clearStats();
    }
    @After
    public void clearCounters(){
        performanceMonitor.clearStats();
    }
    @Test
    public void testContextUp(){
        assertNotNull(applicationContext);
    }
    @Test
    public void testWebService() throws NoSuchMethodException, InterruptedException {
        assertNotNull(testWebService);
        Method testWebServiceMethod1 = TestWebService.class.getMethod("testWebServiceMethod1",String.class);
        String testWebServiceMethod1asString = methodNamingStrategy.getMethodAsString(testWebServiceMethod1);
        Method testDaoLayerMethod            = TestDaoLayer.class.getMethod("doSomethingInDatabase500",String.class);
        String testDaoLayerMethodAsString    = methodNamingStrategy.getMethodAsString(testDaoLayerMethod);
        testWebService.testWebServiceMethod1("test1");
        Map<String, MethodInvocationStats> methodInvocationStatsMap = performanceMonitor.getMethodInvocationStatsMap();
        System.out.println(methodInvocationStatsMap.get(testWebServiceMethod1asString));
        System.out.println(methodInvocationStatsMap.get(testDaoLayerMethodAsString));
    }
}
