package com.alibaba.csp.sentinel.dashboard.service.apollo.jobs;

import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.service.apollo.PushRulesImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AppManagement appManagement;
    @Autowired
    private PushRulesImpl pushRules;

    private static final int SECOND_TIME = 20;

    @Scheduled(fixedDelay = 1000 * SECOND_TIME)
    public void push() {
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