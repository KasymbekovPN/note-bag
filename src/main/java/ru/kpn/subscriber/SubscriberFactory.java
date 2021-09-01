package ru.kpn.tube.subscriber;

import ru.kpn.tube.strategy.SubscriberStrategy;

import java.util.Comparator;

public interface SubscriberFactory<T, R, C> {
    Subscriber<T, R> build();
    SubscriberFactory<T, R, C> reset();
    default SubscriberFactory<T, R, C> strategy(SubscriberStrategy<T, R> strategy){return this;}
    default SubscriberFactory<T, R, C> comparator(Comparator<C> comparator) {return this;}
}
