package com.github.mengweijin.flowable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author mengweijin
 * @date 2022/10/16
 */
@Controller
public class FlowableDefinitionController extends AbstractFlowableController {

    @GetMapping("/definition")
    public String index() {
        return PATH + "/definition/list";
    }

}