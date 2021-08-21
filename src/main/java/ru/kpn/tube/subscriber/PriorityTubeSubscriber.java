package ru.kpn.tube.subscriber;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.strategy.SubscriberStrategy;
import ru.kpn.tube.strategy.none.NoneSubscriberStrategy;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class PriorityTubeSubscriber implements TubeSubscriber<TubeMessage, BotApiMethod<?>> {

    private final SubscriberStrategy<TubeMessage, BotApiMethod<?>> strategy;
    private final Comparator<Integer> priorityComparator;
    private final int priority;

    private TubeSubscriber<TubeMessage, BotApiMethod<?>> next;

    private PriorityTubeSubscriber(SubscriberStrategy<TubeMessage, BotApiMethod<?>> strategy,
                                   Comparator<Integer> priorityComparator,
                                   int priority) {
        this.strategy = strategy;
        this.priorityComparator = priorityComparator;
        this.priority = priority;
    }

    public static PriorityTubeSubscriberBuilder builder() {
        return new PriorityTubeSubscriberBuilder();
    }

    @Override
    public TubeSubscriber<TubeMessage, BotApiMethod<?>> setNext(TubeSubscriber<TubeMessage, BotApiMethod<?>> next) {
        final int compareResult = priorityComparator.compare(getPriority(), next.getPriority());
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
    public Optional<TubeSubscriber<TubeMessage, BotApiMethod<?>>> getNext() {
        return next != null ? Optional.of(next) : Optional.empty();
    }

    @Override
    public Optional<BotApiMethod<?>> executeStrategy(TubeMessage message) {
        return strategy.execute(message);
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    public static class DefaultComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer p0, Integer p1) {
            if (Objects.equals(p0, p1)){
                return 0;
            }
            return p0 > p1 ? 1 : -1;
        }
    }

    // TODO: 21.08.2021 change priority setting 
    public static class PriorityTubeSubscriberBuilder {

        private static final int DEFAULT_PRIORITY = Integer.MIN_VALUE;

        private SubscriberStrategy<TubeMessage, BotApiMethod<?>> strategy;
        private Comparator<Integer> priorityComparator;
        private Integer priority;

        PriorityTubeSubscriberBuilder() {
        }

        public PriorityTubeSubscriberBuilder strategy(SubscriberStrategy<TubeMessage, BotApiMethod<?>> strategy) {
            this.strategy = strategy;
            return this;
        }

        public PriorityTubeSubscriberBuilder priorityComparator(Comparator<Integer> priorityComparator) {
            this.priorityComparator = priorityComparator;
            return this;
        }

        public PriorityTubeSubscriberBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public TubeSubscriber<TubeMessage, BotApiMethod<?>> build() {
            checkOrCreateStrategy();
            checkOrCreateComparator();
            checkOrCreatePriority();
            return new PriorityTubeSubscriber(strategy, priorityComparator, priority);
        }

        private void checkOrCreateStrategy() {
            if (strategy == null){
                strategy = new NoneSubscriberStrategy(DEFAULT_PRIORITY);
            }
        }

        private void checkOrCreateComparator() {
            if (priorityComparator == null){
                priorityComparator = new DefaultComparator();
            }
        }

        private void checkOrCreatePriority() {
            if (priority == null){
                priority = DEFAULT_PRIORITY;
            }
        }
    }
}
