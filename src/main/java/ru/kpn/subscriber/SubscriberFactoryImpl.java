package ru.kpn.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.strategy.strategies.Strategy;

import java.util.Comparator;

@Slf4j
@Service
public class SubscriberFactoryImpl implements SubscriberFactory<Update, BotApiMethod<?>> {

    @Autowired
    private MessageBuilderFactory messageBuilderFactory;

    private Strategy<Update, BotApiMethod<?>> strategy;
    private Comparator<Subscriber<Update, BotApiMethod<?>>> comparator;

    @Override
    public Subscriber<Update, BotApiMethod<?>> build() {
        check();
        logCreation();
        return new PrioritySubscriber(strategy, comparator);
    }

    @Override
    public SubscriberFactory<Update, BotApiMethod<?>> reset() {
        strategy = null;
        comparator = null;
        return this;
    }

    @Override
    public SubscriberFactory<Update, BotApiMethod<?>> strategy(Strategy<Update, BotApiMethod<?>> strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public SubscriberFactory<Update, BotApiMethod<?>> comparator(Comparator<Subscriber<Update, BotApiMethod<?>>> comparator) {
        this.comparator = comparator;
        return this;
    }

    private void logCreation() {
        String message = messageBuilderFactory
                .create("factory.subscriberBuilding")
                .arg(PrioritySubscriber.class)
                .arg(strategy)
                .arg(comparator)
                .build();
        log.info("{}", message);
    }

    private void check() {
        checkComparator();
    }

    private void checkComparator() {
        if (comparator == null){
            comparator = new PrioritySubscriber.DefaultComparator();
        }
    }
}
