What is it.
---------
Jama stands for "Java Application Monitoring and Alerting" library. The main purpose of this tool is to publish runtime application metrics via JMX.

Why to use it.
---------
It's _simple_ to integrate and use it. If you have Spring based application and want to publish some of runtime performance metrics, e.q. method invocation count, min, max, avg time - give it a try. At this moment only JMX is supported. But this enought for exporting JMX counters to another tool from entrprise ecosystem like
HP Site Scope etc.

How to use.
----------
This library published to Maven central, so add this entry to your pom.xml
```xml
<dependency>
    <groupId>com.blogspot.ostas</groupId>
    <artifactId>Jama</artifactId>
    <version>1.0.2</version>
</dependency>
```
Put @MonitoringTarget annotation on service method (String bean method) you want to monitor. Please notice that internal name is optional. :
```java
public class GreetingServiceImpl implements GreetingService {
    @MonitoringTarget(internalName = "internalName")
    @Override
    public String greet(String who) {
        return String.format("hello, %s!", who);
    }
}
```
Define your bean as usual, but add import of monitoring routines from Jama library.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

  <import resource="classpath:applicationContext-monitoring.xml"/>

  <bean id="greetingService" class="com.blogspot.ostas.examples.helloworld.spring.GreetingServiceImpl"/>

</beans>
```

Give the name to JMX bean for exported runtime monitoring metrics. Put monitoring.properties file to classpath with the following content, for example
```text
monitoring.jmx.export.bean.name=com.blogspot.ostas.examples.helloworld.spring:name=Monitoring
```
See example JamaExampleWebApp for reference.

[![Build Status](https://buildhive.cloudbees.com/job/daoway/job/Jama/badge/icon)](https://buildhive.cloudbees.com/job/daoway/job/Jama/)
