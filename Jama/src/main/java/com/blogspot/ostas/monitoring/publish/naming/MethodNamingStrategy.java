package com.blogspot.ostas.monitoring.publish.naming;

import java.lang.reflect.Method;

public interface MethodNamingStrategy {
    String getMethodAsString(Method method);
}
