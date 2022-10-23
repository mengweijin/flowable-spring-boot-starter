package com.github.mengweijin.flowable.idm.impl;

import com.github.mengweijin.flowable.idm.DBConfiguration;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.Group;
import org.flowable.idm.engine.impl.GroupQueryImpl;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/22
 */
public class DBGroupQueryImpl extends GroupQueryImpl {
    protected DBConfiguration dbConfiguration;

    public DBGroupQueryImpl(DBConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        return executeQuery().size();
    }

    @Override
    public List<Group> executeList(CommandContext commandContext) {
        return executeQuery();
    }

    protected List<Group> executeQuery() {
        if (getUserId() != null) {
            return findGroupsByUser(getUserId());
        } else if (getId() != null) {
            return findGroupsById(getId());
        } else {
            return findAllGroups();
        }
    }

    protected List<Group> findGroupsByUser(String userId) {
        List<Group> groups = executeGroupQuery(userId);
        return groups;
    }

    protected List<Group> findGroupsById(String groupId) {
        return executeGroupQuery(groupId);
    }

    protected List<Group> findAllGroups() {
        return executeGroupQuery(null);
    }

    protected List<Group> executeGroupQuery(final String searchExpression) {
        return null;
    }

}
