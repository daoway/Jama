package com.blogspot.ostas.monitoring.publish.format;

import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;

public interface OutputFormatter {
    String format(MethodInvocationStats methodInvocationStats);
}
