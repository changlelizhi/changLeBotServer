package com.changle.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author : 长乐
 * @package : com.changle.service
 * @project : changLeBotServer
 * @name : ChangLeBotService
 * @Date : 2025/7/24
 * @Description : 长乐机器人服务
 */
public interface ChangLeBotService {

    /**
     * 主方法
     */
    void mainFunction(TelegramClient telegramClient, Update update);

    /**
     * 设置机器人命令
     */
    void  setBotCommand(TelegramClient telegramClient);
}
