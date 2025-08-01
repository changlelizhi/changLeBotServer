package com.changle.dto.request;

import lombok.Data;

import java.lang.ref.PhantomReference;

/**
 * @author : 长乐
 * @package : com.changle.dto.request
 * @project : changLeBotServer
 * @name : LockGameRequest
 * @Date : 2025/7/31
 * @Description :
 */
@Data
public class LockGameRequest {

    private String userId;

    private String lockGameModel;

    private String endTime;

}
