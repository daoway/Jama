package com.blogspot.ostas.monitoring.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Method;

public class MethodsNamingStrategyTest {
    private static final Logger LOGGER = Logger.getLogger(MethodsNamingStrategyTest.class);
    @Test
    public void testNameCut(){
        Class<?> clazz = GenericApplicationContext.class;
        Method[] methods = clazz.getMethods();
        for(int i=0;i<methods.length;i++){
            StringBuffer stringBuffer = new StringBuffer();
            Method method = methods[i];
            LOGGER.info("Method : "+method);
            LOGGER.info("Method return type : "+method.getReturnType().getSimpleName());
            stringBuffer.append(method.getReturnType().getSimpleName());
            stringBuffer.append(" ");
            LOGGER.info("Method of : "+clazz.getSimpleName());
            stringBuffer.append(clazz.getSimpleName());
            stringBuffer.append("::");
            LOGGER.info("Method simple name : "+method.getName());
            stringBuffer.append(method.getName());
            Class<?>[] paramTypes = method.getParameterTypes();
            if(paramTypes.length!=0){
                stringBuffer.append("(");
                for(int j=0;j<paramTypes.length;j++){
                    LOGGER.info("Method param simple name : " + paramTypes[j].getSimpleName());
                    stringBuffer.append(paramTypes[j].getSimpleName());
                    if(j != paramTypes.length-1){
                        stringBuffer.append(",");
                    }
                }
                stringBuffer.append(")");
            }else{
                stringBuffer.append("()");
            }
            System.out.println("computed id : "+stringBuffer.toString());
        }
    }
}
