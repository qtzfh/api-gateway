package com.api.gateway.common;

import com.alibaba.fastjson.JSONObject;
import com.api.gateway.constants.BaseConstant;
import com.api.gateway.enums.BaseHttpHeaderEnum;
import com.api.gateway.enums.BaseHttpMethodEnum;
import com.api.gateway.enums.BaseVerifyCodeEnum;
import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;
import com.api.gateway.manager.service.verify.ApiVerifySystemService;
import com.api.gateway.manager.service.verify.impl.ApiVerifySystemServiceImpl;
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
public class BaseHttpRequestVerify {
    /**
     * 校验请求表示
     *
     * @param request
     * @return
     */
    public static Integer verify(HttpRequest request) {
        if (!verifyMethod(request.method().name())) {
            return BaseVerifyCodeEnum.VERIFY_METHOD_FAIL.getCode();
        } else if (!verifyUri(request.uri())) {
            return BaseVerifyCodeEnum.VERIFY_URI_FAIL.getCode();
        } else if (!verifyHeaders(request.headers())) {
            return BaseVerifyCodeEnum.VERIFY_HEADER_FAIL.getCode();
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
            return defaultErrorMessage();
        }
        return JSONObject.toJSONString(BaseResult.fail(baseVerifyCodeEnum));
    }

    /**
     * 默认的异常返回信息
     *
     * @return
     */
    protected static String defaultErrorMessage() {
        return JSONObject.toJSONString(BaseResult.fail(BaseVerifyCodeEnum.VERIFY_UNKNOWN_FAIL));
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
        if (method.toUpperCase().equals(BaseHttpMethodEnum.GET.name()) ||
                method.toUpperCase().equals(BaseHttpMethodEnum.POST.name())) {
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
            if (StringUtils.isBlank(headers.get(value.getHeaderKey())) ||
                    !headers.get(value.getHeaderKey()).equals(value.getHeaderValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证请求uri
     *
     * @param uri 请求的uri
     * @return
     */
    private static boolean verifyUri(String uri) {
        if (StringUtils.isBlank(uri)) {
            return false;
        }
        String[] url = StringUtils.splitByWholeSeparator(uri, BaseConstant.SPLIT_URI_STRING);
        if (url.length <= 0) {
            return false;
        }
        ApiVerifySystemDO apiVerifySystemDO =  BaseService.API_VERIFY_SYSTEM_SERVICE.getBySystemName(url[0]);
        if (Objects.isNull(apiVerifySystemDO)) {
            return false;
        }
        return true;
    }
}
