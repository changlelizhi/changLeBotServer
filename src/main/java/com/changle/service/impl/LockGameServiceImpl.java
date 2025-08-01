package com.changle.service.impl;

import com.changle.bot.constant.ChangLeBotConstant;
import com.changle.entity.LockGame;
import com.changle.mapper.LockGameMapper;
import com.changle.service.DeleteMsgService;
import com.changle.service.LockGameService;
import com.changle.service.SendMsgService;
import com.changle.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;


/**
 * @author : 长乐
 * @package : com.changle.service.impl
 * @project : changLeBotServer
 * @name : LockGameServiceImpl
 * @Date : 2025/7/30
 * @Description :
 */
@Slf4j
@Service
public class LockGameServiceImpl implements LockGameService {

    @Autowired
    private LockGameMapper lockGameMapper;

    @Autowired
    private SendMsgService sendMsgService;

    @Autowired
    private DeleteMsgService deleteMsgService;


    @Override
    public LockGame queryLockGame(String userId) {
        LockGame lockGame = lockGameMapper.selectByUserId(userId);
        return lockGame;
    }

    @Override
    public Integer saveLockGame(LockGame lockGame) {

        Integer result = lockGameMapper.insertLockGame(lockGame);

        return 0;
    }

    @Override
    public void queryLockGameAndSendButMsg(TelegramClient telegramClient, Update update) {
        String userId = update.getMessage().getFrom().getId().toString();
        String userName = UserUtils.combineUsername(update);
        LockGame lockGame = lockGameMapper.selectByUserId(userId);
        if (lockGame == null){
            String text=String.format("没有玩家：<b><a href=\"tg://user?id=%s\">%s</a></b> 的带锁信息!", userId, userName);
            Message message = sendMsgService.sendHtmlMsg(telegramClient, update.getMessage().getChatId().toString(), text);
            deleteMsgService.deleteMsg(telegramClient, update.getMessage().getChatId().toString(), message);
        }else {
            Integer lockGameMode = lockGame.getLockGameMode();
            Integer lockGameStatus = lockGame.getLockGameStatus();
            String startTime = lockGame.getStartTime().format(ChangLeBotConstant.DATE_TIME_FORMATTER);
            String endTime = lockGame.getEndTime().format(ChangLeBotConstant.DATE_TIME_FORMATTER);
            String text=String.format("玩家：<b><a href=\"tg://user?id=%s\">%s</a></b> 的带锁信息：\n 模式：%s\n 状态：%s\n 开始时间：%s\n 结束时间：%s\n",
                    userId, userName, lockGameMode, lockGameStatus, startTime, endTime);
            InlineKeyboardMarkup inlineKeyboardMarkup = setAddTimeButton(userId);
            Message message = sendMsgService.sendGroupLockMessage(telegramClient, inlineKeyboardMarkup, update.getMessage().getChatId().toString(),text);
            deleteMsgService.deleteLockGameMsg(telegramClient, update.getMessage().getChatId().toString(), message);
        }
    }

    /**
     * 设置一个加时按钮信息
     * @param userId
     */
    private InlineKeyboardMarkup setAddTimeButton (String userId) {
        InlineKeyboardButton addTimeButton = InlineKeyboardButton.builder().text("请务必帮我狠狠加时!").callbackData("addTime:" + userId).build();
        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(addTimeButton);
        return InlineKeyboardMarkup.builder().keyboardRow(row).build();
    }


}
