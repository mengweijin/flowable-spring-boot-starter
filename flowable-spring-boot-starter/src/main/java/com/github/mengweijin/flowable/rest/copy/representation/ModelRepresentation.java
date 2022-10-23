/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mengweijin.flowable.rest.copy.representation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Representation of process-models, both current and historic models.
 *
 * @author mengweijin
 */
@Data
public class ModelRepresentation {

    protected String id;
    protected String name;
    protected String key;
    protected String description;
    protected String createdBy;
    protected String lastUpdatedBy;
    protected Date lastUpdated;
    protected boolean latestVersion;
    protected int version;
    protected String comment;
    protected Integer modelType;
    protected String tenantId;

    public static ModelRepresentation toModelRepresentation(org.flowable.engine.repository.Model model) {
        if(model == null) {
            return null;
        }
        ModelRepresentation representation = new ModelRepresentation();
        representation.setId(model.getId());
        representation.setName(model.getName());
        representation.setKey(model.getKey());
        representation.setDescription(null);
        representation.setCreatedBy(null);
        representation.setLastUpdatedBy(null);
        representation.setLastUpdated(model.getLastUpdateTime());
        representation.setLatestVersion(false);
        representation.setVersion(model.getVersion());
        representation.setComment(null);
        representation.setModelType(null);
        representation.setTenantId(model.getTenantId());

        return representation;
    }

    public static String createModelJson(ObjectMapper objectMapper, ModelRepresentation representation) {
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.set("stencilset", stencilSetNode);
        ObjectNode propertiesNode = objectMapper.createObjectNode();
        propertiesNode.put("process_id", representation.getKey());
        propertiesNode.put("name", representation.getName());
        if (StringUtils.isNotEmpty(representation.getDescription())) {
            propertiesNode.put("documentation", representation.getDescription());
        }
        editorNode.set("properties", propertiesNode);

        ArrayNode childShapeArray = objectMapper.createArrayNode();
        editorNode.set("childShapes", childShapeArray);
        ObjectNode childNode = objectMapper.createObjectNode();
        childShapeArray.add(childNode);
        ObjectNode boundsNode = objectMapper.createObjectNode();
        childNode.set("bounds", boundsNode);
        ObjectNode lowerRightNode = objectMapper.createObjectNode();
        boundsNode.set("lowerRight", lowerRightNode);
        lowerRightNode.put("x", 130);
        lowerRightNode.put("y", 193);
        ObjectNode upperLeftNode = objectMapper.createObjectNode();
        boundsNode.set("upperLeft", upperLeftNode);
        upperLeftNode.put("x", 100);
        upperLeftNode.put("y", 163);
        childNode.set("childShapes", objectMapper.createArrayNode());
        childNode.set("dockers", objectMapper.createArrayNode());
        childNode.set("outgoing", objectMapper.createArrayNode());
        childNode.put("resourceId", "startEvent1");
        ObjectNode stencilNode = objectMapper.createObjectNode();
        childNode.set("stencil", stencilNode);
        stencilNode.put("id", "StartNoneEvent");
        return editorNode.toString();
    }
}
