package com.changle.bot.botenum;

import lombok.Getter;

/**
 * @author : 长乐
 * @package : com.changle.bot.botenum
 * @project : changLeBotServer
 * @name : BotInfo
 * @Date : 2025/7/25
 * @Description :
 */
@Getter
public enum BotInfo {
    BOT_NAME("nichangs_bot"),

    GROUP_STATUS("groupStatus"),

    BOT_STATUS("botStatus"),

    BOT_IS_ADMIN("administrator"),

    MEMBER("member"),

    GROUP("group"),

    SUPERGROUP("supergroup"),

    LEFT("left");

    private final String info;

    BotInfo(String info) {
        this.info = info;
    }

}
