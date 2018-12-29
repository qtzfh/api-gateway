package com.api.gateway.handle;

import com.api.gateway.base.BaseRequestInfo;
import com.api.gateway.base.BaseService;
import com.api.gateway.constants.BaseConstant;
import com.api.gateway.exception.ServiceException;
import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * GateWayConfig
 *
 * @author zhangfuhao
 * @Desc 获取网关配置
 * @date 2018年11月29日
 * @Version V1.0
 */
public class GateWayConfig {

    /**
     * 获取系统域名前缀
     *
     * @param requestInfo
     * @return
     */
    public static String getGateWayUrl(BaseRequestInfo requestInfo) {
        String[] url = StringUtils.splitByWholeSeparator(requestInfo.getUri(), BaseConstant.SPLIT_URI_STRING);
        if (Objects.isNull(url) || url.length <= 0){
            throw new ServiceException("获取请求地址失败");
        }
        ApiVerifySystemDO apiVerifySystemDO = BaseService.API_VERIFY_SYSTEM_SERVICE.getBySystemName(url[0]);
        if (Objects.isNull(apiVerifySystemDO)){
            throw new ServiceException("获取系统系统");
        }
        return apiVerifySystemDO.getSystemUrl() + requestInfo.getUri();
    }
}
