changLeBotServer

MySQL建表：
字段：id 主键自增 不能为空
user_id 用户id varchar30 不能为空
user_name 用户名 varchar50 不能为空
tg_name tg用户名 varchar50 可以为空
create_time 创建时间 datetime 不能为空
add_time_count 增加时间计数 smallint 不能为空
reduce_time_count 减少时间计数 smallint 不能为空
toys 玩具 text  可以为空
xpinfo xp信息 text 可以为空
clothing  服装信息 text 可以为空
索引 user_id
字符集 utf8mb4 排序规则 utf8mb4_0900_ai_ci 