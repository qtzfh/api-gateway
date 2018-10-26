package com.zfh.netty.common;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

/**
 * BaseKafka
 *
 * @author zhangfuhao
 * @Desc kafka基础配置
 * @date 2018年10月26日
 * @Version V1.0
 */
public enum BaseKafka {
    /**
     * 单例模式
     */
    INSTANCE;
    /**
     * kafka生产者序列化方法类
     */
    private static final String KAFKA_PRODUCER_KEY_SERIALIZER   = "org.apache.kafka.common.serialization.StringSerializer";
    private static final String KAFKA_PRODUCER_VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";

    private Producer<String, String> producer = null;

    BaseKafka() {
        producer = setProducer();
    }

    public Producer<String, String> getProducer() {
        return producer;
    }

    public Producer<String, String> setProducer() {
        var props = new Properties();
        props.put("bootstrap.servers", BaseProperties.KAFKA_SERVERS);
        props.put("acks", BaseProperties.KAFKA_ACKS);
        props.put("retries", BaseProperties.KAFKA_RETRIES);
        props.put("batch.size", BaseProperties.KAFKA_BATCH_SIZE);
        props.put("linger.ms", BaseProperties.KAFKA_LINGER_MS);
        props.put("buffer.memory", BaseProperties.KAFKA_BUFFER_MEMORY);
        props.put("key.serializer", KAFKA_PRODUCER_KEY_SERIALIZER);
        props.put("value.serializer", KAFKA_PRODUCER_VALUE_SERIALIZER);
        return new KafkaProducer<>(props);
    }
}
