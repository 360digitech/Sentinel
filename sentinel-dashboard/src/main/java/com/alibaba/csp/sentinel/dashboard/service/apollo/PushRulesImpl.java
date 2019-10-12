package com.alibaba.csp.sentinel.dashboard.service.apollo;

import com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.service.apollo.jobs.PushRulesJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author caodegao
 * @data 2019-07-09
 */
@Component
public class PushRulesImpl {
    private static Logger logger = LoggerFactory.getLogger(PushRulesImpl.class);
    @Autowired
    private SentinelApiClient sentinelApiClient;
    @Autowired
    @Qualifier("flowRuleApolloProvider")
    private DynamicRuleProvider<List<FlowRuleEntity>> flowRuleProvider;
    @Autowired
    @Qualifier("degradeRuleApolloProvider")
    private DynamicRuleProvider<List<DegradeRuleEntity>> degradeRuleProvider;
    @Autowired
    @Qualifier("systemRuleApolloProvider")
    private DynamicRuleProvider<List<SystemRuleEntity>> systemRuleProvider;
    @Autowired
    @Qualifier("paramFlowRuleApolloProvider")
    private DynamicRuleProvider<List<ParamFlowRuleEntity>> paramFlowRuleProvider;
    @Autowired
    @Qualifier("authorityRuleApolloProvider")
    private DynamicRuleProvider<List<AuthorityRuleEntity>> authorityRuleProvider;

    @Async("asyncServiceExecutor")
    public void pushAllMachines(AppInfo app) {
        Set<MachineInfo> machines = app.getMachines();
        logger.info("pushAllMachines ThreadName:{}", Thread.currentThread().getName());
        if (!CollectionUtils.isEmpty(machines)) {
            try {
                List<FlowRuleEntity> flowRules = flowRuleProvider.getRules(app.getApp());
                List<DegradeRuleEntity> degradeRules = degradeRuleProvider.getRules(app.getApp());
                List<SystemRuleEntity> systemRules = systemRuleProvider.getRules(app.getApp());
                List<ParamFlowRuleEntity> paramFlowRules = paramFlowRuleProvider.getRules(app.getApp());
                List<AuthorityRuleEntity> authorityRules = authorityRuleProvider.getRules(app.getApp());
                for (MachineInfo machine : machines) {
                    if (machine.isHealthy()) {
                        sentinelApiClient.setFlowRuleOfMachine(machine.getApp(), machine.getIp(), machine.getPort(), flowRules);
                        sentinelApiClient.setDegradeRuleOfMachine(machine.getApp(), machine.getIp(), machine.getPort(), degradeRules);
                        sentinelApiClient.setSystemRuleOfMachine(machine.getApp(), machine.getIp(), machine.getPort(), systemRules);
                        sentinelApiClient.setParamFlowRuleOfMachine(machine.getApp(), machine.getIp(), machine.getPort(), paramFlowRules).get();
                        sentinelApiClient.setAuthorityRuleOfMachine(machine.getApp(), machine.getIp(), machine.getPort(), authorityRules);
                    }
                }
            } catch (Exception e) {
                logger.error("pushAllMachines error:", e);
            }
        }
    }
}