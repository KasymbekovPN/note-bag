package ru.kpn.subscriber;

import ru.kpn.strategy.Strategy;

import java.util.Comparator;

public interface SubscriberFactory<T, R> {
    Subscriber<T, R> build();
    SubscriberFactory<T, R> reset();
    default SubscriberFactory<T, R> strategy(Strategy<T, R> strategy){return this;}
    default SubscriberFactory<T, R> comparator(Comparator<Subscriber<T, R>> comparator) {return this;}
}
