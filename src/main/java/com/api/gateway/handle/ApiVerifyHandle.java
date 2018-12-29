package com.api.gateway.handle;

import com.alibaba.fastjson.JSONObject;
import com.api.gateway.base.BaseInitFactory;
import com.api.gateway.base.BaseRequestInfo;
import com.api.gateway.base.BaseResult;
import com.api.gateway.base.BaseService;
import com.api.gateway.constants.BaseConstant;
import com.api.gateway.enums.BaseHttpHeaderEnum;
import com.api.gateway.enums.BaseHttpMethodEnum;
import com.api.gateway.enums.BaseVerifyCodeEnum;
import com.api.gateway.manager.dataobject.verify.ApiVerifySystemDO;
import com.google.common.util.concurrent.RateLimiter;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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
     * 校验请求参数
     *
     * @param baseRequestInfo request请求信息
     * @return 异常code
     */
    protected static Integer verify(BaseRequestInfo baseRequestInfo) {
        /* 系统名称，使用数组赋值 */
        String[] systemName = new String[SYSTEM_MAX_URL_LENGTH];
        if (!verifyMethod(baseRequestInfo.getMethod().name())) {
            return BaseVerifyCodeEnum.VERIFY_METHOD_FAIL.getCode();
        }
        if (!verifyUri(baseRequestInfo.getUri(), systemName)) {
            return BaseVerifyCodeEnum.VERIFY_URI_FAIL.getCode();
        }
        if (!verifyHeaders(baseRequestInfo.getHeaders())) {
            return BaseVerifyCodeEnum.VERIFY_HEADER_FAIL.getCode();
        }
        if (!getAcquire(systemName[0])) {
            return BaseVerifyCodeEnum.VERIFY_RATE_LIMIT_FAIL.getCode();
        }
        if (verifyBlack(baseRequestInfo.getClientIp())) {
            return BaseVerifyCodeEnum.VERIFY_BLACK_FAIL.getCode();
        }
        return BaseVerifyCodeEnum.VERIFY_SUCCESS.getCode();
    }

    /**
     * 获取异常信息
     *
     * @return 异常描述
     */
    public static String getVerifyMessage(Integer verifyErrorCode) {
        BaseVerifyCodeEnum baseVerifyCodeEnum = BaseVerifyCodeEnum.getByCode(verifyErrorCode);
        if (Objects.isNull(baseVerifyCodeEnum)) {
            return BaseResult.defaultErrorMessage();
        }
        return JSONObject.toJSONString(BaseResult.fail(baseVerifyCodeEnum));
    }

    /**
     * 仅允许get/post 请求
     *
     * @param method 请求名称
     * @return 是否验证成功
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
     * @return 是否验证成功
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
     * @return 是否验证成功
     */
    private static boolean verifyUri(String uri, String[] systemName) {
        if (StringUtils.isBlank(uri)) {
            return false;
        }
        String[] url = StringUtils.splitByWholeSeparator(uri, BaseConstant.SPLIT_URI_STRING);
        if (Objects.isNull(url) || url.length <= 0) {
            return false;
        }
        systemName[0] = url[0];
        ApiVerifySystemDO apiVerifySystemDO = BaseService.API_VERIFY_SYSTEM_SERVICE.getBySystemName(url[0]);
        return Objects.nonNull(apiVerifySystemDO);
    }

    /**
     * 获取限流令牌
     *
     * @param systemName 系统名称
     * @return 获取请求是否被通过
     */
    private static boolean getAcquire(String systemName) {
        if (StringUtils.isBlank(systemName)) {
            return false;
        }
        RateLimiter rateLimiter = BaseInitFactory.RATE_LIMITER_HASH_MAP.get(systemName);
        if (Objects.isNull(rateLimiter)) {
            return false;
        }
        return rateLimiter.tryAcquire();
    }

    /**
     * 验证是否为黑名单ip
     *
     * @param clientIp ip地址
     * @return 是否为黑名单
     */
    private static boolean verifyBlack(String clientIp) {
        if (StringUtils.isBlank(clientIp)) {
            return true;
        }
        Integer value = BaseService.API_BLACK_LIST_SERVICE.getLevelByIp(clientIp);
        return value >= NumberUtils.INTEGER_ZERO;
    }
}
