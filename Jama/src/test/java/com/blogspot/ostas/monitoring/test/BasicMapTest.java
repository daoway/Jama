package com.blogspot.ostas.monitoring.test;

import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

public class BasicMapTest {
    @Test
    public void mapTest(){
        MethodInvocationStats methodInvocationStats = new MethodInvocationStats();
        methodInvocationStats.setHits(new AtomicLong(1l));
        Map<String,MethodInvocationStats> methodInvocationStatsMap = new HashMap<String,MethodInvocationStats>();
        methodInvocationStatsMap.put("m1",methodInvocationStats);
        MethodInvocationStats fromMap = methodInvocationStatsMap.get("m1");
        fromMap.getHits().addAndGet(1l);
        assertEquals(2,methodInvocationStatsMap.get("m1").getHits().longValue());
    }
}
