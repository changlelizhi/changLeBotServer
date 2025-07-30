package com.changle.service;

import com.changle.entity.LockGame;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import javax.management.Query;

/**
 * @author : 长乐
 * @package : com.changle.service
 * @project : changLeBotServer
 * @name : LockGameService
 * @Date : 2025/7/30
 * @Description : 锁游戏服务
 */
public interface LockGameService {

    LockGame queryLockGame(String userId);

    Integer saveLockGame(LockGame lockGame);

    void queryLockGameAndSendButMsg(TelegramClient telegramClient, Update  update);


}
