package com.blogspot.ostas.monitoring.test;

import com.blogspot.ostas.monitoring.interceptor.PerformanceMonitoringInterceptor;
import com.blogspot.ostas.monitoring.test.service.EmptyPerformanceMonitoringInterceptor;
import com.blogspot.ostas.monitoring.test.tools.MyJConsole;
import com.blogspot.ostas.monitoring.test.tools.MyJConsoleLocalJmx;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-app-empty-interceptor.xml"})
public class EmptyInterceptorTest {
    private static final Logger LOGGER = Logger.getLogger(EmptyInterceptorTest.class);
    private final MyJConsole myJConsole = new MyJConsoleLocalJmx();//new MyJConsoleAttachAPIImpl();
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PerformanceMonitoringInterceptor performanceMonitor;
    @Test
    public void testContextUp(){
        assertNotNull(applicationContext);
        Assert.assertTrue(applicationContext.getBean("performanceMonitor") instanceof EmptyPerformanceMonitoringInterceptor);
    }

}
