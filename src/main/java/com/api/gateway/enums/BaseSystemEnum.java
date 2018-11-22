package com.api.gateway.enums;

import com.api.gateway.common.BaseProperties;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * BaseSystemEnum
 *
 * @author zhangfuhao
 * @Desc 系统映射
 * @date 2018年10月19日
 * @Version V1.0
 */
public enum BaseSystemEnum {
    /**
     * 系统名称和系统uri前缀
     */
    ZFH_SPRINGBOOT(BaseProperties.ZFH_SPRINGBOOT, BaseProperties.ZFH_SPRINGBOOT_URI);

    BaseSystemEnum(String systemName, String systemUri) {
        this.systemName = systemName;
        this.systemUri = systemUri;
    }

    private static final ConcurrentMap<String, BaseSystemEnum> CODE_MAP = new ConcurrentHashMap<>(
            BaseVerifyCodeEnum.values().length);

    /**
     * 填充CODE --> Enum的Map
     */
    static {
        for (BaseSystemEnum baseVerifyCodeEnum : BaseSystemEnum.values()) {
            CODE_MAP.put(baseVerifyCodeEnum.systemName, baseVerifyCodeEnum);
        }
    }

    public static BaseSystemEnum getBySystemName(String systemName) {
        return StringUtils.isBlank(systemName) ? null : CODE_MAP.get(systemName);
    }

    /**
     * 接入系统名
     */
    @Getter
    private String systemName;
    /**
     * 接入系统请求uri前缀
     */
    @Getter
    private String systemUri;
}
