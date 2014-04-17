package com.blogspot.ostas.monitoring.preprocess;

import org.apache.log4j.Logger;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public class AdviceNotifier implements BeanPostProcessor {
	private static final Logger LOG = Logger.getLogger(AdviceNotifier.class);
    private List<Class> classList = new ArrayList<Class>();
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

    public List<Class> getClassList() {
        return classList;
    }
}
