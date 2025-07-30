package com.changle.service.impl;

import com.changle.service.SendMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.TextQuote;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
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
    public void sendStartMessage(TelegramClient telegramClient, String chatId) {
        try {
            //TODO  new WebAppInfo(url需要更改)
            InlineKeyboardButton inlineKeyboardButton = InlineKeyboardButton.builder().text("这里，点这里!").webApp(new WebAppInfo("https://6dcba0848e2f.ngrok-free.app")).build();
            InlineKeyboardRow inlineKeyboardRow = new InlineKeyboardRow();
            inlineKeyboardRow.add(inlineKeyboardButton);
            InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder().keyboardRow(inlineKeyboardRow).build();
            SendMessage sendMessage = SendMessage.builder().replyMarkup(inlineKeyboardMarkup).chatId(chatId).text("霓裳Bot，男娘的快乐").build();
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("SendMsgService.sendStartMessage()方法失败: {}", e.getMessage());
        }
    }

    @Override
    public void sendHelpMessage(TelegramClient telegramClient, String chatId) {
        try {
            SendMessage sendMessage = SendMessage.builder().chatId(chatId).text("如果给了管理员，并且赋予了 <b>修改群组信息、删除消息</b> 权限，请联系作者（bot简介有作者联系方式）").parseMode(ParseMode.HTML).build();
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("SendMsgService.sendHelpMessage()方法失败: {}", e.getMessage());
        }
    }

    @Override
    public void sendChinese(TelegramClient telegramClient, String chatId) {
        try {
            SendMessage sendMessage = SendMessage.builder().chatId(chatId).text("简体中文语言包：tg://setlanguage?lang=zhcncc").parseMode(ParseMode.HTML).build();
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("SendMsgService.sendChinese()方法失败: {}", e.getMessage());
        }
    }

    @Override
    public Message sendHtmlMsg(TelegramClient telegramClient, String chatId, String text) {
        try {
            SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(text).parseMode(ParseMode.HTML).build();
            return telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("SendMsgService.sendHtmlMsg()方法失败: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Message sendGroupLockMessage(TelegramClient telegramClient, String chatId) {


        return null;
    }

    @Override
    public Message sendGroupJiaoLangMessage(TelegramClient telegramClient, String chatId) {
        return null;
    }

}
