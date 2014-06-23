package com.blogspot.ostas.monitoring.preprocess.impl;

import com.blogspot.ostas.monitoring.preprocess.PointcutClassesCandidate;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PointcutClassesCandidateOpenPojosImpl implements PointcutClassesCandidate {
    @Override
    public Set<Class<? extends Object>> getClassList() {
        Set<Class <? extends Object>> classes = new HashSet<>();
        //final List<PojoClass> pojoClasses = PojoClassFactory.getPojoClassesRecursively("com.blogspot.ostas.monitoring", null);
        final List<PojoClass> pojoClasses = PojoClassFactory.getPojoClassesRecursively("com.blogspot.ostas.monitoring", null);
        for(PojoClass pojoClass : pojoClasses){
            classes.add(pojoClass.getClazz());
        }
        return classes;
    }
}
