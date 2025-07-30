package com.changle.service.impl;

import com.changle.bot.exception.BotBaseException;
import com.changle.bot.exception.BotError;
import com.changle.config.VirtualThreadConfig;
import com.changle.service.DeleteMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : 长乐
 * @package : com.changle.service.impl
 * @project : changLeBotServer
 * @name : DeleteMsgServiceImpl
 * @Date : 2025/7/27
 * @Description :
 */

@Slf4j
@Service
public class DeleteMsgServiceImpl implements DeleteMsgService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void deleteMsg(TelegramClient telegramClient, String chatId, Message message) {
        scheduler.schedule(() -> {
            try {
                DeleteMessage deleteMessage = DeleteMessage.builder().chatId(chatId).messageId(message.getMessageId()).build();
                telegramClient.execute(deleteMessage);
            } catch (TelegramApiException e) {
                throw new BotBaseException(BotError.BOT_DELETE_MSG_FAILED, e);
            }

        }, 10, TimeUnit.SECONDS);

    }

    @Override
    public void deleteLockGameMsg(TelegramClient telegramClient, String chatId, Message message) {
        scheduler.schedule(() -> {
            try {
                DeleteMessage deleteMessage = DeleteMessage.builder().chatId(chatId).messageId(message.getMessageId()).build();
                telegramClient.execute(deleteMessage);
            } catch (TelegramApiException e) {
                throw new BotBaseException(BotError.BOT_DELETE_MSG_FAILED, e);
            }
        }, 60, TimeUnit.SECONDS);
    }
}
