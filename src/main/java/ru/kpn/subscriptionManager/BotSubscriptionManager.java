package ru.kpn.subscriptionManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.StrategyCalculator;
import ru.kpn.subscriber.Subscriber;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@RequiredArgsConstructor
@Service
public class BotSubscriptionManager implements SubscriptionManager<Update, BotApiMethod<?>> {

    private final StrategyCalculator<BotApiMethod<?>> defaultStrategyCalculator;
    private final Set<Subscriber<Update, BotApiMethod<?>>> subscribers = new TreeSet<>();

    @Override
    public void subscribe(Subscriber<Update, BotApiMethod<?>> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public synchronized BotApiMethod<?> execute(Update update) {
        for (Subscriber<Update, BotApiMethod<?>> subscriber : subscribers) {
            Optional<BotApiMethod<?>> maybeResult = subscriber.executeStrategy(update);
            if (maybeResult.isPresent()){
                return maybeResult.get();
            }
        }
        return calculateDefaultAnswer(update);
    }

    private BotApiMethod<?> calculateDefaultAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        return defaultStrategyCalculator.calculate("noneSubscriberStrategy.unknownInput", chatId, text);
    }
}
