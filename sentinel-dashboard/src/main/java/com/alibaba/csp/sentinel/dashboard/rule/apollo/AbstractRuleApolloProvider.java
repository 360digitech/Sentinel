package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenNamespaceDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象类,获取和保存方法
 *
 * @author caodegao
 * @data 2019-06-27
 */
public abstract class AbstractRuleApolloProvider {

    @Autowired
    private ApolloOpenApiClient apolloOpenApiClient;

    /**
     * 获取配置
     *
     * @param app
     * @return
     */
    public String getRulesByApollo(String app, String ruleType) {
        String env = ApolloConfigUtil.getProperty(ApolloConfigUtil.ENV, "DEV");
        String clusterName = ApolloConfigUtil.getProperty(ApolloConfigUtil.CLUSTER_NAME, "default");
        String namespaceName = ApolloConfigUtil.getProperty(ApolloConfigUtil.NAMESPACE_NAME, "MSF.sentinel.rules");
        String flowDataId = getRuleType(app, ruleType);
        OpenNamespaceDTO openNamespaceDTO = apolloOpenApiClient.getNamespace(ApolloConfigUtil.APP_ID, env, clusterName, namespaceName);
        return openNamespaceDTO
                .getItems()
                .stream()
                .filter(p -> p.getKey().equals(flowDataId))
                .map(OpenItemDTO::getValue)
                .findFirst()
                .orElse("");
    }

    /**
     * 新增修改配置
     *
     * @param app
     * @param rules
     */
    public void publish2Apollo(String app, String ruleType, String rules) {
        String env = ApolloConfigUtil.getProperty(ApolloConfigUtil.ENV, "DEV");
        String clusterName = ApolloConfigUtil.getProperty(ApolloConfigUtil.CLUSTER_NAME, "default");
        String namespaceName = ApolloConfigUtil.getProperty(ApolloConfigUtil.NAMESPACE_NAME, "MSF.sentinel.rules");
        // Increase the configuration
        String flowDataId = getRuleType(app, ruleType);
        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey(flowDataId);
        openItemDTO.setValue(rules);
        openItemDTO.setComment("Program auto-join");
        openItemDTO.setDataChangeCreatedBy("apollo");
        apolloOpenApiClient.createOrUpdateItem(ApolloConfigUtil.APP_ID, env, clusterName, namespaceName, openItemDTO);

        // Release configuration
        NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();
        namespaceReleaseDTO.setEmergencyPublish(true);
        namespaceReleaseDTO.setReleaseComment("Modify or add configurations");
        namespaceReleaseDTO.setReleasedBy("apollo");
        namespaceReleaseDTO.setReleaseTitle("Modify or add configurations");
        apolloOpenApiClient.publishNamespace(ApolloConfigUtil.APP_ID, env, clusterName, namespaceName, namespaceReleaseDTO);
    }

    public static final String FLOW = "Flow";
    public static final String DEGRADE = "Degrade";
    public static final String AUTHORITY = "Authority";
    public static final String SYSTEM = "System";
    public static final String PARAM_FLOW = "ParamFlow";

    private String getRuleType(String app, String ruleType) {
        String result = null;
        switch (ruleType) {
            case FLOW: {
                result = ApolloConfigUtil.getFlowDataId(app);
                break;
            }
            case DEGRADE: {
                result = ApolloConfigUtil.getDegradeDataId(app);
                break;
            }
            case AUTHORITY: {
                result = ApolloConfigUtil.getAuthorityDataId(app);
                break;
            }
            case SYSTEM: {
                result = ApolloConfigUtil.getSystemDataId(app);
                break;
            }
            case PARAM_FLOW: {
                result = ApolloConfigUtil.getParamFlowDataId(app);
                break;
            }
            default: {
                break;
            }
        }
        return result;
    }
}
