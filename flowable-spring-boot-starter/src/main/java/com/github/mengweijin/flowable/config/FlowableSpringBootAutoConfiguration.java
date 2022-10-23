package com.github.mengweijin.flowable.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.flowable.idm.DBConfiguration;
import com.github.mengweijin.flowable.idm.DBIdmEngineConfigurator;
import org.flowable.rest.service.api.RestResponseFactory;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 增加 RestResponseFactory 和 ContentTypeResolver 提供 rest服务。flowable 官方 starter 默认不提供。
 * @author Meng Wei Jin
 **/

@Configuration
public class FlowableSpringBootAutoConfiguration implements WebMvcConfigurer, EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Bean
    public FlowableBeanDefinitionRegistryPostProcessor flowableBeanDefinitionRegistryPostProcessor() {
        return new FlowableBeanDefinitionRegistryPostProcessor();
    }

    @Bean
    public RestResponseFactory restResponseFactory(ObjectMapper objectMapper) {
        return new RestResponseFactory(objectMapper);
    }

    /**
     * 解决中文乱码
     */
    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        //engineConfiguration.setActivityFontName("宋体");
        //engineConfiguration.setLabelFontName("宋体");
        //engineConfiguration.setAnnotationFontName("宋体");

        DBIdmEngineConfigurator dbIdmEngineConfigurator = new DBIdmEngineConfigurator();
        dbIdmEngineConfigurator.setDbConfiguration(new DBConfiguration());
        engineConfiguration.setIdmEngineConfigurator(dbIdmEngineConfigurator);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/flowable/process/model").setViewName("flowable/model/list.html");
    }
}
