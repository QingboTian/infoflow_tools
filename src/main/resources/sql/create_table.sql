-- infoflow_tools.group_bot_info_table definition

CREATE TABLE `group_bot_info_table` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `bot_url` varchar(1000) NOT NULL,
                                        `bot_aes_key` varchar(1000) NOT NULL,
                                        `bot_token` varchar(1000) NOT NULL,
                                        `state` int(11) NOT NULL DEFAULT '1',
                                        `describe` varchar(1000) DEFAULT NULL,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- infoflow_tools.message_table definition

CREATE TABLE `message_table` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `message_id` varchar(1000) NOT NULL,
                                 `uid` varchar(100) NOT NULL,
                                 `group_id` varchar(1000) NOT NULL,
                                 `body` varchar(2000) NOT NULL,
                                 `created` timestamp NOT NULL,
                                 `modified` timestamp NULL DEFAULT NULL,
                                 `state` int(11) NOT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;


-- infoflow_tools.remind_table definition

CREATE TABLE `remind_table` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `uid` varchar(100) NOT NULL COMMENT '创建提醒的用户',
                                `content` varchar(1024) NOT NULL COMMENT '提醒内容',
                                `timer` varchar(100) NOT NULL COMMENT '提醒时间',
                                `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，-1表示已经失效',
                                `group_id` bigint(20) DEFAULT NULL COMMENT '群组Id',
                                `ats` varchar(2048) DEFAULT NULL COMMENT '艾特了哪些用户，逗号分隔',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COMMENT='提醒';