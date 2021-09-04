package ru.kpn.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bot.publisher.Publisher;
import ru.kpn.calculator.ExtractorCalculatorFactory;
import ru.kpn.i18n.I18n;
import ru.kpn.subscriber.Subscriber;

import java.util.Optional;
import java.util.function.Consumer;

public class NPBot extends TelegramWebhookBot implements Publisher<Update, BotApiMethod<?>>, Consumer<Update> {

    private final String botPath;
    private final String botUserName;
    private final String botToken;
    private final ExtractorCalculatorFactory<Update, BotApiMethod<?>> calculatorFactory;
    private final I18n i18n;

    private Subscriber<Update, BotApiMethod<?>> subscriber;

    public NPBot(DefaultBotOptions options,
                 String botPath,
                 String botUserName,
                 String botToken,
                 ExtractorCalculatorFactory<Update, BotApiMethod<?>> calculatorFactory,
                 I18n i18n) {
        super(options);
        this.botPath = botPath;
        this.botUserName = botUserName;
        this.botToken = botToken;
        this.calculatorFactory = calculatorFactory;
        this.i18n = i18n;
    }

    // TODO: 04.09.2021 test it
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Optional<BotApiMethod<?>> maybeResult = calculatorFactory.create(subscriber).calculate(update);
        return maybeResult.isPresent() ? maybeResult.get() : getDefaultBotApiMethod(update);
    }

    private BotApiMethod<?> getDefaultBotApiMethod(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        return new SendMessage(chatId, i18n.get("npBot.noOneSubscribersAnswer"));
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
