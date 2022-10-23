package com.github.mengweijin.flowable.rest.copy.representation;

import lombok.Data;
import org.flowable.idm.api.Group;

/**
 * @author mengweijin
 * @date 2022/10/22
 */
@Data
public class GroupRepresentation {
    protected String id;
    protected String name;
    protected String type;

    public GroupRepresentation() {
    }

    public GroupRepresentation(Group group) {
        setId(group.getId());
        setName(group.getName());
        setType(group.getType());
    }
}
