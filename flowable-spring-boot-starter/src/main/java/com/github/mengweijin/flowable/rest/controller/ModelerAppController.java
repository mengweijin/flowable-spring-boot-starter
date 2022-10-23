package com.github.mengweijin.flowable.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mengweijin.flowable.rest.copy.representation.ModelRepresentation;
import com.github.mengweijin.flowable.rest.copy.representation.ResultListDataRepresentation;
import com.github.mengweijin.flowable.rest.copy.representation.UserRepresentation;
import com.github.mengweijin.flowable.rest.copy.security.DefaultPrivileges;
import com.github.mengweijin.flowable.rest.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.persistence.entity.ModelEntityImpl;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
public class ModelerAppController {

    @Autowired
    private ModelService modelService;

    /**
     * Refer to RemoteAccountResource.java
     * GET /rest/account -> get the current user.
     */
    @GetMapping(value = "/rest/account", produces = "application/json")
    public UserRepresentation getAccount() {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName("admin");
        userRepresentation.setLastName("admin");
        userRepresentation.setFullName("admin");
        userRepresentation.setId("admin");
        List<String> privileges = new ArrayList<>();
        privileges.add(DefaultPrivileges.ACCESS_MODELER);
        privileges.add(DefaultPrivileges.ACCESS_IDM);
        privileges.add(DefaultPrivileges.ACCESS_ADMIN);
        privileges.add(DefaultPrivileges.ACCESS_TASK);
        privileges.add(DefaultPrivileges.ACCESS_REST_API);
        userRepresentation.setPrivileges(privileges);

        return userRepresentation;
    }

    @Deprecated
    //@GetMapping(value = "/rest/models")
    public ResultListDataRepresentation getModels(@RequestParam(required = false) String filterText) {
        List<ModelEntityImpl> list = modelService.likeSearch(filterText);
        List<ModelRepresentation> result = list.stream().map(ModelRepresentation::toModelRepresentation).collect(Collectors.toList());
        return new ResultListDataRepresentation(result);
    }

    @GetMapping(value = "/rest/models")
    public ResultListDataRepresentation getModelEntity(@RequestParam(required = false) String filterText) {
        List<ModelEntityImpl> list = modelService.likeSearch(filterText);
        return new ResultListDataRepresentation(list);
    }

    @GetMapping(value = "/rest/models/{modelId}")
    public ModelRepresentation getById(@PathVariable("modelId") String modelId) {
        Model model = modelService.getById(modelId);
        return ModelRepresentation.toModelRepresentation(model);
    }

    @GetMapping(value = "/rest/models/{modelId}/model-json")
    public JsonNode getDisplayModelJSON(@PathVariable String modelId) {
        return modelService.getDisplayModelJSON(modelId);
    }

    @GetMapping(value = "/rest/models/{modelId}/history")
    public ResultListDataRepresentation getModelHistoryCollection(@PathVariable String modelId, @RequestParam(value = "includeLatestVersion", required = false) Boolean includeLatestVersion) {
        ModelRepresentation representation = this.getById(modelId);
        return new ResultListDataRepresentation(Collections.singletonList(representation));
    }

    @GetMapping(value = {"/rest/models/{modelId}/editor/json"})
    public ObjectNode getModelJSON(@PathVariable String modelId) {
        return modelService.getModelJSON(modelId);
    }

    @GetMapping(value = "/rest/stencil-sets/editor", produces = "application/json")
    public JsonNode getStencilSetForEditor() {
        return modelService.getStencilSetForEditor();
    }

    @PostMapping("/rest/models")
    public ModelRepresentation create(@RequestBody ModelRepresentation modelRepresentation) {
        Model model = modelService.create(modelRepresentation);
        return ModelRepresentation.toModelRepresentation(model);
    }
}
