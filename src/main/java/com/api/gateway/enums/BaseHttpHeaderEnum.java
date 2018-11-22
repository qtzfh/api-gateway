package com.api.gateway.enums;

import lombok.Getter;

/**
 * BaseHeader
 *
 * @author zhangfuhao
 * @Desc 请求头校验类
 * @date 2018年10月16日
 * @Version V1.0
 */
public enum BaseHttpHeaderEnum {
    /**
     * http请求头
     */
    HEADER_APP_ID("app_id", "test_id");

    BaseHttpHeaderEnum(String headerKey, String headerValue) {
        this.headerKey = headerKey;
        this.headerValue = headerValue;
    }

    /**
     * 请求头key
     */
    @Getter
    private String headerKey;
    /**
     * 请求头value
     */
    @Getter
    private String headerValue;
}
