package com.github.mengweijin.flowable.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.flowable.rest.representation.UserRepresentation;
import com.github.mengweijin.flowable.rest.security.DefaultPrivileges;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.rest.service.api.repository.ModelCollectionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/22
 */
@Slf4j
@RestController
@RequestMapping("/modeler-app")
public class ModelerAppController {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ModelCollectionResource modelCollectionResource;

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








    /**
     * GET /rest/models/{modelId}/thumbnail -> Get process model thumbnail
     */
    @GetMapping(value = "/rest/models/{modelId}/thumbnail", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getModelThumbnail(@PathVariable String modelId) {
        String sql = "SELECT thumbnail FROM ACT_DE_MODEL WHERE ID='" + modelId + "'";

        final LobHandler lobHandler = new DefaultLobHandler();
        byte[] thumbnail = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            return lobHandler.getBlobAsBytes(rs, 1);
        });
        return thumbnail;
    }
}
