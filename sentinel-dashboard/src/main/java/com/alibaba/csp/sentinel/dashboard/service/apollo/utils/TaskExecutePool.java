package com.alibaba.csp.sentinel.dashboard.service.apollo.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 创建线程池
 * @author caodegao
 * @data 2019-07-09
 */
@Configuration
@EnableAsync
public class TaskExecutePool {
    /**
     * task.pool.corePoolSize=20
     * task.pool.maxPoolSize=40
     * task.pool.keepAliveSeconds=60
     * task.pool.queueCapacity=200
     */
    @Bean(name = "asyncServiceExecutor")
    public Executor myTaskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(5);
        //最大线程数
        executor.setMaxPoolSize(40);
        //队列容量
        executor.setQueueCapacity(200);
        //活跃时间
        executor.setKeepAliveSeconds(30);
        //线程名字前缀
        executor.setThreadNamePrefix("Rules Executor-");
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}