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
package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import com.alibaba.csp.sentinel.log.RecordLog;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author caodegao
 * @data 2019-06-27
 */
public final class ApolloConfigUtil {

    public static final String PARAM_FLOW_DATA_ID_POSTFIX = "-param-flow-rules";
    public static final String SYSTEM_DATA_ID_POSTFIX = "-system-rules";
    public static final String DEGRADE_DATA_ID_POSTFIX = "-degrade-rules";
    public static final String AUTHORITY_DATA_ID_POSTFIX = "-authority-rules";
    public static final String FLOW_DATA_ID_POSTFIX = "-flow-rules";

    public static final String APP_ID = "sentinel";


    public static final String ENV = "env";
    public static final String CLUSTER_NAME = "clusterName";
    public static final String NAMESPACE_NAME = "namespaceName";

    private ApolloConfigUtil() {
    }

    public static String getFlowDataId(String appName) {
        return String.format("%s%s", appName, FLOW_DATA_ID_POSTFIX);
    }

    public static String getAuthorityDataId(String appName) {
        return String.format("%s%s", appName, AUTHORITY_DATA_ID_POSTFIX);
    }

    public static String getDegradeDataId(String appName) {
        return String.format("%s%s", appName, DEGRADE_DATA_ID_POSTFIX);
    }

    public static String getSystemDataId(String appName) {
        return String.format("%s%s", appName, SYSTEM_DATA_ID_POSTFIX);
    }

    public static String getParamFlowDataId(String appName) {
        return String.format("%s%s", appName, PARAM_FLOW_DATA_ID_POSTFIX);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    private static Properties properties = loadProperties();

    private static final String PROPERTIES_PATH = "/home/q/sentinel/sentinel-app/config/apollo.properties";

    private static Properties loadProperties() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(PROPERTIES_PATH);
            properties.load(inputStream);
        } catch (IOException e) {
            RecordLog.warn("[ApolloConfigUtil] WARN: loadPropertis failed", e);
        }
        return properties;
    }
}
