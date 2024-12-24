package edu.whut.cs.se.udpagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Value("${fiberOptic.threadPool.core_size}")
    private int fiber_optic_core_size;
    @Value("${fiberOptic.threadPool.max_size}")
    private int fiber_optic_max_size;
    @Value("${fiberOptic.threadPool.queue_capacity}")
    private int fiber_optic_queue_capacity;

    /**
     * 光纤解调仪线程池配置
     */
    @Bean(name = "FiberOpticExecutor")
    public ExecutorService asyncTaskShopConsumerExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        threadPoolTaskExecutor.setCorePoolSize(fiber_optic_core_size);
        // 最大空闲时间5分钟
        threadPoolTaskExecutor.setKeepAliveSeconds(60 * 5);
        // 最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(fiber_optic_max_size);
        // 配置队列大小
        threadPoolTaskExecutor.setQueueCapacity(fiber_optic_queue_capacity);
        // 配置线程池前缀
        threadPoolTaskExecutor.setThreadNamePrefix("fiber-optic");
        // 拒绝策略:抛出 RejectedExecutionException 异常
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        threadPoolTaskExecutor.initialize();
        // 启动则分配所有的核心线程
        threadPoolTaskExecutor.getThreadPoolExecutor().prestartAllCoreThreads();
        return threadPoolTaskExecutor.getThreadPoolExecutor();
    }
}