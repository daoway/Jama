package com.blogspot.ostas.monitoring.publish.format.impl;

import com.blogspot.ostas.monitoring.publish.format.OutputFormatter;
import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;

public class OutputFormatterToStringImpl implements OutputFormatter {
    @Override
    public String format(MethodInvocationStats methodInvocationStats) {
        return methodInvocationStats.toString();
    }
}
