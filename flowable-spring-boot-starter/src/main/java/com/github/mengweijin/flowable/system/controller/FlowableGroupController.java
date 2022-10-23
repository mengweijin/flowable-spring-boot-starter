package com.github.mengweijin.flowable.system.controller;

import com.github.mengweijin.flowable.config.AbstractFlowableController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author mengweijin
 * @date 2022/10/16
 */
@Controller
public class FlowableGroupController extends AbstractFlowableController {

    @GetMapping("/group")
    public String index() {
        return PATH + "/group/list";
    }

}