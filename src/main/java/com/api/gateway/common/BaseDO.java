package com.api.gateway.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * BaseModel
 *
 * @author zhangfuhao
 * @Desc 基础DO类
 * @date 2018年11月24日
 * @Version V1.0
 */
@Getter
@Setter
@ToString
public class BaseDO {
    /**
     * 主键id
     */
    @Id
    private Long id;
    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;
}
