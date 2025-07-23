package com.changle.bot;

import com.changle.bot.botenum.BotCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

/**
 * @author : 长乐
 * @package : com.changle.bot
 * @project : changLeBotServer
 * @name : ChangLeBotCommands
 * @Date : 2025/7/23
 * @Description : 长乐Bot 命令
 */
public class ChangLeBotCommands {

    public static List<BotCommand> setGroupCommand() {
        return List.of(
                new BotCommand(BotCommands.LOCK.getCommand(), "锁信息"),
                new BotCommand(BotCommands.JIAO_LANG.getCommand(), "郊狼")
        );
    }

    public static List<BotCommand> setPrivetChatCommand() {
        return List.of(
                new BotCommand(BotCommands.START.getCommand(), "启动Bot"),
                new BotCommand(BotCommands.HELP.getCommand(), "帮助")
        );
    }
}
