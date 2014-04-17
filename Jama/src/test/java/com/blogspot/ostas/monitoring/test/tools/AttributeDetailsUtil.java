package com.blogspot.ostas.monitoring.test.tools;


import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;

import java.util.concurrent.atomic.AtomicLong;

public class AttributeDetailsUtil {
    public static MethodInvocationStats getStatsFromString(String string){
        MethodInvocationStats methodInvocationStats = new MethodInvocationStats();
        String[] keyValue = string.split(",\\s+");
        String key,value;

        for(int i=0;i<keyValue.length;i++){
            key = keyValue[i].split("=")[0];
            value = keyValue[i].split("=")[1];
            System.out.println("key : "+key+" value : "+value);
            if(key.equals("hits")) {
                methodInvocationStats.setHits(new AtomicLong(Long.valueOf(value)));
            }else
            if(key.equals("last")){
                methodInvocationStats.setLatestExecTime(new AtomicLong(Long.valueOf(value)));
            }else
            if(key.equals("total")){
                methodInvocationStats.setTotalExecTime(new AtomicLong(Long.valueOf(value)));
            }else
            if(key.equals("min")){
                methodInvocationStats.setMinExecTime(new AtomicLong(Long.valueOf(value)));
            }else
            if(key.equals("max")){
                methodInvocationStats.setMaxExecTime(new AtomicLong(Long.valueOf(value)));
            }
        }
        return methodInvocationStats;
    }
}
