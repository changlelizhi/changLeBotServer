package com.changle.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : 长乐
 * @package : com.changle.entity
 * @project : changLeBotServer
 * @name : Group
 * @Date : 2025/7/25
 * @Description : 群组
 */
@Builder
@Data
public class Group {

    private Integer id;

    private String chatId;

    private String chatName;

    private String chatType;

    private Integer status;

    private LocalDateTime createTime;

}
