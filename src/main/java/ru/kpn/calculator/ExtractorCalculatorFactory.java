package ru.kpn.calculator;

import ru.kpn.subscriber.Subscriber;

public interface ExtractorCalculatorFactory<T, R> {
    ExtractorCalculator<T,R> create(Subscriber<T, R> subscriber);
}
