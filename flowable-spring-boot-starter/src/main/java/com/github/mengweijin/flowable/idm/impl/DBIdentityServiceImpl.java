package com.github.mengweijin.flowable.idm.impl;

import com.github.mengweijin.flowable.idm.DBConfiguration;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.GroupQuery;
import org.flowable.idm.api.NativeGroupQuery;
import org.flowable.idm.api.NativeUserQuery;
import org.flowable.idm.api.PrivilegeMapping;
import org.flowable.idm.api.User;
import org.flowable.idm.api.UserQuery;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.IdmIdentityServiceImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/22
 */
public class DBIdentityServiceImpl extends IdmIdentityServiceImpl  {

    protected DBConfiguration dbConfiguration;

    public DBIdentityServiceImpl(DBConfiguration dbConfiguration, IdmEngineConfiguration idmEngineConfiguration) {
        super(idmEngineConfiguration);
        this.dbConfiguration = dbConfiguration;
    }

    @Override
    public UserQuery createUserQuery() {
        return new DBUserQueryImpl(dbConfiguration);
    }

    @Override
    public GroupQuery createGroupQuery() {
        return new DBGroupQueryImpl(dbConfiguration);
    }

    /**
     * 不检查
     * @return
     */
    @Override
    public boolean checkPassword(String userId, String password) {
        return true;
    }

    @Override
    public List<Group> getGroupsWithPrivilege(String name) {
        List<Group> groups = new ArrayList<>();
        List<PrivilegeMapping> privilegeMappings = getPrivilegeMappingsByPrivilegeId(name);
        for (PrivilegeMapping privilegeMapping : privilegeMappings) {
            if (privilegeMapping.getGroupId() != null) {
                Group group = new GroupEntityImpl();
                group.setId(privilegeMapping.getGroupId());
                group.setName(privilegeMapping.getGroupId());
                groups.add(group);
            }
        }

        return groups;
    }

    @Override
    public List<User> getUsersWithPrivilege(String name) {
        List<User> users = new ArrayList<>();
        List<PrivilegeMapping> privilegeMappings = getPrivilegeMappingsByPrivilegeId(name);
        for (PrivilegeMapping privilegeMapping : privilegeMappings) {
            if (privilegeMapping.getUserId() != null) {
                User user = new UserEntityImpl();
                user.setId(privilegeMapping.getUserId());
                user.setLastName(privilegeMapping.getUserId());
                users.add(user);
            }
        }

        return users;
    }

    @Override
    public User newUser(String userId) {
        throw new FlowableException("LDAP identity service doesn't support creating a new user");
    }

    @Override
    public void saveUser(User user) {
        throw new FlowableException("LDAP identity service doesn't support saving an user");
    }

    @Override
    public NativeUserQuery createNativeUserQuery() {
        throw new FlowableException("LDAP identity service doesn't support native querying");
    }

    @Override
    public void deleteUser(String userId) {
        throw new FlowableException("LDAP identity service doesn't support deleting an user");
    }

    @Override
    public Group newGroup(String groupId) {
        throw new FlowableException("LDAP identity service doesn't support creating a new group");
    }

    @Override
    public NativeGroupQuery createNativeGroupQuery() {
        throw new FlowableException("LDAP identity service doesn't support native querying");
    }

    @Override
    public void saveGroup(Group group) {
        throw new FlowableException("LDAP identity service doesn't support saving a group");
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new FlowableException("LDAP identity service doesn't support deleting a group");
    }
}
