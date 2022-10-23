package com.github.mengweijin.flowable.idm.impl;

import com.github.mengweijin.flowable.idm.DBConfiguration;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/22
 */
public class DBUserQueryImpl extends UserQueryImpl {
    protected DBConfiguration dbConfiguration;

    public DBUserQueryImpl(DBConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        return executeQuery().size();
    }

    @Override
    public List<User> executeList(CommandContext commandContext) {
        return executeQuery();
    }

    protected List<User> executeQuery() {
        List<User> result = new ArrayList<>();
        if (getId() != null) {
            UserEntity user = findById(getId());
            if (user != null) {
                result.add(user);
            }
        } else if (getIdIgnoreCase() != null) {
            UserEntity user = findById(getIdIgnoreCase());
            if (user != null) {
                result.add(user);
            }
        } else if (getFullNameLike() != null) {
            result = executeNameQuery(getFullNameLike());
        } else if (getFullNameLikeIgnoreCase() != null) {
            result = executeNameQuery(getFullNameLikeIgnoreCase());
        } else {
            result = executeAllUserQuery();
        }
        return result;
    }

    protected List<User> executeNameQuery(String searchExpression) {
        return executeUsersQuery(searchExpression.replaceAll("%", ""));
    }

    protected List<User> executeAllUserQuery() {
        return null;
    }

    protected UserEntity findById(final String userId) {
        return null;
    }

    protected List<User> executeUsersQuery(final String searchExpression) {
        return null;
    }
}
