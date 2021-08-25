package ru.kpn.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.calculator.SubscriberCalculator;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;
import java.util.function.Consumer;

// TODO: 25.08.2021 add builder
public class NPBot extends TelegramWebhookBot implements Publisher<Update, BotApiMethod<?>>, Consumer<Update> {

    private final String botPath;
    private final String botUserName;
    private final String botToken;

    private TubeSubscriber<Update, BotApiMethod<?>> subscriber;

    @Autowired
    public NPBot(DefaultBotOptions options,
                 String botPath,
                 String botUserName,
                 String botToken) {
        super(options);
        this.botPath = botPath;
        this.botUserName = botUserName;
        this.botToken = botToken;
    }

    // TODO: 23.08.2021 subscribers must be here !!!

    // TODO: 25.08.2021 test
    // TODO: 23.08.2021 ???
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        SubscriberCalculator<Update, BotApiMethod<?>> calculator = subscriber.createCalculator();
        Optional<BotApiMethod<?>> calculateResult = calculator.calculate(update);
        // TODO: 25.08.2021 handle empty
        return calculateResult.get();
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

    // TODO: 25.08.2021 test
    @Override
    public void subscribe(TubeSubscriber<Update, BotApiMethod<?>> subscriber) {
        this.subscriber = this.subscriber == null ? subscriber : this.subscriber.setNext(subscriber);
    }

    @SneakyThrows
    @Override
    public void accept(Update update) {
        BotApiMethod<?> botApiMethod = onWebhookUpdateReceived(update);
        execute(botApiMethod);
    }
}
