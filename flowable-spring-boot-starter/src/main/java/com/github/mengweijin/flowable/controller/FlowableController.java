package com.github.mengweijin.flowable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author mengweijin
 * @date 2022/10/16
 */
@Controller
public class FlowableController extends AbstractFlowableController {

    @GetMapping()
    public String index() {
        return PATH + "/index";
    }

}