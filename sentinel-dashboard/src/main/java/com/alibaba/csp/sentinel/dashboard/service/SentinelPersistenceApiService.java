package com.alibaba.csp.sentinel.dashboard.service;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;

import java.util.List;

/**
 * @author cookie.joo
 * @date 2019-07-08
 */
public interface SentinelPersistenceApiService {

    /**
     * 查询FlowRuleEntity
     *
     * @param app
     * @param ip
     * @param port
     * @return
     * @throws Exception
     */
    List<FlowRuleEntity> fetchFlowRuleOfMachine(String app, String ip, int port) throws Exception;

    /**
     * 查询DegradeRuleEntity
     *
     * @param app
     * @param ip
     * @param port
     * @return
     * @throws Exception
     */
    List<DegradeRuleEntity> fetchDegradeRuleOfMachine(String app, String ip, int port) throws Exception;

    /**
     * 查询SystemRuleEntity
     *
     * @param app
     * @param ip
     * @param port
     * @return
     * @throws Exception
     */
    List<SystemRuleEntity> fetchSystemRuleOfMachine(String app, String ip, int port) throws Exception;

    /**
     * 查询ParamFlowRuleEntity
     *
     * @param app
     * @param ip
     * @param port
     * @return
     * @throws Exception
     */
    List<ParamFlowRuleEntity> fetchParamFlowRulesOfMachine(String app, String ip, int port) throws Exception;

    /**
     * 查询AuthorityRuleEntity
     *
     * @param app  application name
     * @param ip   machine client IP
     * @param port machine client port
     * @return all retrieved authority rules
     * @throws Exception
     */
    List<AuthorityRuleEntity> fetchAuthorityRulesOfMachine(String app, String ip, int port) throws Exception;

    /**
     * 设置ParamFlowRuleEntity
     *
     * @param entity
     * @param isDelete
     * @return
     */
    void setParamFlowRuleOfMachine(ParamFlowRuleEntity entity, boolean isDelete);

    /**
     * 设置FlowRuleEntity
     *
     * @param entity
     * @param isDelete
     * @return whether successfully set the rules.
     */
    boolean setFlowRuleOfMachine(FlowRuleEntity entity, boolean isDelete);

    /**
     * 设置DegradeRuleEntity
     *
     * @param entity
     * @param isDelete
     * @return whether successfully set the rules.
     */
    boolean setDegradeRuleOfMachine(DegradeRuleEntity entity, boolean isDelete);

    /**
     * 设置SystemRuleEntity
     *
     * @param entity
     * @param isDelete
     * @return whether successfully set the rules.
     */
    boolean setSystemRuleOfMachine(SystemRuleEntity entity, boolean isDelete);

    /**
     * 设置AuthorityRuleEntity
     *
     * @param entity
     * @param isDelete
     * @return
     */
    boolean setAuthorityRuleOfMachine(AuthorityRuleEntity entity, boolean isDelete);
}
