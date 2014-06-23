package com.blogspot.ostas.monitoring.preprocess.impl;

import com.blogspot.ostas.monitoring.preprocess.PointcutClassesCandidate;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.HashSet;
import java.util.Set;

public class PointcutClassesCandidateSpringImpl implements BeanPostProcessor, PointcutClassesCandidate {
	private static final Logger LOG = Logger.getLogger(PointcutClassesCandidateSpringImpl.class);
    private static Set<Class<? extends Object>> classList = new HashSet<>();
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(LOG.isDebugEnabled()){
            LOG.debug("inspecting bean... "+bean);
        }
		if (bean instanceof Advised) {
            Class clazz = ((Advised) bean).getTargetClass();
            classList.add(clazz);//TODO :// more correct class filtering possible here.
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

    @Override
    public Set<Class<? extends Object>> getClassList() {
        return classList;
    }
}
