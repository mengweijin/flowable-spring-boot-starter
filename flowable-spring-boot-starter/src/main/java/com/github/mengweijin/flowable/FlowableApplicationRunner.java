package com.github.mengweijin.flowable;

import com.github.mengweijin.flowable.system.controller.FlowableUserController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * npm install bpmn-js bpmn-js-properties-panel camunda-bpmn-moddle --save
 * @author mengweijin
 * @date 2022/10/16
 */
@Component
public class FlowableApplicationRunner implements ApplicationRunner {

    //@Autowired
    private FlowableUserController flowableUserController;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println();
    }
}
