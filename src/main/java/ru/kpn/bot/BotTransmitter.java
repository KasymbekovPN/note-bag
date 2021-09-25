package ru.kpn.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bot.transmitter.Transmitter;

@Service
public class BotTransmitter extends TelegramWebhookBot implements Transmitter<BotApiMethod<?>> {

    private final String botPath;
    private final String botUserName;
    private final String botToken;

    @Autowired
    public BotTransmitter(DefaultBotOptions options,
                          @Value("${bot.path}") String botPath,
                          @Qualifier("botUserName") String botUserName,
                          @Qualifier("botToken") String botToken) {
        super(options);
        this.botPath = botPath;
        this.botUserName = botUserName;
        this.botToken = botToken;
    }

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

    @SneakyThrows
    @Override
    public void transmit(BotApiMethod<?> value) {
        execute(value);
    }
}
