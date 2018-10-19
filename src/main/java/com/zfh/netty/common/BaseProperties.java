package com.zfh.netty.common;

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
    public static final String APP_NAME = PROPERTIES.getProperty("app.name");
    /**
     * 配置文件zfh.springboot.uri
     */
    public static final String ZFH_SPRINGBOOT = PROPERTIES.getProperty("zfh.springboot");
    public static final String ZFH_SPRINGBOOT_URI= PROPERTIES.getProperty("zfh.springboot.uri");
}
