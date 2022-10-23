package com.github.mengweijin.flowable.rest.copy.representation;

import lombok.Data;
import org.flowable.idm.api.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/22
 */
@Data
public class UserRepresentation {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String fullName;
    protected String tenantId;
    protected List<GroupRepresentation> groups = new ArrayList<>();
    protected List<String> privileges = new ArrayList<>();

    public UserRepresentation() {
    }

    public UserRepresentation(String userId) {
        setId(userId);
    }

    public UserRepresentation(User user) {
        setId(user.getId());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setFullName((user.getFirstName() != null ? user.getFirstName() : "") + " " + (user.getLastName() != null ? user.getLastName() : ""));
        setTenantId(user.getTenantId());
        setEmail(user.getEmail());
    }
}
