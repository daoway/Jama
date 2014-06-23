package com.blogspot.ostas.monitoring.preprocess;

import java.util.Set;

public interface PointcutClassesCandidate {
    Set<Class<? extends Object>> getClassList();
}
