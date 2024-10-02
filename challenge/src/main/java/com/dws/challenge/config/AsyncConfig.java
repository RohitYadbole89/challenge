package com.dws.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * AsyncConfig class creating threads for ASync task to work without blocking main controller thread
 * @author Rohit Yadbole
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * creating a pool of 4 threads and related thread configuration
     * @return
     */
    @Bean(name ="taskExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("BalanceTransferThread-");
        executor.initialize();
        return executor;
    }
}
