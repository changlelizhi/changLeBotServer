package com.changle.service;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author : 长乐
 * @package : com.changle.service
 * @project : changLeBotServer
 * @name : DeleteMsgService
 * @Date : 2025/7/23
 * @Description : 删除信息服务
 */
public interface DeleteMsgService {

    void deleteMsg(TelegramClient telegramClient, String chatId, Message message);

    void deleteLockGameMsg(TelegramClient telegramClient, String chatId, Message message);
}
