package com.blogspot.ostas.monitoring.preprocess.impl;

import com.blogspot.ostas.monitoring.preprocess.PointcutClassesCandidate;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class PointcutClassesCandidateReflectionsImpl implements PointcutClassesCandidate {
    @Override
    public Set<Class<? extends Object>> getClassList() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().
                setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner()
                        //.filterResultsBy(new FilterBuilder().exclude(PointcutClassesCandidateSpringImpl.class.getName())
                )
        );
        return reflections.getSubTypesOf(Object.class);
    }
}
