package com.api.gateway.manager.service.verify.impl;

import com.api.gateway.base.BaseDBConnect;
import com.api.gateway.base.BaseInitFactory;
import com.api.gateway.manager.dao.verify.ApiVerifySystemMapper;
import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;
import com.api.gateway.manager.service.verify.ApiVerifySystemService;

import java.util.Objects;

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
        ApiVerifySystemDO apiVerifySystemDO = BaseInitFactory.API_VERIFY_SYSTEM_DO_HASH_MAP.get(systemName);
        if (Objects.nonNull(apiVerifySystemDO)) {
            return apiVerifySystemDO;
        }
        // TODO 风险提示，如果过多的请求都每次都需要通过此处查询数据库，则可能将拖挂数据库
        apiVerifySystemDO = new ApiVerifySystemDO();
        apiVerifySystemDO.setSystemName(systemName);
        apiVerifySystemDO = apiVerifySystemMapper.selectOne(apiVerifySystemDO);
        if (Objects.nonNull(apiVerifySystemDO)){
            BaseInitFactory.API_VERIFY_SYSTEM_DO_HASH_MAP.put(systemName, apiVerifySystemDO);
        }
        return apiVerifySystemDO;
    }
}
