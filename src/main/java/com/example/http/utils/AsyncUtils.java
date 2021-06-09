package com.example.http.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class AsyncUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncUtils.class);

    private static volatile ThreadPoolTaskExecutor executor = null;
    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAX_SIZE = 3 * CORE_SIZE;
    private static final int LARGEST_SIZE = 100;
    private static final int QUEUE_SIZE = 10000;
    private static final int KEEP_ALIVE_TIME = 10*60;
    private static final int AWAIT_TERMINATION_TIME  = 60;

    static {
        try {
            executor = new ThreadPoolTaskExecutor();
            int maxSize = getMaxThreadSize();
            executor.setMaxPoolSize(maxSize);
            executor.setCorePoolSize(CORE_SIZE);
            executor.setQueueCapacity(QUEUE_SIZE);
            executor.setAllowCoreThreadTimeOut(true);
            executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
            executor.setWaitForTasksToCompleteOnShutdown(true);
            executor.setAwaitTerminationSeconds(AWAIT_TERMINATION_TIME);
            executor.setThreadNamePrefix("TreadExecutor-request-");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            executor.initialize();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> shutDown()));
        } catch (Exception e) {
            LOGGER.error("初始化线程池异常,e={}", e.getMessage(), e);
        }
    }

    public static void execute(Runnable task) {
        executor.execute(task);
    }

    public static <V> Future<V> submit(Callable<V> task) {
        return executor.submit(task);
    }

    public static <V> Future<V> submitListenable(Callable<V> task) {
        return executor.submitListenable(task);
    }

    public static void shutDown() {
        LOGGER.info("关闭线程池...");
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
    }

    private static int getMaxThreadSize() {
        int maxSize = MAX_SIZE;
        if (maxSize > LARGEST_SIZE) {
            maxSize = LARGEST_SIZE;
        }
        return maxSize;
    }
}
