package com.blogspot.ostas.monitoring.test.service.impl;

import com.blogspot.ostas.monitoring.annotation.MonitoringTarget;
import com.blogspot.ostas.monitoring.test.service.StringConcatenatorService;
import com.codahale.metrics.annotation.Timed;

public class StringConcatenationServiceImpl implements StringConcatenatorService {
    @MonitoringTarget(internalName = "concatenateMethod") //jama marker annotation
    @Timed //used by code hale matrics
    @Override
    public String addIntToString(String s, int i) {
        final StringBuilder sb = new StringBuilder(s);
        sb.append("_");
        sb.append(i);
        return sb.toString();
    }
}
