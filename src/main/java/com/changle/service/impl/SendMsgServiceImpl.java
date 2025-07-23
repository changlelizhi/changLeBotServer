package com.changle.service.impl;

import com.changle.service.SendMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author : 长乐
 * @package : com.changle.service.impl
 * @project : changLeBotServer
 * @name : SendMsgServiceImpl
 * @Date : 2025/7/23
 * @Description :
 */
@Slf4j
@Service
public class SendMsgServiceImpl implements SendMsgService {
    @Override
    public void sendHelpMessage(TelegramClient telegramClient, String chatId) {

        try {
            SendMessage sendMessage = SendMessage.builder().chatId(chatId).text("""
                    <b>作者懒得写帮助信息，GitHub:
                    https://github.com/changlelizhi/changLeBotServer
                    https://github.com/changlelizhi/changLeBotWeb</b>""").parseMode(ParseMode.HTML).build();
            Message message = telegramClient.execute(sendMessage);

        } catch (TelegramApiException e) {
            log.error("发送消息失败: {}", e.getMessage());

        }

    }
}
