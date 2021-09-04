package ru.kpn.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.i18n.I18n;
import ru.kpn.strategy.SubscriberStrategy;
import ru.kpn.strategy.none.NoneSubscriberStrategy;

import java.util.Comparator;

@Slf4j
@Service
public class SubscriberFactoryImpl implements SubscriberFactory<Update, BotApiMethod<?>, Integer> {

    @Autowired
    private I18n i18n;

    private SubscriberStrategy<Update, BotApiMethod<?>> strategy;
    private Comparator<Integer> comparator;

    @Override
    public Subscriber<Update, BotApiMethod<?>> build() {
        check();
        logCreation();
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

    private void logCreation() {
        String comparatorName = comparator == null ? "null" : comparator.getClass().getSimpleName();
        String msg = i18n.get(
                "factory.subscriberBuilding",
                PrioritySubscriber.class.getSimpleName(),
                strategy.getClass().getSimpleName() ,
                comparatorName);
        log.info("{}", msg);
    }

    private void check() {
        checkStrategy();
        checkComparator();
    }

    private void checkStrategy() {
        if (strategy == null){
            strategy = new NoneSubscriberStrategy(Integer.MIN_VALUE, i18n);
        }
    }

    private void checkComparator() {
        if (comparator == null){
            comparator = new PrioritySubscriber.DefaultComparator();
        }
    }
}
