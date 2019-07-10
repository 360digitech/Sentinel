package com.alibaba.csp.sentinel.dashboard.service.apollo.jobs;

import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.service.apollo.PushRulesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @author caodegao
 * @data 2019-07-09
 */
@Component
public class PushRulesJob {
    private static Logger logger = LoggerFactory.getLogger(PushRulesJob.class);
    @Autowired
    private AppManagement appManagement;
    @Autowired
    private PushRulesImpl pushRules;

    /**
     * 休眠时间(秒)
     */
    private static final int SECOND_TIME = 180;

    /**
     * 开关
     */
    @Value("${push.rules.switch}")
    private boolean flag;

    @Scheduled(fixedDelay = 1000 * SECOND_TIME)
    public void push() {
        logger.info("push.rules.switch : {}", flag);
        if (flag) {
            Set<AppInfo> apps = appManagement.getBriefApps();
            if (!CollectionUtils.isEmpty(apps)) {
                for (AppInfo app : apps) {
                    if (!app.isDead()) {
                        pushRules.pushAllMachines(app);
                    }
                }
            }
        }
    }

}