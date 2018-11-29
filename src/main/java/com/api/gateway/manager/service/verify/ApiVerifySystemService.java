package com.api.gateway.manager.service.verify;

import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;

/**
 * ApiVerifySystemService
 *
 * @author zhangfuhao
 * @Desc 接口校验服务
 * @date 2018年11月24日
 * @Version V1.0
 */
public interface ApiVerifySystemService {
    /**
     * 根据系统名称获取数据（系统启动时，static获取所有数据存入hashMap，如果hashMap不存在则查询数据库）
     *
     * @param systemName 系统名称
     * @return
     */
    ApiVerifySystemDO getBySystemName(String systemName);
}
