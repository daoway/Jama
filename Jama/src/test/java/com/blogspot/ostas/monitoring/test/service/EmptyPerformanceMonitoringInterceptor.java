package com.blogspot.ostas.monitoring.test.service;

import com.blogspot.ostas.monitoring.interceptor.PerformanceMonitoringInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class EmptyPerformanceMonitoringInterceptor extends PerformanceMonitoringInterceptor {
    @Override
    protected Object calculateStatsOnMethodInvocation(MethodInvocation methodInvocation) throws Throwable {
        return methodInvocation.proceed();
    }
}
