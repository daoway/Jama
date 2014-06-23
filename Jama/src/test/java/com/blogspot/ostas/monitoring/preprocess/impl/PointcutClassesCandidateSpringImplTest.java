package com.blogspot.ostas.monitoring.preprocess.impl;

import com.blogspot.ostas.monitoring.preprocess.PointcutClassesCandidate;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-app.xml","classpath:applicationContext-monitoring.xml"})
public class PointcutClassesCandidateSpringImplTest {
    private static final Logger LOGGER = Logger.getLogger(PointcutClassesCandidateSpringImpl.class);
    @Autowired
    PointcutClassesCandidate pointcutClassesCandidateSpring;

    @Test
    public void canWeFindAllAdvisedBeansByAspectName(){
        assertNotNull(pointcutClassesCandidateSpring);
        Set<Class <? extends Object>> classes =  pointcutClassesCandidateSpring.getClassList();
        assertNotNull(classes);
        LOGGER.info(classes);
        assertEquals(2,classes.size()); //expecting two classes in tests with annotation @MonitoringTarget
    }
}
