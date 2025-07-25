package com.changle.bot.botenum;

import lombok.Getter;

/**
 * @author : 长乐
 * @package : com.changle.bot.botenum
 * @project : changLeBotServer
 * @name : BotCommands
 * @Date : 2025/7/23
 * @Description : bot命令 枚举
 */
@Getter
public enum BotCommands {

    /**
     * 私有命令
     */
    START("/start"),

    HELP("/help"),

    CHINESE("/chinese"),

    /**
     * 群组命令
     */
    LOCK("/lock"),

    JIAO_LANG("/jiaolang");


    private final String command;

    BotCommands(String command) {
        this.command = command;
    }

}
