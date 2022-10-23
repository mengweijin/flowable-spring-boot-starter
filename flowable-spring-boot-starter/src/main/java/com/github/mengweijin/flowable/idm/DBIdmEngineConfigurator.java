package com.github.mengweijin.flowable.idm;

import com.github.mengweijin.flowable.idm.impl.DBIdentityServiceImpl;
import lombok.Data;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.engine.impl.interceptor.EngineConfigurationConstants;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.configurator.IdmEngineConfigurator;

/**
 * @author mengweijin
 * @date 2022/10/22
 */
@Data
public class DBIdmEngineConfigurator extends IdmEngineConfigurator {

    private DBConfiguration dbConfiguration;

    @Override
    public void beforeInit(AbstractEngineConfiguration engineConfiguration) {
        // Nothing to do
    }

    @Override
    public void configure(AbstractEngineConfiguration engineConfiguration) {

        this.idmEngineConfiguration = new DBIdmEngineConfiguration();

        if (dbConfiguration == null) {
            throw new FlowableException("DBConfiguration is not set");
        }

        super.configure(engineConfiguration);

        getIdmEngineConfiguration(engineConfiguration)
                .setIdmIdentityService(new DBIdentityServiceImpl(dbConfiguration, idmEngineConfiguration));
    }

    protected static IdmEngineConfiguration getIdmEngineConfiguration(AbstractEngineConfiguration engineConfiguration) {
        return (IdmEngineConfiguration) engineConfiguration.getEngineConfigurations().get(EngineConfigurationConstants.KEY_IDM_ENGINE_CONFIG);
    }
}
