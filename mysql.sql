CREATE TABLE `api_verify_system` (
  `id` int(10) unsigned NOT NULL COMMENT '主键id',
  `system_name` varchar(32) NOT NULL COMMENT '系统名称',
  `system_url` varchar(64) NOT NULL COMMENT '系统前缀地址',
  `system_level` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '系统等级（对应白名单1~10）',
  `rate_limit` int(11) unsigned NOT NULL DEFAULT '20' COMMENT '限流速率',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_system_name` (`system_name`) COMMENT '系统名称唯一主键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='网关验证系统信息';

CREATE TABLE `api_black_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ip` varchar(16) NOT NULL COMMENT 'ip地址',
  `level` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '封禁等级（1~10分级）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='网关黑名单信息';

INSERT INTO `api_gateway`.`api_verify_system`(`id`, `system_name`, `system_url`, `rate_limit`, `gmt_create`, `gmt_modified`, `system_level`) VALUES (1, 'zfhspringboot', 'http://localhost:8081', 20, '2018-11-29 14:10:56', '2018-11-29 14:10:58', 1);
INSERT INTO `api_gateway`.`api_black_list`(`id`, `ip`, `level`, `gmt_create`, `gmt_modified`) VALUES (1, '127.0.0.1', 1, '2019-02-15 10:36:19', '2019-02-15 10:36:21');

