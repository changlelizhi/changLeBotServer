package com.changle.bot;

import com.changle.service.ChangLeBotService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author : 长乐
 * @package : com.changle.bot
 * @project : changLeBotServer
 * @name : ChangLeBot
 * @Date : 2025/7/23
 * @Description : 长乐bot
 */
@Getter
@Component
public class ChangLeBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final String botToken;

    private final TelegramClient telegramClient;

    private final ChangLeBotService changLeBotService;

    public ChangLeBot(@Value("${telegram.bot.token}") String botToken, ChangLeBotService changLeBotService) {
        this.botToken = botToken;
        this.telegramClient = new OkHttpTelegramClient(botToken);
        this.changLeBotService = changLeBotService;
    }

    @PostConstruct
    public void init() {
        changLeBotService.setBotCommand(telegramClient);
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update ) {
        changLeBotService.mainFunction(telegramClient, update);
    }
}
