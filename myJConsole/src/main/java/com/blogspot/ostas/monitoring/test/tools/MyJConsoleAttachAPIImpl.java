package com.blogspot.ostas.monitoring.test.tools;

import com.sun.tools.attach.*;
import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyJConsoleAttachAPIImpl implements MyJConsole {
    private static final Logger LOGGER = Logger.getLogger(MyJConsoleAttachAPIImpl.class);
    private JMXConnector connector;
    private MBeanServerConnection mbsc;

    public MyJConsoleAttachAPIImpl() {
            mbsc = getLocalJmxConnection();
    }

    @Override
    public MBeanServerConnection getLocalJmxConnection(){
        for(VirtualMachineDescriptor virtualMachineDescriptor : VirtualMachine.list()){
            LOGGER.info("Listing JVM's : \n"+virtualMachineDescriptor);
            LOGGER.info("\t"+virtualMachineDescriptor.id());
            LOGGER.info("\t"+virtualMachineDescriptor.displayName());
        }
        try{
            String sigarBinPath = "lib";
            System.setProperty("java.library.path", System.getProperty("java.library.path") + File.separator + sigarBinPath);
            Sigar sigar = new Sigar();
            String pid = String.valueOf(sigar.getPid());
            VirtualMachine vm = VirtualMachine.attach(pid);
            LOGGER.info("Connecting to JVM : "+vm);
            String javaHome = vm.getSystemProperties().getProperty("java.home");
            String agentJar = javaHome + File.separator +  "lib" + File.separator + "management-agent.jar";
            vm.loadAgent(agentJar, "com.sun.management.jmxremote");
            String localConnectorAddress = vm.getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress");
            if (localConnectorAddress == null) {
                // Check system properties
                localConnectorAddress = vm.getSystemProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress");
            }
            vm.detach();
            LOGGER.info("Local connector address = " + localConnectorAddress);

            JMXServiceURL jmxURL = new JMXServiceURL(localConnectorAddress);
            connector = JMXConnectorFactory.connect(jmxURL);
            mbsc = connector.getMBeanServerConnection();
            return mbsc;
        }catch (Throwable t){
            LOGGER.error("Fail to init myJConsole! : ",t);
        }
        return null;
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
            final MBeanInfo  monitoringStatsInfo = mbsc.getMBeanInfo(monitoringStats);
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
