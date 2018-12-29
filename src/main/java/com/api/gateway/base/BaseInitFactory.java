package com.api.gateway.base;

import com.api.gateway.exception.ServiceException;
import com.api.gateway.manager.dao.verify.ApiBlackListMapper;
import com.api.gateway.manager.dao.verify.ApiVerifySystemMapper;
import com.api.gateway.manager.dataobject.verify.ApiBlackListDO;
import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;
import com.google.common.util.concurrent.RateLimiter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
    public static final ConcurrentHashMap<String, ApiVerifySystemDO> API_VERIFY_SYSTEM_DO_HASH_MAP = new ConcurrentHashMap<>();
    /**
     * 初始化限流
     */
    public static final ConcurrentHashMap<String, RateLimiter> RATE_LIMITER_HASH_MAP = new ConcurrentHashMap<>();
    /**
     * 初始化黑名单
     */
    public static final ConcurrentHashMap<String, Integer> BLACK_LIST_HASH_MAP = new ConcurrentHashMap<>();
    /**
     * init初始化方法
     */
    public static void init(){
        // TODO 当数据数量过多，此处可能会出现初始化过慢的情况，可以自定义优化
        try {
            /* 初始化网关接入系统 */
            ApiVerifySystemMapper verifySystemMapper = BaseDBConnect.INSTANCE.getSqlSession().getMapper(ApiVerifySystemMapper.class);
            List<ApiVerifySystemDO> apiVerifySystemDOS = verifySystemMapper.selectAll();
            apiVerifySystemDOS.forEach(apiVerifySystemDO -> {
                /* 初始化系统验证 */
                API_VERIFY_SYSTEM_DO_HASH_MAP.put(apiVerifySystemDO.getSystemName(), apiVerifySystemDO);
                /* 初始化限流 */
                RateLimiter rateLimiter = RateLimiter.create(apiVerifySystemDO.getRateLimit());
                RATE_LIMITER_HASH_MAP.put(apiVerifySystemDO.getSystemName(), rateLimiter);
            });
            /* 初始化黑名单 */
            ApiBlackListMapper apiBlackListMapper = BaseDBConnect.INSTANCE.getSqlSession().getMapper(ApiBlackListMapper.class);
            List<ApiBlackListDO> apiBlackListDOS = apiBlackListMapper.selectAll();
            apiBlackListDOS.forEach(apiBlackListDO -> {
                BLACK_LIST_HASH_MAP.put(apiBlackListDO.getIp(), apiBlackListDO.getLevel());
            });
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
