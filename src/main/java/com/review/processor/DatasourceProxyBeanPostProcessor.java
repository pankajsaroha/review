package com.review.processor;

import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;

/*
* To log the sql statements to check if batch is working
* https://stackoverflow.com/questions/66514455/spring-boot-jpa-repository-batch-insert-not-working-solved
* */
@Component
public class DatasourceProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization (final Object bean, final String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization (final Object bean, final String beanName) {
        if (bean instanceof DataSource) {
            ProxyFactory factory = new ProxyFactory(bean);
            factory.setProxyTargetClass(true);
            factory.addAdvice(new ProxyDatasourceInterceptor((DataSource) bean));
            return factory.getProxy();
        }
        return bean;
    }

    private static class ProxyDatasourceInterceptor implements MethodInterceptor {

        private final DataSource dataSource;

        public ProxyDatasourceInterceptor (final DataSource dataSource) {
            this.dataSource = ProxyDataSourceBuilder.create(dataSource).countQuery().logQueryBySlf4j(SLF4JLogLevel.INFO).build();
        }

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            Method proxyMethod = ReflectionUtils.findMethod(dataSource.getClass(), methodInvocation.getMethod().getName());
            if (proxyMethod != null) {
                return proxyMethod.invoke(dataSource, methodInvocation.getArguments());
            }
            return methodInvocation.proceed();
        }
    }
}
