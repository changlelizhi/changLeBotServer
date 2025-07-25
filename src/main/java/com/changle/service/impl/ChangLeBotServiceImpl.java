package com.changle.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.changle.bot.ChangLeBotCommands;
import com.changle.bot.botenum.BotCommands;
import com.changle.bot.botenum.BotInfo;
import com.changle.entity.User;
import com.changle.service.ChangLeBotService;
import com.changle.service.GroupService;
import com.changle.service.SendMsgService;
import com.changle.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllGroupChats;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonCommands;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : 长乐
 * @package : com.changle.service.impl
 * @project : changLeBotServer
 * @name : ChangLeBotServiceImpl
 * @Date : 2025/7/24
 * @Description :
 */

@Slf4j
@Service
public class ChangLeBotServiceImpl implements ChangLeBotService {

    @Autowired
    private SendMsgService sendMsgService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Override
    public void mainFunction(TelegramClient telegramClient, Update update) {
        log.info("收到消息: {}", JSONObject.toJSONString(update));
        //需要验证群在redis中的状态


        if (welcome(update)) {
            //todo 需要把群加入白名单

        } else {
            //todo 需要把群加入黑名单

        }

        if (update.hasMessage() && update.getMessage().isCommand()) {
            String text = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if (StringUtils.isBlank(text)) {

            }

            //todo 私聊机器人命令
            if (update.getMessage().getChat().isUserChat()) {
                if (text.equals(BotCommands.START.getCommand())) {
                    sendMsgService.sendStartMessage(telegramClient, chatId);
                    User user = User.builder().userId("1111111").userName("1111111").addTimeCount(4).reduceTimeCount(4).createTime(LocalDateTime.now()).status(1).build();
                    boolean register = userService.register(user);
                    log.info("用户注册结果: {}", register);
                    return;
                }
                if (text.equals(BotCommands.HELP.getCommand())) {
                    sendMsgService.sendHelpMessage(telegramClient, chatId);
                    return;
                }

                if (text.equals(BotCommands.CHINESE.getCommand())) {
                    sendMsgService.sendChinese(telegramClient, chatId);
                    return;
                }

            }

            //todo 群聊机器人命令（要检查群组是否被加入到白名单）
            if (update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat()) {


            }


        }
    }

    @Override
    public void setBotCommand(TelegramClient telegramClient) {
        try {
            List<BotCommand> groupCommand = ChangLeBotCommands.setGroupCommand();
            List<BotCommand> privetChatCommand = ChangLeBotCommands.setPrivetChatCommand();
            BotCommandScopeAllGroupChats botCommandScopeAllGroupChats = BotCommandScopeAllGroupChats.builder().build();
            SetMyCommands setAnyOneCommands = SetMyCommands.builder().commands(groupCommand).scope(botCommandScopeAllGroupChats).build();
            telegramClient.execute(setAnyOneCommands);

            SetChatMenuButton menuButton = SetChatMenuButton.builder()
                    .menuButton(new MenuButtonCommands())
                    .build();
            telegramClient.execute(menuButton);
            SetMyCommands setPrivetCommand = SetMyCommands.builder().commands(privetChatCommand).build();
            telegramClient.execute(setPrivetCommand);
        } catch (TelegramApiException e) {
            log.error("设置命令失败: {}", e.getMessage());

        }
    }

    /**
     * 机器人入群提示出来
     */
    private static boolean welcome(Update update) {
        return update.hasMyChatMember() && update.getMyChatMember().getNewChatMember().getStatus().equals(BotInfo.MEMBER.getInfo()) && update.getMyChatMember().getNewChatMember().getUser().getIsBot().equals(true)
                && update.getMyChatMember().getNewChatMember().getUser().getUserName().equals(BotInfo.BOT_NAME.getInfo());
    }
}
