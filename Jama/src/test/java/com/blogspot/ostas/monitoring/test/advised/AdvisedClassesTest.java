package com.blogspot.ostas.monitoring.test.advised;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-app.xml","classpath:applicationContext-monitoring.xml"})
public class AdvisedClassesTest {
    @Test
    public void canWeFindAllAdvisedBeansByAspectName(){
        assertTrue(true);
    }
}
