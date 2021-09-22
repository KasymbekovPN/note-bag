package ru.kpn.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.calculator.strategy.StrategyResultCalculatorOLd;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.strategy.SubscriberStrategy;

import java.util.Comparator;

@Slf4j
@Service
public class SubscriberFactoryOldImpl implements SubscriberFactory<Update, BotApiMethod<?>, Integer> {

    @Autowired
    private MessageBuilderFactory messageBuilderFactory;

    @Autowired
    private StrategyResultCalculatorOLd<BotApiMethod<?>, String> resultCalculator;

    private SubscriberStrategy<Update, BotApiMethod<?>> strategy;
    private Comparator<Integer> comparator;

    @Override
    public Subscriber<Update, BotApiMethod<?>> build() {
        check();
        logCreation();
        return new PrioritySubscriberOld(strategy, comparator);
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
        String message = messageBuilderFactory
                .create("factory.subscriberBuilding")
                .arg(PrioritySubscriberOld.class)
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
            comparator = new PrioritySubscriberOld.DefaultComparator();
        }
    }
}
