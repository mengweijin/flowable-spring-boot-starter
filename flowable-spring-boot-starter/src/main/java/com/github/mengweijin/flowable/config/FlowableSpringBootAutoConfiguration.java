package com.github.mengweijin.flowable.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 增加 RestResponseFactory 和 ContentTypeResolver 提供 rest服务。flowable 官方 starter 默认不提供。
 * @author Meng Wei Jin
 **/

@Configuration
public class FlowableSpringBootAutoConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Bean
    public FlowableBeanDefinitionRegistryPostProcessor flowableBeanDefinitionRegistryPostProcessor() {
        return new FlowableBeanDefinitionRegistryPostProcessor();
    }

    /**
     * 解决中文乱码
     */
    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        //engineConfiguration.setActivityFontName("宋体");
        //engineConfiguration.setLabelFontName("宋体");
        //engineConfiguration.setAnnotationFontName("宋体");
    }
}
