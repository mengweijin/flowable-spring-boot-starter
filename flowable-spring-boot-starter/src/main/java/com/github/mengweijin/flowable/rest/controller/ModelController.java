package com.github.mengweijin.flowable.rest.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mengweijin.flowable.rest.copy.representation.ModelRepresentation;
import com.github.mengweijin.flowable.rest.copy.representation.ResultListDataRepresentation;
import com.github.mengweijin.flowable.rest.service.ModelService;
import com.github.mengweijin.layui.model.LayuiTable;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.persistence.entity.ModelEntityImpl;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/10/22
 */
@Slf4j
@RestController
@RequestMapping("/modeler-app")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping(value = "/rest/models")
    public ResultListDataRepresentation getModels(@RequestParam(required = false) String filterText) {
        List<ModelEntityImpl> list = modelService.likeSearch(filterText);
        List<ModelRepresentation> result = list.stream().map(ModelRepresentation::toModelRepresentation).collect(Collectors.toList());
        return new ResultListDataRepresentation(result);
    }

    @GetMapping(value = "/rest/models/page")
    public LayuiTable page(@RequestParam(required = false) String filterText, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
        List<ModelEntityImpl> list = modelService.likeSearch(filterText);
        int start = PageUtil.getStart(current - 1, size);
        int end = PageUtil.getEnd(current - 1, size);
        return LayuiTable.data(CollUtil.sub(list, start, end), list.size());
    }

    @GetMapping(value = "/rest/models/{modelId}")
    public ModelRepresentation getById(@PathVariable("modelId") String modelId) {
        Model model = modelService.getById(modelId);
        return ModelRepresentation.toModelRepresentation(model);
    }

    @GetMapping(value = {"/rest/models/{modelId}/editor/json"})
    public ObjectNode getModelJSON(@PathVariable String modelId) {
        return modelService.getModelJSON(modelId);
    }

    @GetMapping(value = "/rest/models/{modelId}/model-json")
    public JsonNode getDisplayModelJSON(@PathVariable String modelId) {
        return modelService.getDisplayModelJSON(modelId);
    }


    @GetMapping(value = "/rest/stencil-sets/editor", produces = "application/json")
    public JsonNode getStencilSetForEditor() {
        return modelService.getStencilSetForEditor();
    }

    @GetMapping(value = "/rest/models/{modelId}/history")
    public ResultListDataRepresentation getModelHistoryCollection(@PathVariable String modelId, @RequestParam(value = "includeLatestVersion", required = false) Boolean includeLatestVersion) {
        ModelRepresentation representation = this.getById(modelId);
        return new ResultListDataRepresentation(Collections.singletonList(representation));
    }

    @PostMapping("/rest/models")
    public ModelRepresentation create(@RequestBody ModelRepresentation modelRepresentation) {
        Model model = modelService.create(modelRepresentation);
        return ModelRepresentation.toModelRepresentation(model);
    }

    @DeleteMapping(value = "/rest/models/{modelId}")
    public void delete(@PathVariable String modelId) {
        modelService.deleteById(modelId);
    }
}
