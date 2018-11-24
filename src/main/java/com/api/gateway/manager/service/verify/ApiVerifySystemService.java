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
     * 根据系统名称获取数据
     * @param systemName
     * @return
     */
    ApiVerifySystemDO getBySystemName(String systemName);
}
