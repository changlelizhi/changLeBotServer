package com.changle.service;

import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author : 长乐
 * @package : com.changle.service
 * @project : changLeBotServer
 * @name : SendMsgService
 * @Date : 2025/7/23
 * @Description : 发送信息服务接口
 */
public interface SendMsgService {

    void sendHelpMessage(TelegramClient telegramClient, String chatId);

}
