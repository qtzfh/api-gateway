package com.api.gateway.handle;

import com.alibaba.fastjson.JSONObject;
import com.api.gateway.base.BaseResult;
import com.api.gateway.base.BaseService;
import com.api.gateway.constants.BaseConstant;
import com.api.gateway.enums.BaseHttpHeaderEnum;
import com.api.gateway.enums.BaseHttpMethodEnum;
import com.api.gateway.enums.BaseVerifyCodeEnum;
import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * BaseRequestValidate
 *
 * @author zhangfuhao
 * @Desc 请求校验工具
 * @date 2018年10月16日
 * @Version V1.0
 */
public class ApiVerifyHandle {
    private static final Integer SYSTEM_MAX_URL_LENGTH = 1;

    /**
     * 校验请求表示
     *
     * @param request
     * @return
     */
    public static Integer verify(HttpRequest request) {
        /* 系统名称，使用数组赋值 */
        String[] systemName = new String[SYSTEM_MAX_URL_LENGTH];
        if (!verifyMethod(request.method().name())) {
            return BaseVerifyCodeEnum.VERIFY_METHOD_FAIL.getCode();
        } else if (!verifyUri(request.uri(), systemName)) {
            return BaseVerifyCodeEnum.VERIFY_URI_FAIL.getCode();
        } else if (!verifyHeaders(request.headers())) {
            return BaseVerifyCodeEnum.VERIFY_HEADER_FAIL.getCode();
        } else if (!RateLimiterHandle.getAcquire(systemName[0])) {
            return BaseVerifyCodeEnum.VERIFY_RATE_LIMIT_FAIL.getCode();
        }
        return BaseVerifyCodeEnum.VERIFY_SUCCESS.getCode();
    }

    /**
     * 获取异常信息
     *
     * @return
     */
    public static String getVerifyMessage(Integer verifyErrorCode) {
        BaseVerifyCodeEnum baseVerifyCodeEnum = BaseVerifyCodeEnum.getByCode(verifyErrorCode);
        if (Objects.isNull(baseVerifyCodeEnum)) {
            return BaseResult.defaultErrorMessage();
        }
        return JSONObject.toJSONString(BaseResult.fail(baseVerifyCodeEnum));
    }

    /**
     * 获取系统域名前缀
     * @param request
     * @return
     */
    public static String getGateWayUrl(HttpRequest request) {
        String[] url = StringUtils.splitByWholeSeparator(request.uri(), BaseConstant.SPLIT_URI_STRING);
        ApiVerifySystemDO apiVerifySystemDO = BaseService.API_VERIFY_SYSTEM_SERVICE.getBySystemName(url[0]);
        return apiVerifySystemDO.getSystemUrl() + request.uri();
    }

    /**
     * 仅允许get/post 请求
     *
     * @param method 请求名称
     * @return
     */
    private static boolean verifyMethod(String method) {
        if (StringUtils.isBlank(method)) {
            return false;
        }
        if (method.toUpperCase().equals(BaseHttpMethodEnum.GET.name()) || method.toUpperCase().equals(BaseHttpMethodEnum.POST.name())) {
            return true;
        }
        return false;
    }

    /**
     * 验证接口请求头
     *
     * @param headers 接口请求头
     * @return
     */
    private static boolean verifyHeaders(HttpHeaders headers) {
        if (headers.isEmpty()) {
            return false;
        }
        for (BaseHttpHeaderEnum value : BaseHttpHeaderEnum.values()) {
            if (StringUtils.isBlank(headers.get(value.getHeaderKey())) || !headers.get(value.getHeaderKey()).equals(value.getHeaderValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证请求uri
     *
     * @param uri        请求的uri
     * @param systemName 获取系统名称
     * @return
     */
    private static boolean verifyUri(String uri, String[] systemName) {
        if (StringUtils.isBlank(uri)) {
            return false;
        }
        String[] url = StringUtils.splitByWholeSeparator(uri, BaseConstant.SPLIT_URI_STRING);
        if (url.length <= 0) {
            return false;
        }
        systemName[0] = url[0];
        ApiVerifySystemDO apiVerifySystemDO = BaseService.API_VERIFY_SYSTEM_SERVICE.getBySystemName(url[0]);
        if (Objects.isNull(apiVerifySystemDO)) {
            return false;
        }
        return true;
    }
}
