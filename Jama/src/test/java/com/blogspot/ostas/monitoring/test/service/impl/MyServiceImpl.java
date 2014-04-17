package com.blogspot.ostas.monitoring.test.service.impl;

import com.blogspot.ostas.monitoring.test.service.MyService;
import com.blogspot.ostas.monitoring.test.service.StringConcatenatorService;

import java.util.ArrayList;
import java.util.List;

public class MyServiceImpl implements MyService {
    private StringConcatenatorService stringConcatenatorService;
    private int stringsCount;
    public void setStringConcatenatorService(StringConcatenatorService stringConcatenatorService) {
        this.stringConcatenatorService = stringConcatenatorService;
    }

    @Override
    public void setStringsCount(int stringsCount) {
        this.stringsCount = stringsCount;
    }

    @Override
    public int getStringsCount() {
        return stringsCount;
    }

    @Override
    public List<String> listOfStrings(String el) {
        List<String> result = new ArrayList<String>(getStringsCount());
        for(int i=0;i<stringsCount;i++){
            result.add(stringConcatenatorService.addIntToString(el,i));
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void resetCounter() {
        stringsCount = 0;
    }
}
