package com.changle.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.changle.bot.ChangLeBotCommands;
import com.changle.bot.botenum.BotCommands;
import com.changle.bot.botenum.BotInfo;
import com.changle.bot.constant.ChangLeBotConstant;
import com.changle.config.VirtualThreadConfig;
import com.changle.entity.User;
import com.changle.service.ChangLeBotService;
import com.changle.service.GroupService;
import com.changle.service.SendMsgService;
import com.changle.service.UserService;
import com.changle.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator;
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

    @Autowired
    private VirtualThreadConfig virtualThreadConfig;

    @Override
    @Async("virtualThreadExecutor")
    public void mainFunction(TelegramClient telegramClient, Update update) {
        virtualThreadConfig.virtualThreadExecutor().execute(() -> {
            log.info("收到消息: {}", JSONObject.toJSONString(update));
            if (update.hasMyChatMember()) {
                if (update.getMyChatMember().getNewChatMember().getStatus().equals(BotInfo.MEMBER.getInfo())) {
                    groupService.botJoinGroup(telegramClient, update);
                    return;
                }
                if (update.getMyChatMember().getNewChatMember().getStatus().equals(BotInfo.LEFT.getInfo())) {
                    groupService.botLeaveGroup(update);
                    return;
                }

                if (update.getMyChatMember().getNewChatMember().getStatus().equals(BotInfo.BOT_IS_ADMIN.getInfo())
                        && update.getMyChatMember().getNewChatMember().getUser().getUserName().equals(BotInfo.BOT_NAME.getInfo())
                        && update.getMyChatMember().getNewChatMember().getUser().getIsBot()
                ) {
                    groupService.botIsAdmin(telegramClient, update);
                }

            }

            if (update.hasMessage() && update.getMessage().getNewChatMembers() != null && !update.getMessage().getNewChatMembers().isEmpty()) {
                groupService.userJoinGroup(telegramClient, update);
                return;

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
                        return;
                    }
                    if (text.equals(BotCommands.HELP.getCommand())) {
                        //String helpText = String.format("用户:<a href=\"tg://user?id=%s\">%s</a> ，爱你呦，宝贝💖", "6071490801", "以前的静（删库跑路版）");
                        sendMsgService.sendHelpMessage(telegramClient, chatId);
                        return;
                    }

                    if (text.equals(BotCommands.CHINESE.getCommand())) {
                        sendMsgService.sendChinese(telegramClient, chatId);
                        return;
                    }
                }
                if (!botIsAdmin(update)) {
                    log.info("非管理员");
                    return;
                }

                //todo 群聊机器人命令（要检查群组是否被加入到白名单）
                if (update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat()) {
                    log.info("群聊命令");

                }


            }
        });
    }

    @Override
    public void setBotCommand(TelegramClient telegramClient) {
        try {
            List<BotCommand> groupCommand = ChangLeBotCommands.setGroupCommand();
            List<BotCommand> privetChatCommand = ChangLeBotCommands.setPrivetChatCommand();
            BotCommandScopeAllGroupChats botCommandScopeAllGroupChats = BotCommandScopeAllGroupChats.builder().build();
            SetMyCommands setAnyOneCommands = SetMyCommands.builder().commands(groupCommand).scope(botCommandScopeAllGroupChats).build();
            telegramClient.execute(setAnyOneCommands);
            SetChatMenuButton menuButton = SetChatMenuButton.builder().menuButton(new MenuButtonCommands()).build();
            telegramClient.execute(menuButton);
            SetMyCommands setPrivetCommand = SetMyCommands.builder().commands(privetChatCommand).build();
            telegramClient.execute(setPrivetCommand);
            log.info("设置命令成功");
        } catch (TelegramApiException e) {
            log.error("设置命令失败: {}", e.getMessage());
        }
    }


    /**
     * 判断机器人是否是管理员
     */
    private static boolean botIsAdmin(Update update) {
        return update.hasMyChatMember()
                && update.getMyChatMember().getNewChatMember().getStatus().equals(BotInfo.BOT_IS_ADMIN.getInfo())
                && update.getMyChatMember().getNewChatMember().getUser().getIsBot()
                && update.getMyChatMember().getNewChatMember().getUser().getUserName().equals(BotInfo.BOT_NAME.getInfo());
    }


/**
 * 判断机器人是否是管理员 并且给予相应的权限
 */
   /* private static boolean botIsAdmin(Update update) {
        if (update.hasMyChatMember()) {
            if (update.getMyChatMember().getNewChatMember().getStatus().equals("administrator")) {
                //判断权限
                ChatMemberAdministrator ChatMemberAdministrator = (ChatMemberAdministrator) update.getMyChatMember().getNewChatMember();
                return ChatMemberAdministrator.getCanChangeInfo()
                        && ChatMemberAdministrator.getCanDeleteMessages()
                        && ChatMemberAdministrator.getCanManageChat();
            }
            return false;
        }
        return false;
    }*/
}
