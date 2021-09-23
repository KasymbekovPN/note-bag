package ru.kpn.subscriber;

import ru.kpn.strategy.Strategy;

import java.util.Comparator;

public interface SubscriberFactory<T, R, C> {
    Subscriber<T, R> build();
    SubscriberFactory<T, R, C> reset();
    default SubscriberFactory<T, R, C> strategy(Strategy<T, R> strategy){return this;}
    default SubscriberFactory<T, R, C> comparator(Comparator<C> comparator) {return this;}
}
