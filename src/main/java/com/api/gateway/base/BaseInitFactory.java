package com.api.gateway.base;

import com.api.gateway.manager.dao.verify.ApiVerifySystemMapper;
import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;
import com.google.common.util.concurrent.RateLimiter;

import java.util.HashMap;
import java.util.List;

/**
 * BaseInitFactory
 *
 * @author zhangfuhao
 * @Desc 初始化工厂
 * @date 2018年11月27日
 * @Version V1.0
 */
public class BaseInitFactory {
    /**
     * 初始化验证表
     */
    public static final HashMap<String, ApiVerifySystemDO> API_VERIFY_SYSTEM_DO_HASH_MAP = new HashMap<>();
    /**
     * 初始化限流
     */
    public static final HashMap<String, RateLimiter> RATE_LIMITER_HASH_MAP = new HashMap<>();

    static {
        ApiVerifySystemMapper verifySystemMapper = BaseDBConnect.INSTANCE.getSqlSession().getMapper(ApiVerifySystemMapper.class);
        List<ApiVerifySystemDO> apiVerifySystemDOS = verifySystemMapper.selectAll();
        apiVerifySystemDOS.forEach(apiVerifySystemDO -> {
            /* 初始化系统验证 */
            API_VERIFY_SYSTEM_DO_HASH_MAP.put(apiVerifySystemDO.getSystemName(), apiVerifySystemDO);
            /* 初始化限流 */
            RateLimiter rateLimiter = RateLimiter.create(apiVerifySystemDO.getRateLimit());
            RATE_LIMITER_HASH_MAP.put(apiVerifySystemDO.getSystemName(),rateLimiter);
        });
    }
}
