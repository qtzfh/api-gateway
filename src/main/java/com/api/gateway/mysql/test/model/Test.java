package com.api.gateway.mysql.test.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Test
 *
 * @author zhangfuhao
 * @Desc
 * @date 2018年11月22日
 * @Version V1.0
 */
@Data
@Table(name = "test")
public class Test {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;
}
