package com.blogspot.ostas.monitoring.test;

import org.junit.Test;

public class AttributeDetailsParsingTest {
    @Test
    public void parseAttributeDetails(){
        String attributeValue = "hits=100, last=0, total=0, min=0, max=0, avg=0.0";
        String[] keyValue = attributeValue.split(",\\s+");
        String key,value;
        for(int i=0;i<keyValue.length;i++){
            key = keyValue[i].split("=")[0];
            value = keyValue[i].split("=")[1];
            System.out.println("key : "+key+" value : "+value);
        }
    }
}
