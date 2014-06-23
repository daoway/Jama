package com.blogspot.ostas.monitoring.preprocess.impl;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertNotNull;

@Ignore // impl is not ready yet
public class PointcutClassesCandidateReflectionsImplTest {
    private static final Logger LOGGER = Logger.getLogger(PointcutClassesCandidateReflectionsImpl.class);
    @Test
    public void testGetClassList(){
            Set<Class <? extends Object>> classes =  new PointcutClassesCandidateReflectionsImpl().getClassList();
            assertNotNull(classes);
            LOGGER.info(classes);
    }
}
