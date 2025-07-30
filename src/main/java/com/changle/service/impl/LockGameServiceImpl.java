package com.changle.service.impl;

import com.changle.entity.LockGame;
import com.changle.mapper.LockGameMapper;
import com.changle.service.LockGameService;
import com.changle.service.SendMsgService;
import com.changle.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDateTime;

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


    @Override
    public LockGame queryLockGame(String userId) {
        LockGame lockGame = lockGameMapper.selectByUserId(userId);

        //String text=String.format("没有用户：<b><a href=\"tg://user?id=%s\">%s</a></b> 的带锁信息!", userId, userName);
        if (lockGame == null){
         //   sendMsgService.sendGroupLockMessage()


            return lockGame;
        }
        return null;
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
            //deleteMsgService.deleteMsg(telegramClient, update.getMessage().getChatId().toString(), message);
        }else {
            String text=String.format("玩家：<b><a href=\"tg://user?id=%s\">%s</a></b> 的带锁信息为：%s", userId, userName, lockGame.getLockGameId());
            Integer lockGameMode = lockGame.getLockGameMode();
            Integer lockGameStatus = lockGame.getLockGameStatus();
            LocalDateTime endTime = lockGame.getEndTime();

        }
    }


}
