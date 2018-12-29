package com.api.gateway.enums;

import lombok.Getter;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * BaseVerifyCode
 *
 * @author zhangfuhao
 * @Desc 异常统一code
 * @date 2018年10月16日
 * @Version V1.0
 */
@Getter
public enum BaseVerifyCodeEnum {
    /**
     * response状态和信息
     */
    VERIFY_SUCCESS(0, "成功"),
    VERIFY_UNKNOWN_FAIL(1, "系统异常"),
    VERIFY_METHOD_FAIL(2, "不支持此方法"),
    VERIFY_HEADER_FAIL(3, "未知请求头"),
    VERIFY_URI_FAIL(4, "未知请求模块"),
    VERIFY_RATE_LIMIT_FAIL(5, "请求速度过快"),
    VERIFY_BLACK_FAIL(6, "IP已被封禁"),
    VERIFY_CLIENT_TIMEOUT(7, "请求系统超时");

    BaseVerifyCodeEnum(Integer code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    private static final ConcurrentMap<Integer, BaseVerifyCodeEnum> CODE_MAP = new ConcurrentHashMap<>(
            BaseVerifyCodeEnum.values().length);

    /**
     * 填充CODE --> Enum的Map
     */
    static {
        for (BaseVerifyCodeEnum baseVerifyCodeEnum : BaseVerifyCodeEnum.values()) {
            CODE_MAP.put(baseVerifyCodeEnum.code, baseVerifyCodeEnum);
        }
    }

    public static BaseVerifyCodeEnum getByCode(Integer code) {
        return Objects.isNull(code) ? null : CODE_MAP.get(code);
    }

    /**
     * 错误编码
     */
    private Integer code;
    /**
     * 错误描述
     */
    private String  describe;
}
