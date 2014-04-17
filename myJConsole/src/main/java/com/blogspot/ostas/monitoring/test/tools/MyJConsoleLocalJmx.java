package com.blogspot.ostas.monitoring.test.tools;

import org.apache.log4j.Logger;

import javax.management.*;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyJConsoleLocalJmx implements MyJConsole{
    private static final Logger LOGGER = Logger.getLogger(MyJConsoleLocalJmx.class);
    private JMXConnector connector;
    private MBeanServerConnection mbsc;

    public MyJConsoleLocalJmx() {
        mbsc = getLocalJmxConnection();
    }

    @Override
    public MBeanServerConnection getLocalJmxConnection() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        return mBeanServer;
    }
    @Override
    public void closeConnection() throws Exception {
        connector.close();
    }
    @Override
    public Set<ObjectName> listOfMBeans(){
        Set<ObjectName> objectNames = null;
        try {
            objectNames = mbsc.queryNames(null, null);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        for(final ObjectName objectName : objectNames){
            LOGGER.info("found MBean : "+objectName);
        }
        return objectNames;
    }
    @Override
    public boolean containsMBeanWithName(String name){
        try {
            return listOfMBeans().contains(new ObjectName(name));
        } catch (MalformedObjectNameException e) {
            LOGGER.error(e);
        }
        return false;
    }
    @Override
    public Map<String,Object> attributesOfMBeanMap(String mbeanName){
        try{
            final Map<String,Object> attributeValueMap = new HashMap<String, Object>();
            final ObjectName monitoringStats = new ObjectName(mbeanName);
            final MBeanInfo monitoringStatsInfo = mbsc.getMBeanInfo(monitoringStats);
            String attributeName;
            Object attributeValue;
            for(final MBeanAttributeInfo mBeanAttributeInfo : monitoringStatsInfo.getAttributes()){
                attributeName = mBeanAttributeInfo.getName();
                LOGGER.info("attribute name : "+attributeName);
                attributeValue = mbsc.getAttribute(monitoringStats,attributeName);
                LOGGER.info("attribute value : "+attributeValue);
                attributeValueMap.put(attributeName,attributeValue);
            }
            return attributeValueMap;
        }catch (Throwable t){
            LOGGER.error(t);
            throw new RuntimeException(t);
        }
    }
    @Override
    public boolean isMBeanHasAttribute(String mbeanName, String attribute){
        try{
            ObjectName objectName = null;
            try {
                objectName = new ObjectName(mbeanName);
            } catch (MalformedObjectNameException e) {
                LOGGER.error(e);
            }
            final MBeanInfo  monitoringStatsInfo = mbsc.getMBeanInfo(objectName);
            for(final MBeanAttributeInfo mBeanAttributeInfo : monitoringStatsInfo.getAttributes()){
                if(mBeanAttributeInfo.getName().equals(attribute)){
                    return true;
                }
            }
            return false;
        }catch (Throwable t){
            LOGGER.error(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public Object getMBeanAttributeValue(String mbeanName, String attributeName){
        try{
            ObjectName objectName = null;
            try {
                objectName = new ObjectName(mbeanName);
            } catch (MalformedObjectNameException e) {
                LOGGER.error(e);
            }
            return mbsc.getAttribute(objectName,attributeName);
        }catch (Throwable t){
            LOGGER.error(t);
            throw new RuntimeException(t);
        }
    }

}
