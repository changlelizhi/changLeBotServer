-- 用户表结构 (v1.0)
-- 关闭外键检查（避免依赖冲突）
SET FOREIGN_KEY_CHECKS = 0;

-- 删除旧表（如果存在）
DROP TABLE IF EXISTS `t_user`;

-- 创建用户表
CREATE TABLE `t_user` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          `user_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
                          `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
                          `tg_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Telegram用户名',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（默认当前时间）',
                          `add_time_count` int NOT NULL DEFAULT 0 COMMENT '增加时间计数',
                          `reduce_time_count` int NOT NULL DEFAULT 0 COMMENT '减少时间计数',
                          `status` int NOT NULL DEFAULT 0 COMMENT '状态：0-正常 1-禁用',
                          `user_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '个人简介',
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci
  COMMENT='用户表'
  AUTO_INCREMENT=1;  -- 明确主键从1开始

-- 重置主键序列（两种方式任选其一）
-- 方式1：直接修改表
ALTER TABLE `t_user` AUTO_INCREMENT = 1;

-- 方式2：通过TRUNCATE（会清空数据！）
-- TRUNCATE TABLE `t_user`;

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1;