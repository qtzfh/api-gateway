package com.api.gateway.manager.dataobject.verify;

import com.api.gateway.base.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import scala.Int;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * ApiVerifySystem
 *
 * @author zhangfuhao
 * @Desc 系统信息表
 * @date 2018年11月24日
 * @Version V1.0
 */
@Getter
@Setter
@ToString
@Table(name = "api_verify_system")
public class ApiVerifySystemDO extends BaseDO {
    /**
     * 系统名称
     */
    @Column(name = "system_name")
    private String systemName;
    /**
     * 系统前缀url
     */
    @Column(name = "system_url")
    private String systemUrl;

    /**
     * 系统等级
     */
    @Column(name = "system_level")
    private Integer systemLevel;

    /**
     * 限流速率
     */
    @Column(name = "rate_limit")
    private Integer rateLimit;
}
