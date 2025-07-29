package com.changle.service;

import com.changle.entity.Group;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author : 长乐
 * @package : com.changle.service
 * @project : changLeBotServer
 * @name : GroupService
 * @Date : 2025/7/24
 * @Description : 群组服务接口
 */
public interface GroupService {

    void updateGroupDeleteStatus(String chatId, Integer groupStatusDelete, String botStatus);

    void updateGroupStatusNormal(String chatId, Integer groupStatusNormal, String botStatus);

    void updateGroupStatusDisable(String chatId, Integer groupStatusDisable, String botStatus);

    Integer queryGroupStatus(String chatId);


   void botJoinGroup(TelegramClient telegramClient,Update update);

   void botLeaveGroup(Update update);

   void userJoinGroup(TelegramClient telegramClient,Update update);

   void userLeaveGroup(TelegramClient telegramClient,Update update);

    void botIsAdmin(TelegramClient telegramClient,Update update);
}
