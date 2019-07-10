package com.alibaba.csp.sentinel.dashboard.client;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author cookie.joo
 * @date 2019-07-08
 */
public abstract class AbstractSentinelApiClient {

    private static Logger logger = LoggerFactory.getLogger(AbstractSentinelApiClient.class);

    public static final String FLOW_RULE_TYPE = "flow";
    public static final String DEGRADE_RULE_TYPE = "degrade";
    public static final String SYSTEM_RULE_TYPE = "system";
    public static final String AUTHORITY_TYPE = "authority";
    public static final String PARAM_FLOW_TYPE = "paramFlow";

    @Autowired
    private RuleRepository<AuthorityRuleEntity, Long> authorityRepository;
    @Autowired
    private RuleRepository<DegradeRuleEntity, Long> degradeRepository;
    @Autowired
    private RuleRepository<FlowRuleEntity, Long> flowRepository;
    @Autowired
    private RuleRepository<ParamFlowRuleEntity, Long> paramFlowRepository;
    @Autowired
    private RuleRepository<SystemRuleEntity, Long> systemRepository;


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

    /**
     * 把最大的id查询出来自增
     *
     * @param list
     * @return
     */
    private long nextId(List list) {
        long id = 0;
        if (list != null) {
            for (Object obj : list) {
                RuleEntity rule = (RuleEntity) obj;
                if (rule.getId() >= id) {
                    id = rule.getId();
                }
            }
        }
        return (id + 1);
    }

    public List<FlowRuleEntity> unionFlow(FlowRuleEntity entity, boolean isDelete) {
        try {
            List<FlowRuleEntity> apollo = flowRuleProvider.getRules(entity.getApp());
            if (isDelete) {
                apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
            } else {
                if (entity.getId() == null) {
                    entity.setId(nextId(apollo));
                    apollo.add(entity);
                } else {
                    apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
                    apollo.add(entity);
                }
            }
            return apollo;
        } catch (Exception e) {
            logger.error("flowRuleProvider.getRules error:", e);
            return Lists.newArrayList();
        }
    }

    public List<DegradeRuleEntity> unionDegrade(DegradeRuleEntity entity, boolean isDelete) {
        try {
            List<DegradeRuleEntity> apollo = degradeRuleProvider.getRules(entity.getApp());
            if (isDelete) {
                apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
            } else {
                if (entity.getId() == null) {
                    entity.setId(nextId(apollo));
                    apollo.add(entity);
                } else {
                    apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
                    apollo.add(entity);
                }
            }
            return apollo;
        } catch (Exception e) {
            logger.error("degradeRuleProvider.getRules error:", e);
            return Lists.newArrayList();
        }
    }

    public List<ParamFlowRuleEntity> unionParamFlow(ParamFlowRuleEntity entity, boolean isDelete) {
        try {
            List<ParamFlowRuleEntity> apollo = paramFlowRuleProvider.getRules(entity.getApp());
            if (isDelete) {
                apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
            } else {
                if (entity.getId() == null) {
                    entity.setId(nextId(apollo));
                    apollo.add(entity);
                } else {
                    apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
                    apollo.add(entity);
                }
            }
            return apollo;
        } catch (Exception e) {
            logger.error("paramFlowRuleProvider.getRules error:", e);
            return Lists.newArrayList();
        }
    }

    public List<SystemRuleEntity> unionSystem(SystemRuleEntity entity, boolean isDelete) {
        try {
            List<SystemRuleEntity> apollo = systemRuleProvider.getRules(entity.getApp());
            if (isDelete) {
                apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
            } else {
                if (entity.getId() == null) {
                    entity.setId(nextId(apollo));
                    apollo.add(entity);
                } else {
                    apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
                    apollo.add(entity);
                }
            }
            return apollo;
        } catch (Exception e) {
            logger.error("systemRuleProvider.getRules error:", e);
            return Lists.newArrayList();
        }
    }

    public List<AuthorityRuleEntity> unionAuthority(AuthorityRuleEntity entity, boolean isDelete) {
        try {
            List<AuthorityRuleEntity> apollo = authorityRuleProvider.getRules(entity.getApp());
            if (isDelete) {
                apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
            } else {
                if (entity.getId() == null) {
                    entity.setId(nextId(apollo));
                    apollo.add(entity);
                } else {
                    apollo = Lists.newArrayList(Collections2.filter(apollo, entity1 -> !entity1.getId().equals(entity.getId())));
                    apollo.add(entity);
                }
            }
            return apollo;
        } catch (Exception e) {
            logger.error("authorityRuleProvider.getRules error:", e);
            return Lists.newArrayList();
        }
    }
}
