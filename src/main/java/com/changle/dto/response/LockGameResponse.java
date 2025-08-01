package com.changle.dto.response;

import lombok.Data;

/**
 * @author : 长乐
 * @package : com.changle.dto.response
 * @project : changLeBotServer
 * @name : LockGameResponse
 * @Date : 2025/7/31
 * @Description :
 */
@Data
public class LockGameResponse {

    private String lockGameId;

    private String lockGameModel;

    private String lockGameStatus;

    private String lockGameStartTime;

    private String lockGameEndTime;

}
