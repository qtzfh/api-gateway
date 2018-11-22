package com.api.gateway.common;

import java.util.concurrent.*;

/**
 * BaseThreadFactory
 *
 * @author zhangfuhao
 * @Desc 创建默认线程池
 * @date 2018年10月30日
 * @Version V1.0
 */
public class BaseThreadFactory {

    private static final ExecutorService NEW_FIXED_THREAD_POOL;

    static {
        NEW_FIXED_THREAD_POOL = new ThreadPoolExecutor(BaseProperties.THREAD_CORE_SIZE, BaseProperties.THREAD_MAX_SIZE,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(BaseProperties.THREAD_BLOCK_QUEUE_SIZE), new ThreadPoolExecutor.AbortPolicy());
    }

    public static void execute(Runnable task) {
        try {
            NEW_FIXED_THREAD_POOL.execute(task);
        } catch (RejectedExecutionException e) {
            throw new RejectedExecutionException("定时任务运行失败" + task, e);
        }
    }
}
