package ru.kpn.matcher;

import java.util.function.Function;

public interface SubscriberStrategyMatcherFactory<T, R> {
    Function<T, R> create(MatcherType matcherType, Object... args);
}
