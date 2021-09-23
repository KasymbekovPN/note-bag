package ru.kpn.subscriber;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.Strategy;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class PrioritySubscriber implements Subscriber<Update, BotApiMethod<?>> {

    private final Strategy<Update, BotApiMethod<?>> strategy;
    private final Comparator<Integer> comparator;

    public PrioritySubscriber(Strategy<Update, BotApiMethod<?>> strategy) {
        this.strategy = strategy;
        this.comparator = new DefaultComparator();
    }

    public PrioritySubscriber(Strategy<Update, BotApiMethod<?>> strategy, Comparator<Integer> comparator) {
        this.strategy = strategy;
        this.comparator = comparator;
    }

    @Override
    public Optional<BotApiMethod<?>> executeStrategy(Update value) {
        return strategy.execute(value);
    }

    @Override
    public int compareTo(Integer i) {
        return comparator.compare(strategy.getPriority(), i);
    }

    public static class DefaultComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer i1, Integer i2) {
            if (Objects.equals(i1, i2)){
                return 0;
            }
            return i1 > i2 ? 1 : -1;
        }
    }
}
