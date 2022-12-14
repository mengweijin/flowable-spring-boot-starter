package com.github.mengweijin.flowable.config;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.flowable.rest.servlet.FlowableServletContextListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @author mengweijin
 * @date 2022/7/27
 */
public class FlowableBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        String pkg = ClassUtil.getPackage(FlowableBeanDefinitionRegistryPostProcessor.class);
        pkg = StrUtil.subBefore(pkg, ".", true);
        scanner.scan(pkg);

        pkg = ClassUtil.getPackage(FlowableServletContextListener.class);
        pkg = StrUtil.subBefore(pkg, ".", true);
        scanner.scan(pkg);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

}
