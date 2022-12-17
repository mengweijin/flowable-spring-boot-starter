package com.github.mengweijin.flowable.rest.controller;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.vitality.layui.LayuiPage;
import com.github.mengweijin.vitality.layui.LayuiTable;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.rest.service.api.repository.ModelCollectionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private ModelCollectionResource modelCollectionResource;
    @Autowired
    private RepositoryService repositoryService;

    @GetMapping(value = "/list", produces = "application/json")
    public LayuiTable<Model> getModels(String name, String key, LayuiPage page) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if(StrUtil.isNotBlank(name)) {
            modelQuery.modelNameLike(name.trim());
        }
        if(StrUtil.isNotBlank(key)) {
            modelQuery.modelKey(key);
        }
        List<Model> modelList = modelQuery.orderByLastUpdateTime().desc().listPage(Math.toIntExact(page.getPage() - 1), Math.toIntExact(page.getLimit()));
        return new LayuiTable<>(modelList, modelQuery.count());
    }
}
