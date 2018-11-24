package com.api.gateway.manager.service.verify.impl;

import com.api.gateway.base.BaseDBConnect;
import com.api.gateway.manager.dao.verify.ApiVerifySystemMapper;
import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;
import com.api.gateway.manager.service.verify.ApiVerifySystemService;

/**
 * ApiVerifySystemServiceImpl
 *
 * @author zhangfuhao
 * @Desc
 * @date 2018年11月24日
 * @Version V1.0
 */
public class ApiVerifySystemServiceImpl implements ApiVerifySystemService {

    private ApiVerifySystemMapper apiVerifySystemMapper = BaseDBConnect.INSTANCE.getSqlSession().getMapper(ApiVerifySystemMapper.class);

    @Override
    public ApiVerifySystemDO getBySystemName(String systemName) {
        ApiVerifySystemDO apiVerifySystemDO = new ApiVerifySystemDO();
        apiVerifySystemDO.setSystemName(systemName);
        return apiVerifySystemMapper.selectOne(apiVerifySystemDO);
    }
}
