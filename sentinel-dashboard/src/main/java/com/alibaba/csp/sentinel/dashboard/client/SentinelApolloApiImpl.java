/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.client;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.service.SentinelPersistenceApiService;
import com.alibaba.csp.sentinel.util.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author cookie.joo
 * @date 2019-07-05
 */
@Service("sentinelApolloApiImpl")
public class SentinelApolloApiImpl extends AbstractSentinelApiClient implements SentinelPersistenceApiService {
    private static Logger logger = LoggerFactory.getLogger(SentinelApolloApiImpl.class);

    public SentinelApolloApiImpl() {
    }

    @Override
    public List<FlowRuleEntity> fetchFlowRuleOfMachine(String app, String ip, int port) throws Exception {
        return flowRuleProvider.getRules(app);
    }

    @Override
    public List<DegradeRuleEntity> fetchDegradeRuleOfMachine(String app, String ip, int port) throws Exception {
        return degradeRuleProvider.getRules(app);
    }

    @Override
    public List<SystemRuleEntity> fetchSystemRuleOfMachine(String app, String ip, int port) throws Exception {
        return systemRuleProvider.getRules(app);
    }

    @Override
    public List<ParamFlowRuleEntity> fetchParamFlowRulesOfMachine(String app, String ip, int port) throws Exception {
        AssertUtil.notEmpty(app, "Bad app name");
        AssertUtil.notEmpty(ip, "Bad machine IP");
        AssertUtil.isTrue(port > 0, "Bad machine port");
        return paramFlowRuleProvider.getRules(app);
    }

    @Override
    public List<AuthorityRuleEntity> fetchAuthorityRulesOfMachine(String app, String ip, int port) throws Exception {
        AssertUtil.notEmpty(app, "Bad app name");
        AssertUtil.notEmpty(ip, "Bad machine IP");
        AssertUtil.isTrue(port > 0, "Bad machine port");
        return authorityRuleProvider.getRules(app);
    }

    @Override
    public void setParamFlowRuleOfMachine(String app, String ip, int port, List<ParamFlowRuleEntity> rules) {
        AssertUtil.notEmpty(app, "Bad app name");
        AssertUtil.notEmpty(ip, "Bad machine IP");
        AssertUtil.isTrue(port > 0, "Bad machine port");
        setRules(app, PARAM_FLOW_TYPE, rules);
    }

    @Override
    public boolean setFlowRuleOfMachine(String app, String ip, int port, List<FlowRuleEntity> rules) {
        setRules(app, FLOW_RULE_TYPE, rules);
        return true;
    }

    @Override
    public boolean setDegradeRuleOfMachine(String app, String ip, int port, List<DegradeRuleEntity> rules) {
        setRules(app, DEGRADE_RULE_TYPE, rules);
        return true;
    }

    @Override
    public boolean setSystemRuleOfMachine(String app, String ip, int port, List<SystemRuleEntity> rules) {
        setRules(app, SYSTEM_RULE_TYPE, rules);
        return true;
    }

    @Override
    public boolean setAuthorityRuleOfMachine(String app, String ip, int port, List<AuthorityRuleEntity> rules) {
        setRules(app, AUTHORITY_TYPE, rules);
        return true;
    }

    @Autowired
    @Qualifier("flowRuleApolloProvider")
    private DynamicRuleProvider<List<FlowRuleEntity>> flowRuleProvider;
    @Autowired
    @Qualifier("flowRuleApolloPublisher")
    private DynamicRulePublisher<List<FlowRuleEntity>> flowRulePublisher;
    @Autowired
    @Qualifier("degradeRuleApolloProvider")
    private DynamicRuleProvider<List<DegradeRuleEntity>> degradeRuleProvider;
    @Autowired
    @Qualifier("degradeRuleApolloPublisher")
    private DynamicRulePublisher<List<DegradeRuleEntity>> degradeRulePublisher;
    @Autowired
    @Qualifier("systemRuleApolloProvider")
    private DynamicRuleProvider<List<SystemRuleEntity>> systemRuleProvider;
    @Autowired
    @Qualifier("systemRuleApolloPublisher")
    private DynamicRulePublisher<List<SystemRuleEntity>> systemRulePublisher;
    @Autowired
    @Qualifier("paramFlowRuleApolloProvider")
    private DynamicRuleProvider<List<ParamFlowRuleEntity>> paramFlowRuleProvider;
    @Autowired
    @Qualifier("paramFlowRuleApolloPublisher")
    private DynamicRulePublisher<List<ParamFlowRuleEntity>> paramFlowRulePublisher;
    @Autowired
    @Qualifier("authorityRuleApolloProvider")
    private DynamicRuleProvider<List<AuthorityRuleEntity>> authorityRuleProvider;
    @Autowired
    @Qualifier("authorityRuleApolloPublisher")
    private DynamicRulePublisher<List<AuthorityRuleEntity>> authorityRulePublisher;

    private void setRules(String app, String type, List rules) {
        try {
            switch (type) {
                case FLOW_RULE_TYPE: {
                    flowRulePublisher.publish(app, rules);
                    break;
                }
                case DEGRADE_RULE_TYPE: {
                    degradeRulePublisher.publish(app, rules);
                    break;
                }
                case SYSTEM_RULE_TYPE: {
                    systemRulePublisher.publish(app, rules);
                    break;
                }
                case PARAM_FLOW_TYPE: {
                    paramFlowRulePublisher.publish(app, rules);
                    break;
                }
                case AUTHORITY_TYPE: {
                    authorityRulePublisher.publish(app, rules);
                    break;
                }
                default: {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("setFlowRuleOfMachine type:{},error:{}", type, e);
        }
    }

}
