package ru.kpn.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class NPBot extends TelegramWebhookBot {

    private final String botPath;
    private final String botUserName;
    private final String botToken;

    @Autowired
    public NPBot(DefaultBotOptions options,
                 @Value("${bot.path}") String botPath,
                 @Value("${bot.name}") String botUserName,
                 @Value("${bot.token}") String botToken) {
        super(options);
        this.botPath = botPath;
        this.botUserName = botUserName;
        this.botToken = botToken;
    }

    // TODO: 23.08.2021 subscribers must be here !!!
    
    // TODO: 23.08.2021 ???
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    // TODO: 25.08.2021 to separated interface
    @SneakyThrows
    public synchronized void run(Update datum) {
        BotApiMethod<?> botApiMethod = onWebhookUpdateReceived(datum);
        execute(botApiMethod);
    }
}
