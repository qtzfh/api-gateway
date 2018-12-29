package com.api.gateway.manager.service.verify;

/**
 * ApiWhiteListService
 *
 * @author zhangfuhao
 * @Desc 系统黑名单服务
 * @date 2018年12月29日
 * @Version V1.0
 */
public interface ApiBlackListService {
    /**
     * 根据ip过去白名单等级
     * @param ip ip地址
     * @return 黑名单等级（-1：代表不存在）
     */
    Integer getLevelByIp(String ip);
}
