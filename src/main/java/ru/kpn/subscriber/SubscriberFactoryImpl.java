package ru.kpn.subscriber;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.SubscriberStrategy;

import java.util.Comparator;

@Service
public class SubscriberFactoryImpl implements SubscriberFactory<Update, BotApiMethod<?>, Integer> {

    private SubscriberStrategy<Update, BotApiMethod<?>> strategy;
    private Comparator<Integer> comparator;

    @Override
    public Subscriber<Update, BotApiMethod<?>> build() {
        // TODO: 30.08.2021 log it
        return new PrioritySubscriber(strategy, comparator);
    }

    @Override
    public SubscriberFactory<Update, BotApiMethod<?>, Integer> reset() {
        strategy = null;
        comparator = null;
        return this;
    }

    @Override
    public SubscriberFactory<Update, BotApiMethod<?>, Integer> strategy(SubscriberStrategy<Update, BotApiMethod<?>> strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public SubscriberFactory<Update, BotApiMethod<?>, Integer> comparator(Comparator<Integer> comparator) {
        this.comparator = comparator;
        return this;
    }
}
