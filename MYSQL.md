## 数据库创建脚本
```$xslt
CREATE TABLE `api_verify_system` (
  `id` int(10) unsigned NOT NULL COMMENT '主键id',
  `system_name` varchar(32) NOT NULL COMMENT '系统名称',
  `system_url` varchar(64) NOT NULL COMMENT '系统前缀地址',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_system_name` (`system_name`) COMMENT '系统名称唯一主键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='接入系统信息';
```
