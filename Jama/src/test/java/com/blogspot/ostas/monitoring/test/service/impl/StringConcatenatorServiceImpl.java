package com.blogspot.ostas.monitoring.test.service.impl;

import com.blogspot.ostas.monitoring.annotation.MonitoringTarget;
import com.blogspot.ostas.monitoring.test.service.StringConcatenatorService;

public class StringConcatenatorServiceImpl implements StringConcatenatorService {
    @MonitoringTarget(internalName = "concatenateMethod")
    @Override
    public String addIntToString(String s, int i) {
        final StringBuilder sb = new StringBuilder(s);
        sb.append("_");
        sb.append(i);
        return sb.toString();
    }
}
