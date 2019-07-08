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
     * @param app
     * @param ip
     * @param port
     * @param rules
     * @return
     */
    void setParamFlowRuleOfMachine(String app, String ip, int port, List<ParamFlowRuleEntity> rules);

    /**
     * 设置FlowRuleEntity
     *
     * @param app
     * @param ip
     * @param port
     * @param rules
     * @return whether successfully set the rules.
     */
    boolean setFlowRuleOfMachine(String app, String ip, int port, List<FlowRuleEntity> rules);

    /**
     * 设置DegradeRuleEntity
     *
     * @param app
     * @param ip
     * @param port
     * @param rules
     * @return whether successfully set the rules.
     */
    boolean setDegradeRuleOfMachine(String app, String ip, int port, List<DegradeRuleEntity> rules);

    /**
     * 设置SystemRuleEntity
     *
     * @param app
     * @param ip
     * @param port
     * @param rules
     * @return whether successfully set the rules.
     */
    boolean setSystemRuleOfMachine(String app, String ip, int port, List<SystemRuleEntity> rules);

    /**
     * 设置AuthorityRuleEntity
     *
     * @param app
     * @param ip
     * @param port
     * @param rules
     * @return
     */
    boolean setAuthorityRuleOfMachine(String app, String ip, int port, List<AuthorityRuleEntity> rules);

    /**
     * 获取新的ID
     *
     * @param app
     * @return
     */
    long nextSystemRuleId(String app);

    /**
     * 获取新的ID
     *
     * @param app
     * @return
     */
    long nextParamFlowId(String app);

    /**
     * 获取新的ID
     *
     * @param app
     * @return
     */
    long nextFlowRuleId(String app);

    /**
     * 获取新的ID
     *
     * @param app
     * @return
     */
    long nextAuthorityRuleId(String app);

    /**
     * 获取新的ID
     *
     * @param app
     * @return
     */
    long nextDegradeRuleId(String app);
}
