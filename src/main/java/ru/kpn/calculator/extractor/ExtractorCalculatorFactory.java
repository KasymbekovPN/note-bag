package ru.kpn.calculator.extractor;

import ru.kpn.subscriber.Subscriber;

public interface ExtractorCalculatorFactory<T, R> {
    ExtractorCalculator<T,R> create(Subscriber<T, R> subscriber);
}
