package com.blogspot.ostas.monitoring.client.tools;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.util.Map;
import java.util.Set;

public interface MyJConsole {
    MBeanServerConnection getLocalJmxConnection();

    void closeConnection() throws Exception;

    Set<ObjectName> listOfMBeans();

    boolean containsMBeanWithName(String name);

    Map<String,Object> attributesOfMBeanMap(String mbeanName);

    boolean isMBeanHasAttribute(String mbeanName, String attribute);

    Object getMBeanAttributeValue(String mbeanName, String attributeName);
}
