package com.github.mengweijin.flowable;

import com.github.mengweijin.flowable.processor.FlowableBeanDefinitionRegistryPostProcessor;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 去掉登录验证后，直接访问：http://localhost:8081/modeler/#/processes
 * @author Meng Wei Jin
 **/

@Configuration(proxyBeanMethods = false)
public class FlowableSecurityAutoConfiguration implements WebMvcConfigurer, EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    /**
     * 解决中文乱码
     */
    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        //engineConfiguration.setActivityFontName("宋体");
        //engineConfiguration.setLabelFontName("宋体");
        //engineConfiguration.setAnnotationFontName("宋体");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Bean
    public FlowableBeanDefinitionRegistryPostProcessor flowableBeanDefinitionRegistryPostProcessor() {
        return new FlowableBeanDefinitionRegistryPostProcessor();
    }
}
