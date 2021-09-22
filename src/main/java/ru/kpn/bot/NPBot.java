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
import ru.kpn.bot.publisher.Publisher;
import ru.kpn.calculator.extractor.ExtractorCalculatorFactory;
import ru.kpn.subscriber.Subscriber;

import java.util.function.Consumer;

// TODO: 22.09.2021 rename to BotTransmitter 
// TODO: 22.09.2021 rm interfaces Publisher and Consumer
// TODO: 22.09.2021 clr onWebhookUpdateReceived
// TODO: 22.09.2021 del calculatorFactory
// TODO: 22.09.2021 override execute as sync 
@Service
public class NPBot extends TelegramWebhookBot implements Publisher<Update, BotApiMethod<?>>, Consumer<Update> {

    private final String botPath;
    private final String botUserName;
    private final String botToken;
    private final ExtractorCalculatorFactory<Update, BotApiMethod<?>> calculatorFactory;

    private Subscriber<Update, BotApiMethod<?>> subscriber;

    @Autowired
    public NPBot(DefaultBotOptions options,
                 @Value("${bot.path}") String botPath,
                 @Qualifier("botUserName") String botUserName,
                 @Qualifier("botToken") String botToken,
                 ExtractorCalculatorFactory<Update, BotApiMethod<?>> calculatorFactory) {
        super(options);
        this.botPath = botPath;
        this.botUserName = botUserName;
        this.botToken = botToken;
        this.calculatorFactory = calculatorFactory;
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
