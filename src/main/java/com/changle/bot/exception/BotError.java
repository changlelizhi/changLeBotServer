package com.changle.bot.exception;

import lombok.Getter;

/**
 * @author : 长乐
 * @package : com.changle.bot.botenum
 * @project : changLeBotServer
 * @name : BotError
 * @Date : 2025/7/29
 * @Description :
 */
@Getter
public enum BotError {

    BOT_JOIN_FAILED(9000,"群组加入处理失败"),

    BOT_LEAVE_FAILED(9001,"群组退出处理失败"),

    BOT_SET_ADMIN_FAILED(9002,"设置bot为管理员失败"),

    BOT_DELETE_MSG_FAILED(9100,"删除消息处理失败"),
    ;

    private final Integer errorCode;

    private final String errorMsg;

    BotError(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public static String getErrorMsg(Integer errorCode) {
        for (BotError value : BotError.values()) {
            if (value.getErrorCode().equals(errorCode)) {
                return value.getErrorMsg();
            }
        }
        return null;
    }
}
