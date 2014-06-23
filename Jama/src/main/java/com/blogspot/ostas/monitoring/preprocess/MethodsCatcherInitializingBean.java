package com.blogspot.ostas.monitoring.preprocess;

import com.blogspot.ostas.monitoring.interceptor.PerformanceMonitoringInterceptor;
import com.blogspot.ostas.monitoring.publish.naming.MethodNamingStrategy;
import com.blogspot.ostas.monitoring.statistics.MethodInvocationStats;
import org.apache.log4j.Logger;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class MethodsCatcherInitializingBean implements InitializingBean, ApplicationContextAware{
    private static final Logger LOGGER = Logger.getLogger(MethodsCatcherInitializingBean.class);
    private ApplicationContext applicationContext;
    private PerformanceMonitoringInterceptor performanceMonitoringInterceptor;
    private final String pointcutBeanName = "serviceMethods";
    private MethodNamingStrategy methodNamingStrategy;

    public void setPerformanceMonitoringInterceptor(PerformanceMonitoringInterceptor performanceMonitoringInterceptor) {
        this.performanceMonitoringInterceptor = performanceMonitoringInterceptor;
    }
    public void setMethodNamingStrategy(MethodNamingStrategy methodNamingStrategy) {
        this.methodNamingStrategy = methodNamingStrategy;
    }

    /*
         *  Logic behind this method : it obtains reference to pointcut method matcher (Spring AOP api)
         *  which can answer the question - can this particular Method be a joint point defined by pointcut expression
         *  (see bean with name "serviceMethods" for reference).
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        final Set<Class<? extends Object>> classes = new HashSet<>();
        for(String beanName : applicationContext.getBeanDefinitionNames()){
            Object bean = applicationContext.getBean(beanName);
            if (bean instanceof Advised) {
                Class<? extends Object> clazz = ((Advised) bean).getTargetClass();
                classes.add(clazz);
            }
        }

        final Pointcut pointcut = (Pointcut) applicationContext.getBean(pointcutBeanName);
        final MethodMatcher methodMatcher = pointcut.getMethodMatcher();
        for(final Class clazz : classes){
                for(final Method method: clazz.getDeclaredMethods()){
                    if(Modifier.isPublic(method.getModifiers())){
                        if(methodMatcher.matches(method, clazz,method.getParameterTypes())){
                            if(LOGGER.isDebugEnabled()){
                                LOGGER.debug("method matches pointcut : "+method);
                            }
                            MethodInvocationStats emptyMethodInvocationStats = new MethodInvocationStats();
                            emptyMethodInvocationStats.setHits(new AtomicLong(-1));
                            performanceMonitoringInterceptor.getMethodInvocationStatsMap().put(methodNamingStrategy.getMethodAsString(method), emptyMethodInvocationStats);
                        }
                    }
                }
        }
        if(LOGGER.isDebugEnabled()){
           LOGGER.debug("catch methods : "+ performanceMonitoringInterceptor.getMethodInvocationStatsMap());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
