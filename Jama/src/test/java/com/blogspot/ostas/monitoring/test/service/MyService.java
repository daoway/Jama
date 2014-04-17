package com.blogspot.ostas.monitoring.test.service;

import java.util.List;

public interface MyService {
    void setStringsCount(int stringsCount);
    int  getStringsCount();
    List<String> listOfStrings(String el);
    void resetCounter();
}
