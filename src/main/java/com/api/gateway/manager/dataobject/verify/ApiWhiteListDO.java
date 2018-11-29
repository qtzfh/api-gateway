package com.api.gateway.manager.dataobject.verify;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * ApiWhiteListDO
 *
 * @author zhangfuhao
 * @Desc 白名单信息
 * @date 2018年11月29日
 * @Version V1.0
 */
@Getter
@Setter
@ToString
@Table(name = "api_white_list")
public class ApiWhiteListDO {
    /**
     * ip封禁
     */
    @Column(name = "ip")
    private String ip;
    /**
     * 权限等级(1~10分级)
     */
    @Column(name = "level")
    private String level;
}
