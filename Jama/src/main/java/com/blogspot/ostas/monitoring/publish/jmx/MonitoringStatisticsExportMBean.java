package com.blogspot.ostas.monitoring.publish.jmx;

import com.blogspot.ostas.monitoring.interceptor.PerformanceMonitoringInterceptor;
import org.apache.log4j.Logger;

import javax.management.*;

@SuppressWarnings("UnusedDeclaration")
public class MonitoringStatisticsExportMBean implements DynamicMBean {

    private static final Logger LOGGER = Logger.getLogger(MonitoringStatisticsExportMBean.class);
    private static final boolean DEBUG = LOGGER.isDebugEnabled();

    private final PerformanceMonitoringInterceptor performanceMonitoringInterceptor;

    private MonitoringStatisticsExportMBean(PerformanceMonitoringInterceptor performanceMonitoringInterceptor) {
        this.performanceMonitoringInterceptor = performanceMonitoringInterceptor;
    }

    private MBeanAttributeInfo[] prepareJmxAttributes() {
        MBeanAttributeInfo[] beanAttributeInfos = new MBeanAttributeInfo[performanceMonitoringInterceptor.getMethodInvocationStatsMap().size()];
        int index = 0;
        for (String method : performanceMonitoringInterceptor.getMethodInvocationStatsMap().keySet()) {
            beanAttributeInfos[index] = new MBeanAttributeInfo(method, "java.lang.String", "", true, false, false);
            index++;
        }
        if(DEBUG){
            LOGGER.debug("jmx attribute methods : "+ performanceMonitoringInterceptor.getMethodInvocationStatsMap().keySet());
        }
        return beanAttributeInfos;
    }

    @Override
    public Object getAttribute(String attribute) {
        try {
            if (performanceMonitoringInterceptor.getMethodInvocationStatsMap().containsKey(attribute)) {
                final String value = performanceMonitoringInterceptor.getMethodInvocationStatsMap().get(attribute).toString();
                if (DEBUG) {
                    LOGGER.debug("[JMX_ATTRIBUTE] " + value);
                }
                return value;
            }
        } catch (Throwable throwable) {
            LOGGER.error(throwable.fillInStackTrace());
        }
        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) {
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return null;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) {
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return new MBeanInfo(MonitoringStatisticsExportMBean.class.getName(),
                "System Runtime Performance Metrics",
                prepareJmxAttributes(),
                new MBeanConstructorInfo[0],
                new MBeanOperationInfo[0],
                null);
    }
}
