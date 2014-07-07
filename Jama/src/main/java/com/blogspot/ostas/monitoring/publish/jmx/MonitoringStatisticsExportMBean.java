package com.blogspot.ostas.monitoring.publish.jmx;

import com.blogspot.ostas.monitoring.interceptor.PerformanceMonitoringInterceptor;
import com.blogspot.ostas.monitoring.publish.format.OutputDecorator;
import org.apache.log4j.Logger;

import javax.management.*;

@SuppressWarnings("UnusedDeclaration")
public class MonitoringStatisticsExportMBean implements DynamicMBean {

    private static final Logger LOGGER = Logger.getLogger(MonitoringStatisticsExportMBean.class);
    private static final boolean DEBUG = LOGGER.isDebugEnabled();

    private final PerformanceMonitoringInterceptor performanceMonitoringInterceptor;
    private final OutputDecorator outputDecorator;

    private MonitoringStatisticsExportMBean(PerformanceMonitoringInterceptor performanceMonitoringInterceptor, OutputDecorator outputDecorator) {
        this.performanceMonitoringInterceptor = performanceMonitoringInterceptor;
        this.outputDecorator = outputDecorator;
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
          return outputDecorator.format(performanceMonitoringInterceptor.getMethodInvocationStatsMap().get(attribute));
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
