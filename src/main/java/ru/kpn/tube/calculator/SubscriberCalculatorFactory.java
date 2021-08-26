package ru.kpn.tube.calculator;

import ru.kpn.tube.subscriber.TubeSubscriber;

public interface SubscriberCalculatorFactory<T, R> {
    SubscriberCalculator<T,R> create(TubeSubscriber<T, R> subscriber);
}
