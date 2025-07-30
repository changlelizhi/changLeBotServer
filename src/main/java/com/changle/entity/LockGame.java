package com.changle.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : 长乐
 * @package : com.changle.entity
 * @project : changLeBotServer
 * @name : LockGame
 * @Date : 2025/7/30
 * @Description : 锁游戏实体类
 */
@Data
@Builder
public class LockGame {

    private Integer id;

    private String lockGameId;

    private String userId;

    private Integer lockGameMode;

    private Integer lockGameStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime pauseTime;

}
