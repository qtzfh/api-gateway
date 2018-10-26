package com.zfh.netty.enums;

import com.zfh.netty.common.BaseProperties;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * BaseHttpUriVerify
 *
 * @author zhangfuhao
 * @Desc http请求接口验证
 * @date 2018年10月19日
 * @Version V1.0
 */
public enum BaseHttpUriVerifyEnum {
    /**
     * uri请求地址和目标系统
     */
    ZFH_SPRINGBOOT_ZHIHU_TOPIC("/zhihu/topic/list", BaseProperties.ZFH_SPRINGBOOT);

    BaseHttpUriVerifyEnum(String requestUri, String systemName) {
        this.requestUri = requestUri;
        this.systemName = systemName;
    }

    private static final ConcurrentMap<String, BaseHttpUriVerifyEnum> CODE_MAP = new ConcurrentHashMap<>(
            BaseHttpUriVerifyEnum.values().length);

    /**
     * 填充CODE --> Enum的Map
     */
    static {
        for (BaseHttpUriVerifyEnum baseHttpUriVerifyEnum : BaseHttpUriVerifyEnum.values()) {
            CODE_MAP.put(baseHttpUriVerifyEnum.requestUri, baseHttpUriVerifyEnum);
        }
    }

    public static BaseHttpUriVerifyEnum getByRequestUri(String requestUri) {
        return StringUtils.isBlank(requestUri) ? null : CODE_MAP.get(requestUri);
    }

    /**
     * 网关请求地址
     */
    @Getter
    private String requestUri;
    /**
     * 源系统请求地址
     */
    /**
     * 源系统请求名称
     */
    @Getter
    private String systemName;
}
