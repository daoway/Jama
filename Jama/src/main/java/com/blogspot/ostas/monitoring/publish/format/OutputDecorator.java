package com.blogspot.ostas.monitoring.publish.format;

import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;

public interface OutputDecorator {
    String format(MethodInvocationStats methodInvocationStats);
}
