package ru.kpn.tube.calculator;

import ru.kpn.tube.subscriber.Subscriber;

public interface ExtractorCalculatorFactory<T, R> {
    ExtractorCalculator<T,R> create(Subscriber<T, R> subscriber);
}
