package ru.kpn.subscriber;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.Strategy;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

// TODO: 22.09.2021 del
class PrioritySubscriberOld implements Subscriber<Update, BotApiMethod<?>> {

    private final Strategy<Update, BotApiMethod<?>> strategy;
    private final Comparator<Integer> comparator;

    // TODO: 22.09.2021 del 
    private Subscriber<Update, BotApiMethod<?>> next;

    public PrioritySubscriberOld(Strategy<Update, BotApiMethod<?>> strategy,
                                 Comparator<Integer> comparator) {
        this.strategy = strategy;
        this.comparator = comparator;
    }

    // TODO: 22.09.2021 del 
    @Override
    public Subscriber<Update, BotApiMethod<?>> setNext(Subscriber<Update, BotApiMethod<?>> next) {
        final int compareResult = comparator.compare(getPriority(), next.getPriority());
        if (compareResult >= 0){
            if (this.next == null){
                this.next = next;
            } else {
                this.next = this.next.setNext(next);
            }
            return this;
        } else {
            return next.setNext(this);
        }
    }

    @Override
    public int compareTo(Subscriber<Update, BotApiMethod<?>> s) {
        return 0;
    }

    // TODO: 22.09.2021 del
    @Override
    public Optional<Subscriber<Update, BotApiMethod<?>>> getNext() {
        return next != null ? Optional.of(next) : Optional.empty();
    }

    @Override
    public Optional<BotApiMethod<?>> executeStrategy(Update message) {
        return strategy.execute(message);
    }

    // TODO: 22.09.2021 del 
    @Override
    public Integer getPriority() {
        return strategy.getPriority();
    }

    // TODO: 22.09.2021 del
    static class DefaultComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer p0, Integer p1) {
            if (Objects.equals(p0, p1)){
                return 0;
            }
            return p0 > p1 ? 1 : -1;
        }
    }
}
