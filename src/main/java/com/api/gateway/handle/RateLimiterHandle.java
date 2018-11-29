package com.api.gateway.handle;

import com.api.gateway.base.BaseInitFactory;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * RateLimitHandle
 *
 * @author zhangfuhao
 * @Desc 限流处理
 * @date 2018年11月24日
 * @Version V1.0
 */
public class RateLimiterHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterHandle.class);

    /**
     * 获取限流令牌
     *
     * @param systemName 系统名称
     * @return 获取请求是否被通过
     */
    public static boolean getAcquire(String systemName) {
        if (StringUtils.isBlank(systemName)) {
            return false;
        }
        RateLimiter rateLimiter = BaseInitFactory.RATE_LIMITER_HASH_MAP.get(systemName);
        if (Objects.isNull(rateLimiter)) {
            return false;
        }
        return rateLimiter.tryAcquire();
    }
}
