package ru.kpn.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bot.publisher.Publisher;
import ru.kpn.calculator.extractor.ExtractorCalculatorFactory;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.subscriber.Subscriber;

import java.util.function.Consumer;

public class NPBot extends TelegramWebhookBot implements Publisher<Update, BotApiMethod<?>>, Consumer<Update> {

    private final String botPath;
    private final String botUserName;
    private final String botToken;
    private final ExtractorCalculatorFactory<Update, BotApiMethod<?>> calculatorFactory;
    private final MessageBuilderFactory messageBuilderFactory;

    private Subscriber<Update, BotApiMethod<?>> subscriber;

    public NPBot(DefaultBotOptions options,
                 String botPath,
                 String botUserName,
                 String botToken,
                 ExtractorCalculatorFactory<Update, BotApiMethod<?>> calculatorFactory,
                 MessageBuilderFactory messageBuilderFactory) {
        super(options);
        this.botPath = botPath;
        this.botUserName = botUserName;
        this.botToken = botToken;
        this.calculatorFactory = calculatorFactory;
        this.messageBuilderFactory = messageBuilderFactory;
    }

    // TODO: 04.09.2021 test it
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return calculatorFactory.create(subscriber).calculate(update);
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
    
    @Override
    public void subscribe(Subscriber<Update, BotApiMethod<?>> subscriber) {
        this.subscriber = this.subscriber == null ? subscriber : this.subscriber.setNext(subscriber);
    }

    @SneakyThrows
    @Override
    public void accept(Update update) {
        BotApiMethod<?> botApiMethod = onWebhookUpdateReceived(update);
        execute(botApiMethod);
    }
}
