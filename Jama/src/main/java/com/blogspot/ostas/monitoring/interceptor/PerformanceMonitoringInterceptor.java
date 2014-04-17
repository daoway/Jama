package com.blogspot.ostas.monitoring.interceptor;

import com.blogspot.ostas.monitoring.publish.naming.MethodNamingStrategy;
import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PerformanceMonitoringInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = Logger.getLogger(PerformanceMonitoringInterceptor.class);
    private static final boolean DEBUG = LOGGER.isDebugEnabled();

    private Map<String, MethodInvocationStats> methodInvocationStatsMap;

    public PerformanceMonitoringInterceptor() {
        methodInvocationStatsMap = new ConcurrentHashMap<String, MethodInvocationStats>();
    }

    public final Map<String, MethodInvocationStats> getMethodInvocationStatsMap() {
        return methodInvocationStatsMap;
    }

    private MethodNamingStrategy methodNamingStrategy;

    public void setMethodNamingStrategy(MethodNamingStrategy methodNamingStrategy) {
        this.methodNamingStrategy = methodNamingStrategy;
    }

    protected Object calculateStatsOnMethodInvocation(MethodInvocation methodInvocation) throws Throwable {
        final ReflectiveMethodInvocation reflectiveMethodInvocation = (ReflectiveMethodInvocation) methodInvocation;
        final Class targetClass = AopUtils.getTargetClass(reflectiveMethodInvocation.getProxy());
        //final Method currentMethod = AopUtils.getMostSpecificMethod(methodInvocation.getMethod(), targetClass);
        final Method candidateMethod = methodInvocation.getMethod();
        final Method currentMethod = targetClass.getMethod(candidateMethod.getName(), candidateMethod.getParameterTypes());
        long start = System.currentTimeMillis();
        try {
            return methodInvocation.proceed();
        } finally {
            long execTime = System.currentTimeMillis() - start;
            final String methodID = methodNamingStrategy.getMethodAsString(currentMethod);
            MethodInvocationStats methodInvocationStats = methodInvocationStatsMap.get(methodID);
            if (methodInvocationStats == null) {
                LOGGER.error("method is null : " + methodID);
                throw new RuntimeException("Method with id : " + methodID + " not found in stats map : " + methodInvocationStatsMap.keySet());
            }
            if (methodInvocationStats.getHits().longValue() == -1l) {
                methodInvocationStats.setHits(new AtomicLong(1));
                methodInvocationStats.setLatestExecTime(new AtomicLong(execTime));
                methodInvocationStats.setTotalExecTime(new AtomicLong(execTime));
                methodInvocationStats.setMinExecTime(new AtomicLong(execTime));
                methodInvocationStats.setMaxExecTime(new AtomicLong(execTime));
            } else {
                methodInvocationStats.getHits().incrementAndGet();
                methodInvocationStats.getLatestExecTime().set(execTime);
                methodInvocationStats.getTotalExecTime().addAndGet(methodInvocationStats.getLatestExecTime().get());
                if (methodInvocationStats.getMaxExecTime().get() < execTime) {
                    methodInvocationStats.getMaxExecTime().set(execTime);
                }
                if (methodInvocationStats.getMinExecTime().get() > execTime) {
                    methodInvocationStats.getMinExecTime().set(execTime);
                }
            }

            if (DEBUG) {
                LOGGER.debug(new StringBuilder()
                        .append(methodID)
                        .append(" stats : ")
                        .append(methodInvocationStatsMap.get(methodID)));
            }
        }
    }
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return calculateStatsOnMethodInvocation(methodInvocation);
    }

    public void clearStats() {
        for (final Map.Entry<String, MethodInvocationStats> methodInvocationStats : methodInvocationStatsMap.entrySet()) {
            MethodInvocationStats invocationStats = new MethodInvocationStats();
            invocationStats.setHits(new AtomicLong(-1));
            invocationStats.setLatestExecTime(new AtomicLong(0));
            invocationStats.setMinExecTime(new AtomicLong(0));
            invocationStats.setTotalExecTime(new AtomicLong(0));
            methodInvocationStats.setValue(invocationStats);
        }
    }
}
