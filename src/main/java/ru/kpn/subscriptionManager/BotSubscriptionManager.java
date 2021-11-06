package ru.kpn.subscriptionManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bot.transmitter.Transmitter;
import ru.kpn.strategyCalculator.StrategyCalculator;
import ru.kpn.strategyCalculator.RawMessage;
import ru.kpn.subscriber.Subscriber;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@RequiredArgsConstructor
@Service
public class BotSubscriptionManager implements SubscriptionManager<Update, BotApiMethod<?>> {

    private final Transmitter<BotApiMethod<?>> transmitter;
    private final StrategyCalculator<BotApiMethod<?>, String> defaultStrategyCalculator;
    private final Set<Subscriber<Update, BotApiMethod<?>>> subscribers = new TreeSet<>();

    @Override
    public void subscribe(Subscriber<Update, BotApiMethod<?>> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public synchronized void execute(Update update) {
        Optional<BotApiMethod<?>> maybeResult = Optional.empty();;
        for (Subscriber<Update, BotApiMethod<?>> subscriber : subscribers) {
            maybeResult = subscriber.executeStrategy(update);
            if (maybeResult.isPresent()){
                break;
            }
        }
        BotApiMethod<?> result = maybeResult.isPresent() ? maybeResult.get() : calculateDefaultAnswer(update);
        transmitter.transmit(result);
    }

    private BotApiMethod<?> calculateDefaultAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        RawMessage<String> rawMessage = new RawMessage<>() {
            @Override
            public String getCode() {
                return "noneSubscriberStrategy.unknownInput";
            }

            @Override
            public RawMessage<String> add(Object o) {return this;}

            @Override
            public Object[] getArgs() {
                return new Object[]{chatId, text};
            }
        };

        return defaultStrategyCalculator.calculate(rawMessage);
    }
}
