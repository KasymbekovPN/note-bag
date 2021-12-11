package ru.kpn.subscriber;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.strategies.Strategy;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class PrioritySubscriber implements Subscriber<Update, BotApiMethod<?>> {

    private final Strategy<Update, BotApiMethod<?>> strategy;
    private final Comparator<Subscriber<Update, BotApiMethod<?>>> comparator;

    public PrioritySubscriber(Strategy<Update, BotApiMethod<?>> strategy) {
        this.strategy = strategy;
        this.comparator = new DefaultComparator();
    }

    public PrioritySubscriber(Strategy<Update, BotApiMethod<?>> strategy, Comparator<Subscriber<Update, BotApiMethod<?>>> comparator) {
        this.strategy = strategy;
        this.comparator = comparator;
    }

    @Override
    public Optional<BotApiMethod<?>> executeStrategy(Update value) {
        return strategy.execute(value);
    }

    @Override
    public Integer getPriority() {
        return strategy.getPriority();
    }

    @Override
    public int compareTo(Subscriber<Update, BotApiMethod<?>> subscriber) {
        return comparator.compare(this, subscriber);
    }

    public static class DefaultComparator implements Comparator<Subscriber<Update, BotApiMethod<?>>> {
        @Override
        public int compare(Subscriber<Update, BotApiMethod<?>> s1, Subscriber<Update, BotApiMethod<?>> s2) {
            if (Objects.equals(s1.getPriority(), s2.getPriority())){
                return 0;
            }
            return s1.getPriority() > s2.getPriority() ? 1 : -1;
        }
    }
}
