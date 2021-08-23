package ru.kpn.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class NPBot extends TelegramWebhookBot {

    // TODO: 23.08.2021 subscribers must be here !!! 
    
    // TODO: 23.08.2021 ???
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
