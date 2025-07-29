-- 群组表结构 (v1.0)
-- 关闭外键检查（避免依赖冲突）
SET FOREIGN_KEY_CHECKS = 0;

-- 删除旧表（如果存在）
DROP TABLE IF EXISTS `t_group`;

-- 创建用户表
CREATE TABLE `t_group` (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                           `chat_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群 ID',
                           `chat_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群名称',
                           `chat_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群类型',
                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（默认当前时间）',
                           `status` int NOT NULL DEFAULT 0 COMMENT '状态：1-加入未给权限，2-加入已给权限 9-禁用',
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE INDEX `idx_group_id`(`group_id`) USING BTREE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci
  COMMENT='群组表'
  AUTO_INCREMENT=1;  -- 明确主键从1开始

-- 重置主键序列（两种方式任选其一）
-- 方式1：直接修改表
ALTER TABLE `t_group` AUTO_INCREMENT = 1;

-- 方式2：通过TRUNCATE（会清空数据！）
-- TRUNCATE TABLE `t_group`;

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1;