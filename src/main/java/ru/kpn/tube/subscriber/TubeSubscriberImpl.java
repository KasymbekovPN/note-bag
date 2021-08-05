package ru.kpn.tube.subscriber;

import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.strategy.SubscriberStrategy;

public class TubeSubscriberImpl implements TubeSubscriber<TubeMessage> {

    private final SubscriberStrategy<TubeMessage> strategy;

    private TubeSubscriber<TubeMessage> next;

    public TubeSubscriberImpl(SubscriberStrategy<TubeMessage> strategy) {
        this.strategy = strategy;
    }

    @Override
    public void calculate(TubeMessage value) {
        if (!strategy.execute(value) && next != null){
            next.calculate(value);
        }
    }

    @Override
    public TubeSubscriber<TubeMessage> hookUp(TubeSubscriber<TubeMessage> previous) {
        if (previous == null) {
            return this;
        }

        previous.setNext(this);
        return previous;
    }

    @Override
    public void setNext(TubeSubscriber<TubeMessage> next) {
        if (this.next == null){
            this.next = next;
        } else {
            this.next.setNext(next);
        }
    }
}
