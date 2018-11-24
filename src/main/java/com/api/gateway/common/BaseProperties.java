package com.api.gateway.common;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * BaseProperties
 *
 * @author zhangfuhao
 * @Desc 读取配置文件
 * @date 2018年10月18日
 * @Version V1.0
 */
public class BaseProperties {
    private static final InternalLogger LOGGER                   = InternalLoggerFactory.getInstance(BaseProperties.class);
    private static final String         CURRENT_WORKING_DIECTORY = System.getProperty("user.dir");
    /**
     * 配置文件路径
     */
    private static final String         RESOURCE_PATH            = "/src/main/resources/";
    /**
     * 暂时只使用netty.properties这个配置文件
     */
    private static final String         PROPERTIES_FILE          = "netty.properties";

    private static volatile Properties PROPERTIES = new Properties();

    /* 静态创建 */
    static {
        try {
            PROPERTIES.load(new FileInputStream(CURRENT_WORKING_DIECTORY + RESOURCE_PATH + PROPERTIES_FILE));
        } catch (IOException e) {
            LOGGER.error("载入配置文件失败", e);
        }
    }

    /**
     * 配置文件app.name
     */
    public static final String APP_NAME                = PROPERTIES.getProperty("app.name");
    public static final String LOG_HANDLE_TYPE         = PROPERTIES.getProperty("log.handle.type");
    /**
     * 配置文件kafka配置
     */
    public static final String KAFKA_SERVERS           = PROPERTIES.getProperty("kafka.bootstrap.servers");
    public static final String KAFKA_ACKS              = PROPERTIES.getProperty("kafka.acks");
    public static final String KAFKA_RETRIES           = PROPERTIES.getProperty("kafka.retries");
    public static final String KAFKA_BATCH_SIZE        = PROPERTIES.getProperty("kafka.batch.size");
    public static final String KAFKA_LINGER_MS         = PROPERTIES.getProperty("kafka.linger.ms");
    public static final String KAFKA_BUFFER_MEMORY     = PROPERTIES.getProperty("kafka.buffer.memory");
    /**
     * 线程池配置
     */
    public static final int    THREAD_CORE_SIZE        = Integer.parseInt(PROPERTIES.getProperty("thread.core.size"));
    public static final int    THREAD_MAX_SIZE         = Integer.parseInt(PROPERTIES.getProperty("thread.max.size"));
    public static final int    THREAD_BLOCK_QUEUE_SIZE = Integer.parseInt(PROPERTIES.getProperty("thread.block.queue.size"));
}
