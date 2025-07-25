package com.changle.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : 长乐
 * @package : com.changle.entity
 * @project : changLeBotServer
 * @name : User
 * @Date : 2025/7/23
 * @Description : 用户类
 */
@Builder
@Data
public class User {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * Telegram用户名
     */
    private String tgName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 增加时间计数
     */
    private Integer addTimeCount;

    /**
     * 减少时间计数
     */
    private Integer reduceTimeCount;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 玩具信息
     */
    private String toys;

    /**
     * XP信息
     */
    private String xpinfo;

    /**
     * 服装信息
     */
    private String clothing;

}
