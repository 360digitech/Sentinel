package com.alibaba.csp.sentinel.dashboard.client;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
/**
 * @author cookie.joo
 * @date 2019-07-08
 */
public abstract class AbstractSentinelApiClient {

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

    /**
     * 获取id值
     *
     * @param app
     * @return
     */
    public long nextSystemRuleId(String app) {
        List<SystemRuleEntity> list = systemRepository.findAllByApp(app);
        return nextId(list);
    }

    public long nextParamFlowId(String app) {
        List<ParamFlowRuleEntity> list = paramFlowRepository.findAllByApp(app);
        return nextId(list);
    }

    public long nextFlowRuleId(String app) {
        List<FlowRuleEntity> list = flowRepository.findAllByApp(app);
        return nextId(list);
    }

    public long nextAuthorityRuleId(String app) {
        List<AuthorityRuleEntity> list = authorityRepository.findAllByApp(app);
        return nextId(list);
    }

    public long nextDegradeRuleId(String app) {
        List<DegradeRuleEntity> list = degradeRepository.findAllByApp(app);
        return nextId(list);
    }

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
}
