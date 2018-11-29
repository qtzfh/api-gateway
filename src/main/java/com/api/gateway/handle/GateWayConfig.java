package com.api.gateway.handle;

import com.api.gateway.base.BaseService;
import com.api.gateway.constants.BaseConstant;
import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;

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
     * @param request
     * @return
     */
    public static String getGateWayUrl(HttpRequest request) {
        String[] url = StringUtils.splitByWholeSeparator(request.uri(), BaseConstant.SPLIT_URI_STRING);
        ApiVerifySystemDO apiVerifySystemDO = BaseService.API_VERIFY_SYSTEM_SERVICE.getBySystemName(url[0]);
        return apiVerifySystemDO.getSystemUrl() + request.uri();
    }
}
