package ru.kpn.calculator.extractor;

import java.util.Optional;

public interface ExtractorCalculator<T, R> {
    R calculate(T message);
}
