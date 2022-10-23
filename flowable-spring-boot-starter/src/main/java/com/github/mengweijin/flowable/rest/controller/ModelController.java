package com.github.mengweijin.flowable.rest.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import com.github.mengweijin.flowable.rest.service.ModelService;
import com.github.mengweijin.layui.model.LayuiTable;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.persistence.entity.ModelEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/22
 */
@Slf4j
@RestController
@RequestMapping("/flowable/process/model")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping(value = "/page")
    public LayuiTable page(@RequestParam(required = false) String filterText, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
        List<ModelEntityImpl> list = modelService.likeSearch(filterText);
        int start = PageUtil.getStart(current - 1, size);
        int end = PageUtil.getEnd(current - 1, size);
        return LayuiTable.data(CollUtil.sub(list, start, end), list.size());
    }

}
