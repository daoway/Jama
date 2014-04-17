package com.blogspot.ostas.monitoring.test.tools;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import java.lang.management.ManagementFactory;

public class MyJConsoleLocalJmx extends MyJConsoleAttachAPIImpl{
    @Override
    public MBeanServerConnection getLocalJmxConnection() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        return mBeanServer;
    }
}
