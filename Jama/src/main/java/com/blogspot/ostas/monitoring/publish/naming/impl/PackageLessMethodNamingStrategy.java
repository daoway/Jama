package com.blogspot.ostas.monitoring.publish.naming.impl;

import com.blogspot.ostas.monitoring.publish.naming.MethodNamingStrategy;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PackageLessMethodNamingStrategy implements MethodNamingStrategy {
    private final Map<Method, String> cache = new ConcurrentHashMap<Method, String>();

    @Override
    public String getMethodAsString(Method method) {
        String methodID = cache.get(method);
        if (methodID == null) {
            final StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append(method.getReturnType().getSimpleName());
            stringBuffer.append(" ");
            stringBuffer.append(method.getDeclaringClass().getSimpleName()); //recheck
            stringBuffer.append("::");
            stringBuffer.append(method.getName());
            final Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != 0) {
                stringBuffer.append("(");
                for (int j = 0; j < paramTypes.length; j++) {
                    stringBuffer.append(paramTypes[j].getSimpleName());
                    if (j != paramTypes.length - 1) {
                        stringBuffer.append(",");
                    }
                }
                stringBuffer.append(")");
            } else {
                stringBuffer.append("()");
            }
            final String id = stringBuffer.toString();
            cache.put(method, id);
            return id;
        } else {
            return methodID;
        }
    }
}
