package com.api.gateway.manager.dataobject.verify;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * ApiBlackListDO
 *
 * @author zhangfuhao
 * @Desc 黑名单信息
 * @date 2018年11月29日
 * @Version V1.0
 */
@Getter
@Setter
@ToString
@Table(name = "api_black_list")
public class ApiBlackListDO {
    /**
     * 封禁IP
     */
    @Column(name = "ip")
    private String  ip;
    /**
     * 封禁等级
     */
    @Column(name = "level")
    private Integer level;
}
