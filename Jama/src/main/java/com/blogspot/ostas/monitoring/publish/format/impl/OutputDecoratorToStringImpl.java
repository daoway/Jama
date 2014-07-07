package com.blogspot.ostas.monitoring.publish.format.impl;

import com.blogspot.ostas.monitoring.publish.format.OutputDecorator;
import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;

public class OutputDecoratorToStringImpl implements OutputDecorator {
    @Override
    public String format(MethodInvocationStats methodInvocationStats) {
        return methodInvocationStats.toString();
    }
}
