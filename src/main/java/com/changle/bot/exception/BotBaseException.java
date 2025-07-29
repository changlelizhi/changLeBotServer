package com.changle.bot.exception;

import lombok.Getter;

/**
 * @author : 长乐
 * @package : com.changle.bot.exception
 * @project : changLeBotServer
 * @name : BotBaseException
 * @Date : 2025/7/29
 * @Description : 错误处理
 */
@Getter
public class BotBaseException extends RuntimeException{



    private final BotError botError;


    public BotBaseException(BotError botError) {
        this.botError = botError;
    }

    public BotBaseException(BotError botError, Throwable  cause) {
        super(botError.getErrorMsg());
        this.botError = botError;
    }
}
