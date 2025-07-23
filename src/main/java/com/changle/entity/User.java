package com.changle.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * @author : 长乐
 * @package : com.changle.entity
 * @project : changLeBotServer
 * @name : User
 * @Date : 2025/7/23
 * @Description : 用户类
 */
@Builder
@Getter
public class User {

    private String userId;

    private String userName;

    private String tgName;

    private String toys;

    private String color;

    public User() {
    }
}
