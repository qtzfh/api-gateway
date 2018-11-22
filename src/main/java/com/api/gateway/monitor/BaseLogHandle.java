package com.api.gateway.monitor;

import com.api.gateway.common.BaseProperties;
import com.api.gateway.constants.BaseConstant;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BaseLogHandle
 *
 * @author zhangfuhao
 * @Desc 请求日志处理
 * @date 2018年10月25日
 * @Version V1.0
 */
public class BaseLogHandle {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(BaseLogHandle.class);

    private static final String        LOG_HANDLE_TOPIC      = "log_handle";
    private static final String        LOG_HANDLE_TYPE_KAFKA = "kafka";
    private static final AtomicInteger ATOMIC_INTEGER        = new AtomicInteger();
    private static final int           KAFKA_MAX_LOG_FLUSH   = 200;

    /**
     * 记录接口请求地址
     *
     * @param method 请求方式
     * @param uri    请求地址
     * @param result 接口返回结果
     */
    public void send(String method, String uri, String result) {
        send(method, uri, StringUtils.EMPTY, result);
    }

    /**
     * 记录接口请求地址
     *
     * @param method 请求方式
     * @param uri    请求地址
     * @param body   请求内容
     * @param result 接口返回结果
     */
    public void send(String method, String uri, String body, String result) {
        /* 处理后日志 */
        String afterResult = String.format("%s %s %s %s", method, uri, body, result);
        switch (BaseProperties.LOG_HANDLE_TYPE) {
            case LOG_HANDLE_TYPE_KAFKA:
                toKafka(afterResult);
                break;
            default:
                LOGGER.warn(afterResult);
                break;
        }
    }

    /**
     * 发送日志到kafka
     *
     * @param afterResult 整理后日志
     */
    private void toKafka(String afterResult) {
        var producer = BaseKafka.INSTANCE.getProducer();
        /* 使用异步发送kafka数据 */
        producer.send(new ProducerRecord<>(LOG_HANDLE_TOPIC, afterResult), (metadata, e) -> {
            if (Objects.nonNull(e)) {
                LOGGER.error("kafka生产者错误:{}:value:{}", metadata.offset(), afterResult, e);
            }
        });
        if (ATOMIC_INTEGER.getAndIncrement() >= KAFKA_MAX_LOG_FLUSH) {
            ATOMIC_INTEGER.set(0);
            producer.flush();
        }
    }
}
