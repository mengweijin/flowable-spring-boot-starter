package com.github.mengweijin.flowable.rest.service;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mengweijin.flowable.exception.FlowableApiException;
import com.github.mengweijin.flowable.rest.copy.representation.ModelRepresentation;
import com.github.mengweijin.flowable.rest.copy.BpmnDisplayJsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.GraphicInfo;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.persistence.entity.ModelEntityImpl;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/23
 */
@Slf4j
@Service
public class ModelService {

    public static final String ACT_RE_MODEL_QUERY = "SELECT ID_ as id, REV_ as revision, NAME_ as name, KEY_ as 'key', CATEGORY_ as category, " +
            "CREATE_TIME_ as create_time, LAST_UPDATE_TIME_ as lastUpdateTime, VERSION_ as version, " +
            "META_INFO_ as metaInfo, DEPLOYMENT_ID_ as deploymentId, EDITOR_SOURCE_VALUE_ID_ as editorSourceValueId, " +
            "EDITOR_SOURCE_EXTRA_VALUE_ID_ as editorSourceExtraValueId, TENANT_ID_ as tenantId FROM ACT_RE_MODEL ";

    protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private BpmnDisplayJsonConverter bpmnDisplayJsonConverter;

    public List<ModelEntityImpl> likeSearch(String filterText) {
        String sql = ACT_RE_MODEL_QUERY;
        if(StrUtil.isNotBlank(filterText)) {
            String text = "'%" + filterText.trim() + "%'";
            sql += " WHERE NAME_ LIKE " + text + " OR KEY_ LIKE " + text;
        }
        sql += " ORDER BY CREATE_TIME_ DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ModelEntityImpl.class));
    }

    public Model create(ModelRepresentation representation) {
        representation.setKey(representation.getKey().replaceAll(" ", ""));
        long count = repositoryService.createModelQuery().modelKey(representation.getKey()).count();
        if(count > 0) {
            throw new FlowableApiException("Provided model key already exists: " + representation.getKey());
        }

        String editorJson = ModelRepresentation.createModelJson(objectMapper, representation);
        org.flowable.engine.repository.Model model = repositoryService.newModel();
        model.setMetaInfo(editorJson);
        model.setName(representation.getName());
        model.setKey(StringUtils.defaultString(representation.getKey()));

        repositoryService.saveModel(model);
        repositoryService.addModelEditorSource(model.getId(), editorJson.getBytes(StandardCharsets.UTF_8));

        return repositoryService.createModelQuery().modelId(model.getId()).singleResult();
    }

    public Model getById(String modelId) {
        return repositoryService.createModelQuery().modelId(modelId).singleResult();
    }

    public ObjectNode getModelJSON(String modelId) {
        Model model = this.getById(modelId);
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put("modelId", model.getId());
        modelNode.put("name", model.getName());
        modelNode.put("key", model.getKey());
        modelNode.put("description", "");
        modelNode.putPOJO("lastUpdated", model.getLastUpdateTime());
        modelNode.put("lastUpdatedBy", "");
        if (StringUtils.isNotEmpty(model.getMetaInfo())) {
            try {
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                editorJsonNode.put("modelType", "model");
                modelNode.set("model", editorJsonNode);
            } catch (Exception e) {
                log.error("Error reading editor json {}", modelId, e);
                throw new FlowableApiException("Error reading editor json " + modelId);
            }

        } else {
            ObjectNode editorJsonNode = objectMapper.createObjectNode();
            editorJsonNode.put("id", "canvas");
            editorJsonNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorJsonNode.put("modelType", "model");
            modelNode.set("model", editorJsonNode);
        }
        return modelNode;
    }

    public JsonNode getStencilSetForEditor() {
        try {
            JsonNode stencilNode = objectMapper.readTree(this.getClass().getClassLoader().getResourceAsStream("stencilset_bpmn.json"));
            return stencilNode;
        } catch (Exception e) {
            log.error("Error reading bpmn stencil set json", e);
            throw new FlowableApiException("Error reading bpmn stencil set json");
        }
    }

    public void deleteById(String modelId) {
        repositoryService.deleteModel(modelId);
    }

    public JsonNode getDisplayModelJSON(String modelId) {
        ObjectNode displayNode = objectMapper.createObjectNode();
        Model model = this.getById(modelId);
        bpmnDisplayJsonConverter.processProcessElements(model, displayNode, new GraphicInfo());
        return displayNode;
    }
}
