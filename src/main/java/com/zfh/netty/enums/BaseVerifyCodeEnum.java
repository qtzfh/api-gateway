package com.zfh.netty.enums;

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
    VERIFY_UNKNOWN_FAIL(1, "未知异常"),
    VERIFY_METHOD_FAIL(2, "不支持此方法"),
    VERIFY_HEADER_FAIL(3, "未知请求头"),
    VERIFY_URI_FAIL(4, "未知请求地址");

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
