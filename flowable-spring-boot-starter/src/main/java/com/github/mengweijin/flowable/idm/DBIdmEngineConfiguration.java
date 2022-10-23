package com.github.mengweijin.flowable.idm;

import org.flowable.idm.engine.IdmEngineConfiguration;

/**
 * Lightweight {@link IdmEngineConfiguration} to be used when running with custom database.
 * Refer to LdapIdmEngineConfiguration.java in Ldap module.
 * @author mengweijin
 * @date 2022/10/22
 */
public class DBIdmEngineConfiguration extends IdmEngineConfiguration {

    public DBIdmEngineConfiguration() {
        setUsingRelationalDatabase(false);
    }

    @Override
    public void initDataManagers() {
        // No need to initialize data managers when using ldap
    }
}
