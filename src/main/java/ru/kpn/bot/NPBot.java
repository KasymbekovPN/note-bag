package ru.kpn.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.calculator.ExtractorCalculatorFactory;
import ru.kpn.tube.subscriber.Subscriber;

import java.util.Optional;
import java.util.function.Consumer;

public class NPBot extends TelegramWebhookBot implements Publisher<Update, BotApiMethod<?>>, Consumer<Update> {

    private final String botPath;
    private final String botUserName;
    private final String botToken;
    private final ExtractorCalculatorFactory<Update, BotApiMethod<?>> calculatorFactory;

    private Subscriber<Update, BotApiMethod<?>> subscriber;

    public NPBot(DefaultBotOptions options,
                 String botPath,
                 String botUserName,
                 String botToken,
                 ExtractorCalculatorFactory<Update, BotApiMethod<?>> calculatorFactory) {
        super(options);
        this.botPath = botPath;
        this.botUserName = botUserName;
        this.botToken = botToken;
        this.calculatorFactory = calculatorFactory;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        final Optional<BotApiMethod<?>> maybeResult = calculatorFactory.create(subscriber).calculate(update);
        return maybeResult.isPresent() ? maybeResult.get() : getDefaultBotApiMethod(update);
    }

    private BotApiMethod<?> getDefaultBotApiMethod(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        // TODO: 26.08.2021 other text + translate
        return new SendMessage(chatId, "No one subscriber answer");
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
